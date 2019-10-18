package com.example.jnidemo.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BackGroudHandler {
  private static ThreadPoolExecutor singleThreadExecutor
      = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
      new LinkedBlockingDeque<Runnable>());

  public static void execute(Runnable runnable) {
    singleThreadExecutor.execute(runnable);
  }

}
