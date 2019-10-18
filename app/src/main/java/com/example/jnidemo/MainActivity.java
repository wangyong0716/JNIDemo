package com.example.jnidemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.jnidemo.ui.FileContentActivity;
import com.example.jnidemo.util.BackGroudHandler;
import com.example.jnidemo.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private TextView jniTv;
  private TextView dir;
  private ListView fileList;
  private FileAdapter adapter;
  private String path;

  private Handler mainHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    jniTv = findViewById(R.id.jni);
    dir = findViewById(R.id.dir);
    fileList = findViewById(R.id.file_list);
    adapter = new FileAdapter(this);
    fileList.setAdapter(adapter);

    initPath();

    dir.setText(path);

    mainHandler = new Handler(getMainLooper());
  }

  private void initPath() {
    path = getLogDir() + "/tombstones";
    Log.i("QQQ", "path = " + path);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  public void handleClick(View view) {
    switch (view.getId()) {
      case R.id.crash:
        crash();
        break;
      case R.id.list:
        showFilePath();
        break;
      case R.id.clear:
        clearFiles();
        break;
      default:
        break;
    }
  }

  private void showFilePath() {
    BackGroudHandler.execute(
        new Runnable() {
          @Override
          public void run() {
            final List<String> fileList = FileUtil.getDirContent(path);
            mainHandler.post(
                new Runnable() {
                  @Override
                  public void run() {
                    adapter.setFileList(fileList);
                  }
                });
          }
        });
  }

  private void clearFiles() {
    BackGroudHandler.execute(
        new Runnable() {
          @Override
          public void run() {
            FileUtil.clearFiles(path);
            showFilePath();
          }
        });
  }

  private void viewFileContent(String fileName) {
    Intent intent = new Intent(this, FileContentActivity.class);
    intent.putExtra("file_path", path + "/" + fileName);
    startActivity(intent);
  }

  private void crash() {
    crashInJava();
    jniTv.setText(JniUtil.sayHello());
  }

  private void crashInJava() {
    Object o = null;
    int i = (int)o;
  }

  class FileAdapter extends BaseAdapter {
    private Context context;
    private List<String> fileList = new ArrayList<>();

    public FileAdapter(Context context) {
      this.context = context;
    }

    private void setFileList(List<String> list) {
      fileList.clear();
      fileList.addAll(list);
      notifyDataSetChanged();
    }

    @Override
    public int getCount() {
      return fileList.size();
    }

    @Override
    public String getItem(int position) {
      return fileList.get(position);
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      View view = View.inflate(context, R.layout.item_file_name_layout, null);
      final TextView nameTv = view.findViewById(R.id.name);
      nameTv.setText(getItem(position));
      view.setOnClickListener(
          new OnClickListener() {
            @Override
            public void onClick(View v) {
              viewFileContent(getItem(position));
            }
          });
      return view;
    }
  }

  public String getLogDir() {
    return getFilesDir().getPath();
//    return Environment.getExternalStorageDirectory().getAbsolutePath();
  }
}
