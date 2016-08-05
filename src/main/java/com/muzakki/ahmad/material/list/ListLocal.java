package com.muzakki.ahmad.material.list;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jeki on 6/3/16.
 */
public abstract class ListLocal extends List{
    private final Activity act;
    private final ListInternetConnection ic;
    protected final Listener listener;

    private ListModel model;

    public ListLocal(Activity act){
        this(act,null);
    }
    public ListLocal(Activity act,Listener listener) {
        super(act,listener);
        this.act = act;
        this.listener = listener;
        ic = getListInternetConnection(act,this);
    }

    public void render(){
        getEmptyView().setVisibility(GONE);
        hideInfo();
        model  = getListModel();
        ArrayList<Bundle> data = model.getData();
        if(!data.isEmpty()) {
            setListData(data);
        }else{
            fetchData();
        }
    }

    @Override
    public void refresh() {
        model.emptyData();
        render();
    }

    protected ListInternetConnection getListInternetConnection(Activity act, List list) {
        return new ListInternetConnection(act,list);
    }

    protected void fetchData(){
        if(listener!=null)  listener.setLoading(true);
        String url = getUrl();
        Bundle param = getParam();

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideInfo();
            }
        });

        ic.get(url,param);
    }

    public void setListData(ArrayList<Bundle> data){ // called when ready
        if(data.isEmpty()) notifyEmpty();
        if(listener!=null) listener.setLoading(false);
        setAdapter(new ListAdapter(this,data));
    }

    protected ListModel getListModel(){
        if(model==null) model = new ListModel(act,getTableName());
        return model;
    }

    protected abstract String getTableName();

    protected Bundle getParam(){
        throw new UnsupportedOperationException("getParam Not Implemented!");
    }

    protected String getUrl(){
        throw new UnsupportedOperationException("getUrl Not Implemented!");
    }


    public void parseData(JSONArray result){
        if(result==null){
            notifyEmpty();
            return;
        }

        try {
            ArrayList<JSONObject> arrayObject = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject obj = result.getJSONObject(i);
                arrayObject.add(obj);
            }
            model.insertData(arrayObject);
        }catch(JSONException e){e.printStackTrace();}

        ArrayList<Bundle> data = model.getData();
        setListData(data);
    }

    @Override
    public void onListClick(Bundle data) {
        Log.i("jeki",data.toString());
    }

}
