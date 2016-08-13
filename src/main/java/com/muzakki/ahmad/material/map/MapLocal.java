package com.muzakki.ahmad.material.map;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muzakki.ahmad.helper.Database;

import java.util.ArrayList;

/**
 * Created by jeki on 8/13/16.
 */
public abstract class MapLocal extends RelativeLayout implements OnMapReadyCallback {
    Database db;
    String sql = "select * from "+getTable();
    private ArrayList<Bundle> data;
    private MapView map;
    private Bundle savedInstance;
    private CameraPosition cameraPosition;
    private GoogleMap googleMap;

    public MapLocal(Context context) {
        super(context);
        db = new Database(context);
        map = new MapView(getContext());

        map.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        addView(map);
    }

    protected abstract String getTable();

    public void render(Bundle savedInstance){
        this.savedInstance = savedInstance;
        data = db.query(sql,null);
        if(!data.isEmpty()){
            drawMap();
        }else{
            throw new UnsupportedOperationException("fetch data here");
        }
    }

    private void drawMap() {
        map.onCreate(savedInstance);
        map.onResume();

        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.getMapAsync(this);
    }

    public void refresh(){

    }

    public void setCameraPosition(CameraPosition cameraPosition) {
        this.cameraPosition = cameraPosition;
        Log.i("jeki",cameraPosition.target.toString());
        if(googleMap!=null)
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if(cameraPosition!=null)
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        for(Bundle d: data){
            MarkerOptions opt = getMarkerOptions(d);
            if(opt.getPosition()!=null){
                opt.visible(true);
                googleMap.addMarker(opt);
            }
        }
    }

    /** To set icon
     *  opt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
     */
    protected abstract MarkerOptions getMarkerOptions(Bundle data);
}
