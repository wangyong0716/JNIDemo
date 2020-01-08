package com.example.jnidemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.kit.webdoor.WebDoorManager;
import com.example.xcrash.XCrash;

public class DemoApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    DoraemonKit.install(this);

    // H5任意门功能需要，非必须
    DoraemonKit.setWebDoorCallback(new WebDoorManager.WebDoorCallback() {
      @Override
      public void overrideUrlLoading(Context context, String s) {
        // 使用自己的H5容器打开这个链接
      }
    });
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    Log.i("QQQ", "application -> attachBaseContext");
    XCrash.init(this);
  }
}
