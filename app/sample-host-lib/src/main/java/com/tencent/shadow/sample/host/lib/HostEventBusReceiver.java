package com.tencent.shadow.sample.host.lib;


import com.common.lib.GloballLocationBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 这是一个将要打包到宿主中的类。作用：
 * 插件发送EventBus时，如果需要宿主接收，那么需要调用该类的getEventBus方法转发到宿主(因为插件中的EventBus消息载体对象和宿主中的有可能不一样，可以通过配置表json，key value的形式来对应)。
 */
public class HostEventBusReceiver {
    private static HostEventBusReceiver sInstance;

    public static void init() {
        sInstance = new HostEventBusReceiver();
    }

    public static HostEventBusReceiver getInstance() {
        return sInstance;
    }

    private HostEventBusReceiver() {
    }

    /**
     * 配置表演示 字符串
     * {
     *     "key": "changeAddress",
     *     "value": {
     *         "id": "1",
     *         "name": "上海市",
     *         "area": "中骏广场"
     *     }
     * }
     */
    public void getEventBus(String eventBusConfig) {
        try {
            JSONObject object = new JSONObject(eventBusConfig);
            String key = object.optString("key");
            switch (key) {
                case "changeAddress" : {
                    JSONObject value = object.optJSONObject("value");
                    Gson gson = new Gson();
                    GloballLocationBean bean = gson.fromJson(value.toString(), GloballLocationBean.class);
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
