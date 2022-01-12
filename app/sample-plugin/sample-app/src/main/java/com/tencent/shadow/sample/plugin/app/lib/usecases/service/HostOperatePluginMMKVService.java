package com.tencent.shadow.sample.plugin.app.lib.usecases.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.tencent.shadow.sample.host.lib.HostContainerHolder;
import com.tencent.shadow.sample.host.lib.HostGetPluginMMKVContainer;

import java.util.Set;

public class HostOperatePluginMMKVService extends IntentService {
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    public HostOperatePluginMMKVService() {
        super("HostOperatePluginMMKVService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle bundle = intent.getExtras();
        Set<String> keys = bundle.keySet();
        for (String key: keys) {
                HostGetPluginMMKVContainer mmkvContainer
                        = HostContainerHolder.mmkvInstances.remove(key);
                if (mmkvContainer != null) {
                    // 读取插件sp中的值
                    Object value = getApplicationContext().getSharedPreferences("plugin_sp", Context.MODE_PRIVATE).getString(key, "");
                    uiHandler.post(() -> {
                        mmkvContainer.getMMKVValue(value);
                    });
                }
        }
    }
}
