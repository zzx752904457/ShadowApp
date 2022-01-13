package com.tencent.shadow.sample.plugin.app.lib.usecases.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.common.lib.UserLoginStateEvent;
import com.tencent.shadow.sample.plugin.app.lib.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ManagerFragment extends PluginFragment {

    private final AnimatorSet set = new AnimatorSet();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("ManagerFragment", "onCreateView");
        EventBus.getDefault().register(this);
        return pluginInflater.inflate(R.layout.fragment_manager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("ManagerFragment", "onViewCreated");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ManagerFragment", "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("ManagerFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("ManagerFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("ManagerFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("ManagerFragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        set.cancel();
        Log.e("ManagerFragment", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ManagerFragment", "onDestroy");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserLoginStateEvent bean) {
        set.cancel();
        TextView textView = getView().findViewById(R.id.tv_plugin_fragment);
        textView.setText("插件fragment\n已经接收到宿主的EventBus");
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 1.5f, 1f);
        animatorX.setRepeatCount(Animation.INFINITE);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 1.5f, 1f);
        animatorY.setRepeatCount(Animation.INFINITE);
        set.setDuration(800);
        set.play(animatorX).with(animatorY);
        set.start();
    }
}
