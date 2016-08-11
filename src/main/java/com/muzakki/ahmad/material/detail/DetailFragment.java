package com.muzakki.ahmad.material.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzakki.ahmad.material.form.Form;
import com.muzakki.ahmad.material.list.List;

/**
 * Created by jeki on 7/27/16.
 */
public class DetailFragment extends Fragment {

    private View layout;

    private Listener listener;
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
        DetailTabActivity act = ((DetailTabActivity) getActivity());
        layout = act.getTabView(position,container,savedInstanceState);
        listener = act.getListener(position);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Thread thread = new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        render();
                    }
                });
            }
        };
        thread.start();
    }

    private void render(){
        if(layout instanceof List){
            ((List) layout).render();
        }else if(layout instanceof Form){
            ((Form) layout).render();
        }else if(layout instanceof Detail){
            ((Detail) layout).render();
        }else if(listener!=null){
            int pos = getArguments().getInt("position");
            listener.render(pos,layout);
        }
    }

    public interface Listener{
        void render(int position,View layout);
    }
}
