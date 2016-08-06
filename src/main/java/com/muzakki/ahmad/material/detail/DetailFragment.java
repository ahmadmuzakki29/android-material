package com.muzakki.ahmad.material.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jeki on 7/27/16.
 */
public class DetailFragment extends Fragment {


    public DetailFragment(){}

    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle data = new Bundle();
        data.putInt("position", position);
        fragment.setArguments(data);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int position = getArguments().getInt("position");
        return ((DetailTabActivity)getActivity()).getTabView(position,container,savedInstanceState);
    }
}
