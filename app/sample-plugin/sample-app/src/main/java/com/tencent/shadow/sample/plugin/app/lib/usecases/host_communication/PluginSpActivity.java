package com.tencent.shadow.sample.plugin.app.lib.usecases.host_communication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.common.lib.CommonConstants;
import com.tencent.shadow.sample.host.lib.HostInfoProvider;
import com.tencent.shadow.sample.plugin.app.lib.R;

public class PluginSpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_sp);
        HostInfoProvider hostUiLayerProvider = HostInfoProvider.getInstance();
        String city = hostUiLayerProvider.getHostMMKVStringValue(CommonConstants.PREF_KEY_CITY);
        TextView tvName = findViewById(R.id.tv_city_name);
        tvName.setText(city);

        EditText etSp = findViewById(R.id.et_sp);
        Button btnSave = findViewById(R.id.btn_save_sp);
        btnSave.setOnClickListener(v -> {
            String cityValue = etSp.getText().toString();
            if (TextUtils.isEmpty(cityValue)) {
                return;
            }
            // 插件自身保存
            getSharedPreferences("plugin_sp", Context.MODE_PRIVATE).edit().putString(CommonConstants.PREF_KEY_PLUG_CITY, cityValue).apply();
        });
    }
}