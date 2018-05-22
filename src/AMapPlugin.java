package com.amap;

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

import java.util.HashMap;
import java.util.Map;

public class AMapPlugin extends CordovaPlugin implements AMapLocationListener {

    public AMapLocationClient mLocationClient = null;

    public Context mContext;

    public CallbackContext callbackContext;

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        this.callbackContext = callbackContext;

        if (action.equals("getMyLocation")) {
            this.initAMap();
        } else if (action.equals("stopMyLocation")) {
            this.stopAmap();
            this.callbackContext.success();
        } else {
            callbackContext.error("ERROR ACTION");
        }
        return true;
    }

    public void onLocationChanged(AMapLocation aMapLocation) {
        JSONObject location = new JSONObject();
        try {
            location.put("Latitude", aMapLocation.getLatitude());
            location.put("Longitude", String.valueOf(aMapLocation.getLongitude()));
            location.put("Province", String.valueOf(aMapLocation.getProvince()));
            location.put("City", String.valueOf(aMapLocation.getCity()));
            location.put("District", String.valueOf(aMapLocation.getDistrict()));
            location.put("Address", String.valueOf(aMapLocation.getAddress()));
        } catch (JSONException e) {
            this.callbackContext.error("LOCATION MESSAGE ERROR -- JSON PUT ERROR");
            return;
        }

        Log.d("LOCATION:", location.toString());

        PluginResult result = new PluginResult(PluginResult.Status.OK, location);
        result.setKeepCallback(true);
        this.callbackContext.sendPluginResult(result);
    }

    public void initAMap() {

        // 判断是否需要实例化新对象
        if (this.mLocationClient == null) {
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            mLocationOption.setNeedAddress(true);
            mLocationOption.setInterval(4000);
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
        String[] permissions = { Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        this.cordova.requestPermissions(this, 0, permissions);
    }
}