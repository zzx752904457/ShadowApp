package com.tencent.shadow.sample.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.shadow.sample.constant.Constant;
import com.tencent.shadow.sample.host.HostApplication;

public class MainProcessManagerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getIntExtra("fromId", 0)) {
            case Constant.FROM_ID_LOAD_VIEW_TO_HOST: {
                HostApplication.getApp().getPluginManager()
                        .enter(context, Constant.FROM_ID_LOAD_VIEW_TO_HOST, intent.getExtras(), null);
            }
            break;
            case Constant.FROM_ID_GET_FRAGMENT_FROM_PLUG: {
                HostApplication.getApp().getPluginManager()
                        .enter(context, Constant.FROM_ID_GET_FRAGMENT_FROM_PLUG, intent.getExtras(), null);
            }
            case Constant.FROM_ID_MMKV: {
                HostApplication.getApp().getPluginManager()
                        .enter(context, Constant.FROM_ID_GET_FRAGMENT_FROM_PLUG, intent.getExtras(), null);
            }
            break;
            default:
                break;
        }
    }
}
