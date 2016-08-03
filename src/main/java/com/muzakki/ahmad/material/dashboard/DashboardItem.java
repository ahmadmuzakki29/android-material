package com.muzakki.ahmad.material.dashboard;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by jeki on 8/1/16.
 */
public class DashboardItem {
    private final String title;
    private String iconPath;
    private int iconResource;
    public DashboardItem(String title,String iconPath){
        this.title = title;
        this.iconPath = iconPath;
    }

    public DashboardItem(String title, int iconResource){
        this.title = title;
        this.iconResource = iconResource;
    }

    public void setIcon(Activity act,ImageView image){
        if(iconPath==null){
            image.setImageBitmap(BitmapFactory.decodeResource(act.getResources(),iconResource));
        }else{
            Glide.with(act)
                    .load(iconPath)
                    .asBitmap()
                    .centerCrop()
                    .into(image);
        }
    }

    public String getTitle() {
        return title;
    }
}
