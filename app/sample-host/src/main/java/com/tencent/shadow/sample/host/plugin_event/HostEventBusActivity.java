package com.tencent.shadow.sample.host.plugin_event;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;

import com.common.lib.EventBusJsonConfigBean;
import com.common.lib.GloballLocationBean;
import com.common.lib.UserLoginStateEvent;
import com.google.gson.Gson;
import com.tencent.shadow.sample.constant.Constant;
import com.tencent.shadow.sample.host.HostApplication;
import com.tencent.shadow.sample.host.R;
import com.tencent.shadow.sample.host.lib.HostContainerHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HostEventBusActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_event_bus);
        EventBus.getDefault().register(this);
        findViewById(R.id.btn_load_plugin_event).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME, "com.tencent.shadow.sample.plugin.app.lib.usecases.host_communication.PluginEventBusActivity");
            HostApplication.getApp().getPluginManager().enter(HostEventBusActivity.this, Constant.FROM_ID_START_ACTIVITY, bundle, null);
        });
        findViewById(R.id.btn_send_event_bus).setOnClickListener(v -> {
            UserLoginStateEvent event = new UserLoginStateEvent();
            event.isLogin = true;
            EventBus.getDefault().post(event);
            EventBusJsonConfigBean<UserLoginStateEvent> configBean = new EventBusJsonConfigBean<>();
            configBean.key = "loginStatus";
            configBean.value = event;
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_EVENT_BUS_JSON_CONFIG, new Gson().toJson(configBean));
            HostApplication.getApp().getPluginManager()
                    .enter(this, Constant.FROM_ID_POST_EVENT_BUS, bundle, null);
        });
        loadPluginFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GloballLocationBean bean) {
        findViewById(R.id.ll_bg).setBackgroundColor(Color.parseColor("#ffffa0"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void loadPluginFragment() {
        String className = "com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.ManagerFragment";
        HostContainerHolder.fragmentInstances.put(className, fragment -> {
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container, fragment).commitAllowingStateLoss();
        });
        // 当是单进程时，可以直接使用主进程的manager对象
        Bundle bundle = new Bundle();
        bundle.putInt("fromId", Constant.FROM_ID_GET_FRAGMENT_FROM_PLUG);
        bundle.putString(Constant.KEY_FRAGMENT_CLASSNAME, className);
        HostApplication.getApp().getPluginManager()
                .enter(this, Constant.FROM_ID_GET_FRAGMENT_FROM_PLUG, bundle, null);
    }
}
