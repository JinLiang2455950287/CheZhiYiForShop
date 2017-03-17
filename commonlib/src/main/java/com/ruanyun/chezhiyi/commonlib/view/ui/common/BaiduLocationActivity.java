package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.net.URISyntaxException;

/**
 * Description:
 * author: zhangsan on 16/9/14 上午11:41.
 */
public class BaiduLocationActivity extends AutoLayoutActivity implements BDLocationListener, Topbar.onTopbarClickListener,InfoWindow.OnInfoWindowClickListener {

    private BaiduMap mBaiduMap;
    MapView mMapView;
    Topbar topbar;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BaiduSDKReceiver mBaiduReceiver;
    ProgressDialog progressDialog;
    LocationClient mLocClient;
    BDLocation lastLocation;
    double latitude,longtitude,desLatitude,desLongtitude;
    String address;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SDKInitializer.initialize(app);
        setContentView(R.layout.activity_baidulocation);
        init();
    }

    private void init() {
        desLatitude=getIntent().getDoubleExtra(C.IntentKey.DESLATITUDE,0);
        desLongtitude=getIntent().getDoubleExtra(C.IntentKey.DESLONGTITUDE,0);
        address=getIntent().getStringExtra(C.IntentKey.ADDRESS);
        topbar = getView(R.id.topbar);
        mMapView = getView(R.id.bmapView);
        topbar.setBackBtnEnable(true)
                .setTttleText("地图")
                .enableRightText()
                .setRightText("开始导航")
                .onRightTextClick()
                .onBackBtnClick()
                .setTopbarClickListener(this);
       // Intent intent = getIntent();
         //latitude = intent.getDoubleExtra("latitude", 0);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        //initMapView();
      /*  if (latitude == 0) {

        } else {
            longtitude = intent.getDoubleExtra("longitude", 0);
            String address = intent.getStringExtra("address");
            LatLng p = new LatLng(latitude, longtitude);
            mMapView = new MapView(this,
                    new BaiduMapOptions().mapStatus(new MapStatus.Builder()
                            .target(p).build()));
            showMap(latitude, longtitude, address);
        }*/
/*        LatLng p = new LatLng(desLatitude,desLongtitude);
        mMapView = new MapView(this,
                new BaiduMapOptions().mapStatus(new MapStatus.Builder()
                        .target(p).build()));
        addOverLay(desLatitude,desLongtitude,R.drawable.position);*/

        showMapWithLocationClient();
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mBaiduReceiver = new BaiduSDKReceiver();
        registerReceiver(mBaiduReceiver, iFilter);
        //
    }

   /* private void initMapView() {
        mMapView.setLongClickable(true);
    }*/

    @Override
    protected void onPause() {
        mMapView.onPause();
        if (mLocClient != null) {
            mLocClient.stop();
        }
        super.onPause();
        lastLocation = null;
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        if (mLocClient != null) {
            mLocClient.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocClient != null)
            mLocClient.stop();
        mLocClient = null;
        mMapView.onDestroy();
        unregisterReceiver(mBaiduReceiver);
    }

    private void showMap(double latitude, double longtitude, String address) {
        // sendButton.setVisibility(View.GONE);
        LatLng llA = new LatLng(latitude, longtitude);
        CoordinateConverter converter = new CoordinateConverter();
        converter.coord(llA);
        converter.from(CoordinateConverter.CoordType.COMMON);
        LatLng convertLatLng = converter.convert();
        OverlayOptions ooA = new MarkerOptions().position(convertLatLng).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.position))
                .zIndex(4).draggable(true);
        mBaiduMap.addOverlay(ooA);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 12.0f);
        mBaiduMap.animateMapStatus(u);
    }

    private void showMapWithLocationClient() {
        String str1 = getResources().getString(com.hyphenate.easeui.R.string.Making_sure_your_location);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(str1);

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface arg0) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("map", "cancel retrieve location");
                finish();
            }
        });

        progressDialog.show();

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(this);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// open gps
         option.setCoorType("bd09ll");
        // Johnson change to use gcj02 coordination. chinese national standard
        // so need to conver to bd09 everytime when draw on baidu map
