package com.amap;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.io.Serializable;
import java.util.ArrayList;

import com.dobay.dudao.R;

public class MapActivity extends Activity implements
        PoiSearch.OnPoiSearchListener,
        AMap.OnMyLocationChangeListener {

    private static final String TAG = "MapActivity";
    private MapView mMapView;
    private AMap mAMap;
    private boolean mNeedMoveToCenter = Boolean.TRUE;
    private ArrayList<Mark> mMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        mMarks = (ArrayList<Mark>) intent.getSerializableExtra("marks");
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        setSelfPoint();
        Log.d(TAG, "标记个数" + mMarks.size());
        setMarks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.d(TAG, "获取到搜索信息");
        ArrayList<PoiItem> items = poiResult.getPois();
        for (PoiItem item : items) {
            Log.d(TAG, item.getAdName());
            LatLonPoint point = item.getLatLonPoint();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(point.getLatitude(), point.getLongitude()));
            markerOptions.title(item.getTitle());
            markerOptions.snippet(item.getSnippet());
            mAMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onMyLocationChange(Location location) {
        if (mNeedMoveToCenter) {
            Log.d(TAG, "移动相机");
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 14);
            mAMap.animateCamera(update);
            mNeedMoveToCenter = Boolean.FALSE;
        }
        // initSearch(new LatLonPoint(location.getLatitude(), location.getLongitude()));
    }

    private void initSearch(LatLonPoint point) {
        PoiSearch.Query query = new PoiSearch.Query("煌上煌", "050000", "");
        query.setPageSize(100);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(point, 3000));
        poiSearch.searchPOIAsyn();
    }

    private void setSelfPoint() {
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        locationStyle.interval(10000);
        mAMap.setMyLocationStyle(locationStyle);
        mAMap.setOnMyLocationChangeListener(this);
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
        mAMap.setMyLocationEnabled(true);
    }

    private void setMarks() {
        for (Mark mark : mMarks) {
            MarkerOptions markerOptions = new MarkerOptions();
            Log.d(TAG,"坐标"+mark.getLn());
            markerOptions.position(new LatLng(mark.getLt(), mark.getLn()));
            markerOptions.title(mark.getTitle());
            markerOptions.snippet(mark.getSnippet());
            mAMap.addMarker(markerOptions);
        }
    }

    public static class Mark implements Serializable {
        private String title;
        private String snippet;
        private Double ln;
        private Double lt;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public Double getLn() {
            return ln;
        }

        public void setLn(Double ln) {
            this.ln = ln;
        }

        public Double getLt() {
            return lt;
        }

        public void setLt(Double lt) {
            this.lt = lt;
        }
    }
}
