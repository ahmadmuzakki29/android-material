package com.muzakki.ahmad.material.dashboard;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.muzakki.ahmad.material.R;

import java.util.ArrayList;

/**
 * Created by jeki on 8/1/16.
 */
public abstract class DashboardActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    ArrayList<Fragment> fragments;


    private int currentPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        initDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        super.onStart();
        ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }
    }

    protected abstract String[] getMenuItems();

    protected abstract Fragment getFragmentInstance(int pos);

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] drawer_array = getMenuItems();
        fragments=new ArrayList<>(drawer_array.length);
        for(String s: drawer_array) fragments.add(null);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        DrawerAdapter adapter = new DrawerAdapter(this, drawer_array);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, android.R.string.untitled, android.R.string.untitled) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectDrawerItem(adapterView.getSelectedItemPosition());
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    private Fragment getFragment(int pos){
         if(fragments.get(pos)==null) fragments.set(pos, getFragmentInstance(pos));
        return fragments.get(pos);
    }

    private void selectDrawerItem(int pos) {
        currentPosition =pos;

        Fragment fragment = getFragment(pos);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(pos, true);
        mDrawerLayout.closeDrawer(findViewById(R.id.drawer_wrapper));
        onSelectDrawerItem(pos);
    }

    protected void onSelectDrawerItem(int pos){
        ActionBar ab = getSupportActionBar();
        if(ab!=null){
            ab.setTitle(getMenuItems()[pos]);
        }
    }

    public class DrawerAdapter extends ArrayAdapter<String> {
        private final android.content.Context context;
        private final String[] values;

        public DrawerAdapter(android.content.Context context, String[] values) {
            super(context, android.R.layout.simple_list_item_1, values);
            this.context = context;
            this.values = values;
        }
/*
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = super.getView(position, convertView, parent);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            textView.setText(values[position]);
            switch (position){
                case 0: textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.drawer_home, 0, 0, 0);break;
                case 1: textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.drawer_profile, 0, 0, 0);break;
                case 2: textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.drawer_setting, 0, 0, 0);break;
            }

            return rowView;
        }*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