//        option.setCoorType("gcj02");
        option.setScanSpan(30000);
        option.setAddrType("all");
        mLocClient.setLocOption(option);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            return;
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (lastLocation != null) {
            if (lastLocation.getLatitude() == bdLocation.getLatitude() && lastLocation.getLongitude() == bdLocation.getLongitude()) {
                // Log.d("map", "same location, skip refresh");
                // mMapView.refresh(); //need this refresh?
                return;
            }
        }
        lastLocation = bdLocation;

     //  addDesOverLay(lastLocation.getLatitude(),lastLocation.getLongitude());
        mBaiduMap.clear();
       addOverLay(lastLocation.getLatitude(),lastLocation.getLongitude(),R.drawable.position,"当前位置");
       addOverLay(desLatitude,desLongtitude,R.drawable.position,address);

    }

    private void addDesOverLay(double currentLatitude,double currentLongtitude){
        mBaiduMap.clear();
        LatLng latLng ,deslatlng= null;
        OverlayOptions overlayOptions,desoverlayOptions = null;
      //  Marker marker = null;
        latLng = new LatLng(currentLatitude, currentLongtitude);
        deslatlng=new LatLng(desLatitude,desLongtitude);
        desoverlayOptions=new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.position)).zIndex(5);
        // 图标
        overlayOptions = new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.position)).zIndex(5);
        //marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
       mBaiduMap.addOverlay(overlayOptions);
       mBaiduMap.addOverlay(desoverlayOptions);
        // 将地图移到到最后一个经纬度位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(deslatlng);
        mBaiduMap.setMapStatus(u);
        mBaiduMap.animateMapStatus(u);
    }

/*    static final BitmapDescriptor desBitmap=BitmapDescriptorFactory
            .fromResource(R.drawable.position);
    static final BitmapDescriptor curBitmap=BitmapDescriptorFactory
            .fromResource(com.hyphenate.easeui.R.drawable.ease_icon_marka);*/
    private void addOverLay(double latitude,double longtitude,int icon,String address){
        LatLng llA = new LatLng(latitude, longtitude);
        CoordinateConverter converter = new CoordinateConverter();
        converter.coord(llA);
        converter.from(CoordinateConverter.CoordType.COMMON);
        LatLng convertLatLng = converter.convert();
        BitmapDescriptor desBitmap=BitmapDescriptorFactory
                .fromResource(icon);
        OverlayOptions ooA = new MarkerOptions().position(convertLatLng).icon(desBitmap)
                .zIndex(4).draggable(false);

        mBaiduMap.addOverlay(ooA);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 13.0f);
        mBaiduMap.animateMapStatus(u);
       // InfoWindow infoWindow=new InfoWindow(creatTipText(address),convertLatLng,0);
      //  mBaiduMap.showInfoWindow(infoWindow);


    }



    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.img_btn_left) {
            finish();
        } else if(viewId==R.id.tv_title_right){
          openBaiduApp();
        }
    }


    private void openBaiduApp() {
        Intent intent = new Intent();
        if (AppUtility.isInstalled(mContext, "com.baidu.BaiduMap")) {
            try {
                intent = Intent.parseUri("intent://map/direction?" +
                        "origin=latlng:" + lastLocation.getLatitude() + "," + lastLocation.getLongitude() +
                        "|name:" + lastLocation.getAddrStr() +
                        "&destination=latlng:" + desLatitude + "," + desLongtitude +
                        "|name:" + address +
                        "&mode=driving" +
                        "&src=Name|AppName" +
                        "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0);
            } catch (URISyntaxException e) {
                //  LogX.d("URISyntaxException : " + e.getMessage());
                e.printStackTrace();
            }
            startActivity(intent);
        }else {
            AppUtility.showToastMsg("未安装百度地图");
        }
    }

    @Override
    public void onInfoWindowClick() {

    }


    public class BaiduSDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            String st1 = getResources().getString(com.hyphenate.easeui.R.string.Network_error);
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {

                String st2 = getResources().getString(com.hyphenate.easeui.R.string.please_check);
                AppUtility.showToastMsg(st2);
            } else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                AppUtility.showToastMsg(st1);
            }
        }
    }


    private TextView creatTipText(String address){
        TextView tvTip=new TextView(mContext);
        tvTip.setBackgroundResource(R.color.black);
        tvTip.getBackground().setAlpha(160);
        tvTip.setTextColor(getResources().getColor(R.color.white));
        return  tvTip;
    }

  /*  public void sendLocation(View view) {
        Intent intent = this.getIntent();
        intent.putExtra("latitude", lastLocation.getLatitude());
        intent.putExtra("longitude", lastLocation.getLongitude());
        intent.putExtra("address", lastLocation.getAddrStr());
        this.setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(com.hyphenate.easeui.R.anim.slide_in_from_left, com.hyphenate.easeui.R.anim.slide_out_to_right);
    }*/
}
