package com.example.jnidemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.example.xcrash.XCrash;

public class DemoApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    Log.i("QQQ", "application -> attachBaseContext");
    XCrash.init(this);
  }
}
