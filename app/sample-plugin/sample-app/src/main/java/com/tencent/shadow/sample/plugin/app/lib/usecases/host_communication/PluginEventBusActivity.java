package com.tencent.shadow.sample.plugin.app.lib.usecases.host_communication;

import android.app.Activity;
import android.os.Bundle;

import com.common.lib.EventBusJsonConfigBean;
import com.common.lib.GloballLocationBean;
import com.google.gson.Gson;
import com.tencent.shadow.sample.host.lib.HostEventBusReceiver;
import com.tencent.shadow.sample.plugin.app.lib.R;

import org.greenrobot.eventbus.EventBus;

public class PluginEventBusActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_event_bus);

        findViewById(R.id.btn_change_host_color).setOnClickListener(v -> {
            GloballLocationBean locationBean = new GloballLocationBean();
            EventBusJsonConfigBean<GloballLocationBean> configBean = new EventBusJsonConfigBean<>();
            configBean.key = "changeAddress";
            configBean.value = locationBean;
            // 发给插件自身
            EventBus.getDefault().post(configBean);
            // 发给宿主
            HostEventBusReceiver hostEventBusReceiver = HostEventBusReceiver.getInstance();
            String configStr = new Gson().toJson(configBean);
            hostEventBusReceiver.getEventBus(configStr);
        });
    }
}