package com.tencent.shadow.sample.plugin.app.lib.usecases.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

public class PluginFragment extends Fragment {
    protected Context pluginContext;
    protected Resources pluginResources;
    protected LayoutInflater pluginInflater;

    public void setPluginParams(Context context, Resources resources, LayoutInflater inflater) {
        pluginContext = context;
        pluginResources = resources;
        pluginInflater = inflater;
    }
}
