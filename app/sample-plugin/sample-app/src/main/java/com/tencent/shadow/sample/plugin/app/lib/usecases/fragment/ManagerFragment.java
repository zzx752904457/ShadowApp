package com.tencent.shadow.sample.plugin.app.lib.usecases.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.shadow.sample.plugin.app.lib.R;


public class ManagerFragment extends PluginFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("ManagerFragment", "onCreateView");
        return pluginInflater.inflate(R.layout.fragment_manager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("ManagerFragment", "onViewCreated");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ManagerFragment", "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("ManagerFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("ManagerFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("ManagerFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("ManagerFragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("ManagerFragment", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ManagerFragment", "onDestroy");
    }
}
