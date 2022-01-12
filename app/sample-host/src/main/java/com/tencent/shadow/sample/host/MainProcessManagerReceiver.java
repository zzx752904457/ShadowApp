package com.tencent.shadow.sample.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.sample.constant.Constant;
import com.tencent.shadow.sample.host.HostApplication;

public class MainProcessManagerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PluginHelper.getInstance().singlePool.execute(() -> {
            // 多进程，所以也需要初始化一遍
            HostApplication.getApp().initPlugin(new EnterCallback() {
                @Override
                public void onShowLoadingView(View view) {

                }

                @Override
                public void onCloseLoadingView() {

                }

                @Override
                public void onEnterComplete() {
                    HostApplication.getApp().getPluginManager()
                            .enter(context, intent.getIntExtra("fromId", 0), intent.getExtras(), null);
                }
            });
        });
    }
}
