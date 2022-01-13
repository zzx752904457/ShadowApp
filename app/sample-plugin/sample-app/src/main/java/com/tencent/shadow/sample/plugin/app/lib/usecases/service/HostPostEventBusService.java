package com.tencent.shadow.sample.plugin.app.lib.usecases.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.common.lib.GloballLocationBean;
import com.common.lib.UserLoginStateEvent;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 宿主发送EventBus给插件需要启动的插件service
 */
public class HostPostEventBusService extends IntentService {
    public HostPostEventBusService() {
        super("HostPostEventBusService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String eventBusConfig = intent.getStringExtra("KEY_EVENT_BUS_JSON_CONFIG");
        try {
            JSONObject object = new JSONObject(eventBusConfig);
            String key = object.optString("key");
            switch (key) {
                case "loginStatus" : {
                    JSONObject value = object.optJSONObject("value");
                    Gson gson = new Gson();
                    UserLoginStateEvent bean = gson.fromJson(value.toString(), UserLoginStateEvent.class);
                    EventBus.getDefault().post(bean);
                }
                break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
