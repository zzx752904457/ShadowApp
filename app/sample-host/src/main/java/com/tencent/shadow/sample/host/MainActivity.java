/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tencent.shadow.sample.host;

import static com.tencent.shadow.sample.constant.Constant.PART_KEY_PLUGIN_BASE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.sample.constant.Constant;
import com.tencent.shadow.sample.host.plugin_fragment.HostGetPluginFragmentActivity;
import com.tencent.shadow.sample.host.plugin_view.HostAddPluginViewActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TestHostTheme);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> partKeysAdapter = new ArrayAdapter<>(this, R.layout.part_key_adapter);
        partKeysAdapter.addAll(
                Constant.PART_KEY_PLUGIN_MAIN_APP,
                Constant.PART_KEY_PLUGIN_ANOTHER_APP
        );
        spinner.setAdapter(partKeysAdapter);

        // 先点击该按钮加载插件
        findViewById(R.id.btn_init_plugin).setOnClickListener(v -> {
            initPlugin();
        });

        // 加载插件activity
        findViewById(R.id.btn_load_plugin_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partKey = (String) spinner.getSelectedItem();
                Intent intent = new Intent(MainActivity.this, PluginLoadActivity.class);
                switch (partKey) {
                    //为了演示多进程多插件，其实两个插件内容完全一样，除了所在进程
                    case Constant.PART_KEY_PLUGIN_MAIN_APP:
                        intent.putExtra(Constant.KEY_PLUGIN_PART_KEY, PART_KEY_PLUGIN_BASE);
                        break;
                    case Constant.PART_KEY_PLUGIN_ANOTHER_APP:
                        intent.putExtra(Constant.KEY_PLUGIN_PART_KEY, partKey);
                        break;
                }

                switch (partKey) {
                    //为了演示多进程多插件，其实两个插件内容完全一样，除了所在进程
                    case Constant.PART_KEY_PLUGIN_MAIN_APP:
                    case Constant.PART_KEY_PLUGIN_ANOTHER_APP:
                        intent.putExtra(Constant.KEY_ACTIVITY_CLASSNAME, "com.tencent.shadow.sample.plugin.app.lib.gallery.splash.SplashActivity");
                        break;

                }
                startActivity(intent);
            }
        });

        // 加载插件fragment
        findViewById(R.id.btn_load_plugin_fragment).setOnClickListener(v -> {
            Intent intent = new Intent(this, HostGetPluginFragmentActivity.class);
            startActivity(intent);
        });

        // 加载插件view
        findViewById(R.id.btn_load_plugin_view).setOnClickListener(v -> {
            Intent intent = new Intent(this, HostAddPluginViewActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_sp).setOnClickListener(v -> {
            Intent intent = new Intent(this, HostSpActivity.class);
            startActivity(intent);
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void initPlugin() {
        HostApplication.getApp().loadPluginManager(PluginHelper.getInstance().pluginManagerFile);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_PLUGIN_ZIP_PATH, PluginHelper.getInstance().pluginZipFile.getAbsolutePath());
        PluginHelper.getInstance().singlePool.execute(() -> {
            HostApplication.getApp().getPluginManager()
                    .enter(MainActivity.this, Constant.FROM_ID_INIT_PLUGIN, bundle, new EnterCallback() {
                        @Override
                        public void onShowLoadingView(View view) {
                        }

                        @Override
                        public void onCloseLoadingView() {
                        }

                        @Override
                        public void onEnterComplete() {
                        }
                    });
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        throw new RuntimeException("必须赋予权限.");
                    }
                }
            }
        }
    }

}
