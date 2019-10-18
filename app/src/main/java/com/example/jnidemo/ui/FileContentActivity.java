package com.example.jnidemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.jnidemo.R;
import com.example.jnidemo.util.BackGroudHandler;
import com.example.jnidemo.util.FileUtil;

public class FileContentActivity extends Activity {
  private String filePath;
  private TextView fileContent;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_content_layout);
    fileContent = findViewById(R.id.file_content);
    fileContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    filePath = getIntent().getStringExtra("file_path");
    readFile();
  }

  private void readFile() {
    BackGroudHandler.execute(new Runnable() {
      @Override
      public void run() {
        final String content = FileUtil.getFileContent(filePath);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            fileContent.setText(content);
          }
        });
      }
    });
  }
}
