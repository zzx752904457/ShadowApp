package com.tencent.shadow.sample.plugin.app.lib.usecases.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.tencent.shadow.sample.host.lib.HostAddPluginViewContainer;
import com.tencent.shadow.sample.host.lib.HostContainerHolder;
import com.tencent.shadow.sample.plugin.app.lib.R;

/**
 * 宿主添加插件view需要启动的插件service
 */
public class HostAddPluginViewService extends IntentService {
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public HostAddPluginViewService() {
        super("HostAddPluginViewService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int id = intent.getIntExtra("id", 0);
        HostAddPluginViewContainer viewContainer
                = HostContainerHolder.viewInstances.remove(id);

        uiHandler.post(() -> {
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_host_add_plugin_view, null, false);
            viewContainer.addView(view);
        });
    }
}
