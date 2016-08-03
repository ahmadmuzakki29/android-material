package com.muzakki.ahmad.material.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.muzakki.ahmad.helper.Helper;
import com.muzakki.ahmad.layout.JustifyLayout;
import com.muzakki.ahmad.material.R;

import java.util.ArrayList;

/**
 * Created by jeki on 8/1/16.
 */
public abstract class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
    }

    protected abstract ArrayList<DashboardItem> getItems();

    protected abstract void onMenuClick(int i);

    protected void render(){
        ArrayList<DashboardItem> items = getItems();
        int i=0;
        JustifyLayout parent = (JustifyLayout) findViewById(R.id.parent);
        for(DashboardItem item : items){
            View item_layout = getItemLayout();
            JustifyLayout.LayoutParams lp = new JustifyLayout.LayoutParams(Helper.getPxFromDp(150,getResources()), JustifyLayout.LayoutParams.WRAP_CONTENT);
            item_layout.setLayoutParams(lp);
            item_layout.findViewById(R.id.wrapper).setOnClickListener(new mOnClickListener(i++));
            drawItem(item_layout,item);
            parent.addView(item_layout);
        }
    }

    private class mOnClickListener implements View.OnClickListener{
        private final int i;

        mOnClickListener(int i){
            this.i = i;
        }
        @Override
        public void onClick(View view) {
            onMenuClick(i);
        }
    }

    protected View getItemLayout(){
        return getLayoutInflater().inflate(R.layout.dashboard_item, null);
    }

    protected void drawItem(View layout,DashboardItem item){
        ((TextView)layout.findViewById(R.id.title)).setText(item.getTitle());
        item.setIcon(this,((ImageView)layout.findViewById(R.id.icon)));
    }
}
