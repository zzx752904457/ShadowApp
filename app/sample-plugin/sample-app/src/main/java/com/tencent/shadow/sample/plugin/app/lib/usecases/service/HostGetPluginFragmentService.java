package com.tencent.shadow.sample.plugin.app.lib.usecases.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.tencent.shadow.sample.host.lib.HostContainerHolder;
import com.tencent.shadow.sample.host.lib.HostGetPluginFragmentContainer;
import com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.PluginFragment;

/**
 * 宿主添加插件fragment需要启动的插件service
 */
public class HostGetPluginFragmentService extends IntentService {
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public HostGetPluginFragmentService() {
        super("HostGetPluginFragmentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String className = intent.getStringExtra("KEY_FRAGMENT_CLASSNAME");
        HostGetPluginFragmentContainer viewContainer
                = HostContainerHolder.fragmentInstances.remove(className);

        uiHandler.post(() -> {
            try {
                Class<?> fragmentClass = getClassLoader().loadClass(className);
                PluginFragment pluginFragment = (PluginFragment) fragmentClass.newInstance();
                pluginFragment.setPluginParams(getApplicationContext(), getApplicationContext().getResources(), LayoutInflater.from(getApplicationContext()));
                if (viewContainer != null) {
                    viewContainer.getPluginFragment(pluginFragment);
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                e.printStackTrace();
            }
        });
    }
}
