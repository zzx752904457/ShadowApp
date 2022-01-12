package com.tencent.shadow.sample.host.plugin_fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.tencent.shadow.sample.constant.Constant;
import com.tencent.shadow.sample.host.R;
import com.tencent.shadow.sample.host.lib.HostContainerHolder;
import com.tencent.shadow.sample.host.lib.HostGetPluginFragmentContainer;

public class HostGetPluginFragmentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_get_plugin_fragment);
        findViewById(R.id.btn_load_fragment).setOnClickListener(v -> {
            loadPluginFragment();
        });
    }

    private void loadPluginFragment() {
        String className = "com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.ManagerFragment";
        HostContainerHolder.fragmentInstances.put(className, fragment -> {
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_container, fragment).commitAllowingStateLoss();
        });
        //当是多进程时，不能直接操作主进程的manager对象，所以通过一个广播调用manager。
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.putExtra("fromId", Constant.FROM_ID_GET_FRAGMENT_FROM_PLUG);
        intent.putExtra(Constant.KEY_FRAGMENT_CLASSNAME, className);
        intent.setAction("sample_host.manager.startPluginService");
        sendBroadcast(intent);

        // 当是单进程时，可以直接使用主进程的manager对象
//        Bundle bundle = new Bundle();
//        bundle.putInt("fromId", Constant.FROM_ID_GET_FRAGMENT_FROM_PLUG);
//        bundle.putString(Constant.KEY_FRAGMENT_CLASSNAME, className);
//        HostApplication.getApp().getPluginManager()
//                .enter(this, Constant.FROM_ID_GET_FRAGMENT_FROM_PLUG, bundle, null);
    }
}
