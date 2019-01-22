package com.amap;

import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;

import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocation;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.Manifest;
import android.content.Context;

import com.amap.api.location.AMapLocationClient;

import org.json.JSONObject;


public class AMapPlugin extends CordovaPlugin implements AMapLocationListener {

    private static final String TAG = "AMapPluginLog";
    private static final int GPS_REQUEST_CODE = 0;

    private AMapLocationClient mLocationClient = null;

    private Context mContext;

    private CallbackContext callbackContext;

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        this.callbackContext = callbackContext;
        switch (action) {
            case "_getMyLocation": {
                this.initAMap();
                break;
            }
            case "getMyLocation": {
                initGPS();
                this.initAMap();
                break;
            }
            case "checkGPS": {
                callbackContext.success(isGPSOpen() ? 1 : 0);
                break;
            }
            case "openGPS": {
                openGPS();
                break;
            }
            case "stopMyLocation": {
                this.stopAmap();
                this.callbackContext.success();
            }
            default: {
                callbackContext.error("ERROR ACTION");
            }
        }
        return true;
    }

    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

                JSONObject location = new JSONObject();
                try {
                    location.put("Latitude", aMapLocation.getLatitude());
                    location.put("Longitude", aMapLocation.getLongitude());
                    location.put("Province", aMapLocation.getProvince());
                    location.put("City", aMapLocation.getCity());
                    location.put("District", aMapLocation.getDistrict());
                    location.put("Address", aMapLocation.getAddress());
                } catch (JSONException e) {
                    this.callbackContext.error("LOCATION MESSAGE ERROR -- JSON PUT ERROR");
                    return;
                }

                Log.d("LOCATION:", location.toString());

                PluginResult result = new PluginResult(PluginResult.Status.OK, location);
                result.setKeepCallback(true);
                this.callbackContext.sendPluginResult(result);
            } else {
                this.callbackContext.error("定位失败" + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
            stopAmap();
        }
    }

    private boolean isGPSOpen() {
        Boolean result = Boolean.FALSE;
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            result = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        } else {
            Log.d(TAG, "无法获取系统GPS服务");
            this.callbackContext.error("无法使用设备GPS服务，请检查您设备的GPS是否正常");
        }
        return result;
    }

    private void openGPS() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则跳转至设置开启界面，设置完毕后返回到当前页面
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            // 转到手机设置界面，用户设置GPS
            Intent intent = new Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.cordova.startActivityForResult(this, intent, GPS_REQUEST_CODE);
        }
    }

    private void initAMap() {

        // 判断是否需要实例化新对象
        if (this.mLocationClient == null) {
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            mLocationOption.setNeedAddress(true);
            // 单次定位
            mLocationOption.setOnceLocation(true);
            // 4秒获取一次定位
            // mLocationOption.setInterval(4000);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            this.mLocationClient = new AMapLocationClient(mContext);
            this.mLocationClient.setLocationOption(mLocationOption);
            this.mLocationClient.setLocationListener(this);
        }

        // 判断是否需要开启定位
        if (!this.mLocationClient.isStarted()) {
            this.mLocationClient.startLocation();
        }
    }

    /* 检查gps状态并引导用户打开gps */
    private void initGPS() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则跳转至设置开启界面，设置完毕后返回到当前页面
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            // 转到手机设置界面，用户设置GPS
            Intent intent = new Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public void stopAmap() {

        // 判断是否需要关闭定位
        if (this.mLocationClient != null && this.mLocationClient.isStarted()) {
            this.mLocationClient.stopLocation();
            this.mLocationClient = null;
        }
    }

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {

        super.initialize(cordova, webView);

        // 获取ApplicationContext
        this.mContext = cordova.getActivity().getApplicationContext();

        // 获取授权
        this.requestPermission();
    }

    public void requestPermission() {
        String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        this.cordova.requestPermissions(this, 0, permissions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == GPS_REQUEST_CODE) {
            Log.d(TAG, resultCode + "");
            this.callbackContext.success(resultCode);
        }
    }
}