package com.tencent.shadow.sample.host;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.common.lib.CommonConstants;
import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.sample.constant.Constant;
import com.tencent.shadow.sample.host.lib.HostContainerHolder;

public class HostSpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        EditText etSp = findViewById(R.id.et_sp);
        Button btnSave = findViewById(R.id.btn_save_sp);
        Button btnLaunch = findViewById(R.id.btn_launch_plugin);
        Button btnLoad = findViewById(R.id.btn_load_plugin_sp);
        btnSave.setOnClickListener(v -> {
            String cityValue = etSp.getText().toString();
            if (TextUtils.isEmpty(cityValue)) {
                return;
            }
            getSharedPreferences("host_sp", Context.MODE_PRIVATE).edit().putString(CommonConstants.PREF_KEY_CITY, cityValue).apply();
        });
        btnLaunch.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_PLUGIN_PART_KEY, getIntent().getStringExtra(Constant.KEY_PLUGIN_PART_KEY));
            bundle.putString(Constant.KEY_ACTIVITY_CLASSNAME, "com.tencent.shadow.sample.plugin.app.lib.usecases.host_communication.PluginSpActivity");

            HostApplication.getApp().getPluginManager()
                    .enter(HostSpActivity.this, Constant.FROM_ID_START_ACTIVITY, bundle, new EnterCallback() {
                        @Override
                        public void onShowLoadingView(final View view) {
                        }

                        @Override
                        public void onCloseLoadingView() {
                        }

                        @Override
                        public void onEnterComplete() {
                        }
                    });
        });

        btnLoad.setOnClickListener(v -> {
            loadPluginSp();
        });
    }

    private void loadPluginSp() {
        HostContainerHolder.mmkvInstances.put(CommonConstants.PREF_KEY_PLUG_CITY, value -> {
            Toast.makeText(HostSpActivity.this, value.toString(), Toast.LENGTH_SHORT).show();
        });
        // 当是单进程时，可以直接使用主进程的manager对象
        Bundle bundle = new Bundle();
        bundle.putInt("fromId", Constant.FROM_ID_MMKV);
        bundle.putString(CommonConstants.PREF_KEY_PLUG_CITY, "");
        HostApplication.getApp().getPluginManager()
                .enter(this, Constant.FROM_ID_MMKV, bundle, null);
    }
}
