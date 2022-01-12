package com.tencent.shadow.sample.plugin.app.lib;

import android.app.Application;

import com.tencent.shadow.sample.plugin.app.lib.gallery.cases.UseCaseManager;
import com.tencent.shadow.sample.plugin.app.lib.gallery.cases.entity.UseCase;
import com.tencent.shadow.sample.plugin.app.lib.gallery.cases.entity.UseCaseCategory;
import com.tencent.shadow.sample.plugin.app.lib.usecases.activity.TestActivityOnCreate;
import com.tencent.shadow.sample.plugin.app.lib.usecases.activity.TestActivityOptionMenu;
import com.tencent.shadow.sample.plugin.app.lib.usecases.activity.TestActivityOrientation;
import com.tencent.shadow.sample.plugin.app.lib.usecases.activity.TestActivityReCreate;
import com.tencent.shadow.sample.plugin.app.lib.usecases.activity.TestActivityReCreateBySystem;
import com.tencent.shadow.sample.plugin.app.lib.usecases.activity.TestActivitySetTheme;
import com.tencent.shadow.sample.plugin.app.lib.usecases.activity.TestActivityWindowSoftMode;
import com.tencent.shadow.sample.plugin.app.lib.usecases.context.ActivityContextSubDirTestActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.context.ApplicationContextSubDirTestActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.dialog.TestDialogActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.TestDialogFragmentActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.TestDynamicFragmentActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.fragment.TestXmlFragmentActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.host_communication.PluginUseHostClassActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.packagemanager.TestPackageManagerActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.provider.TestDBContentProviderActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.provider.TestFileProviderActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.receiver.TestDynamicReceiverActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.receiver.TestReceiverActivity;
import com.tencent.shadow.sample.plugin.app.lib.usecases.webview.WebViewActivity;

public class UseCaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initCase();
    }

    private static void initCase() {

        if (UseCaseManager.sInit) {
            throw new RuntimeException("不能重复调用init");
        }

        UseCaseManager.sInit = true;

        UseCaseCategory activityCategory = new UseCaseCategory("Activity测试用例", new UseCase[]{
                new TestActivityOnCreate.Case(),
                new TestActivityReCreate.Case(),
                new TestActivityReCreateBySystem.Case(),
                new TestActivityOrientation.Case(),
                new TestActivityWindowSoftMode.Case(),
                new TestActivitySetTheme.Case(),
                new TestActivityOptionMenu.Case(),
                new WebViewActivity.Case()
        });
        UseCaseManager.useCases.add(activityCategory);

        UseCaseCategory broadcastReceiverCategory = new UseCaseCategory("广播测试用例", new UseCase[]{
                new TestReceiverActivity.Case(),
                new TestDynamicReceiverActivity.Case()
        });
        UseCaseManager.useCases.add(broadcastReceiverCategory);


        UseCaseCategory providerCategory = new UseCaseCategory("ContentProvider测试用例", new UseCase[]{
                new TestDBContentProviderActivity.Case(),
                new TestFileProviderActivity.Case()
        });
        UseCaseManager.useCases.add(providerCategory);


        UseCaseCategory fragmentCategory = new UseCaseCategory("fragment测试用例", new UseCase[]{
                new TestDynamicFragmentActivity.Case(),
                new TestXmlFragmentActivity.Case(),
                new TestDialogFragmentActivity.Case()
        });
        UseCaseManager.useCases.add(fragmentCategory);

        UseCaseCategory dialogCategory = new UseCaseCategory("Dialog测试用例", new UseCase[]{
                new TestDialogActivity.Case(),
        });
        UseCaseManager.useCases.add(dialogCategory);

        UseCaseCategory packageManagerCategory = new UseCaseCategory("PackageManager测试用例", new UseCase[]{
                new TestPackageManagerActivity.Case(),
        });
        UseCaseManager.useCases.add(packageManagerCategory);


        UseCaseCategory contextCategory = new UseCaseCategory("Context相关测试用例", new UseCase[]{
                new ActivityContextSubDirTestActivity.Case(),
                new ApplicationContextSubDirTestActivity.Case(),
        });
        UseCaseManager.useCases.add(contextCategory);

        UseCaseCategory communicationCategory = new UseCaseCategory("插件和宿主通信相关测试用例", new UseCase[]{
                new PluginUseHostClassActivity.Case(),
        });
        UseCaseManager.useCases.add(communicationCategory);
    }
}
