package com.example.jnidemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

    checkPermission();

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
      case R.id.anr:
        anr();
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
//    javaCrash();
    jniTv.setText(JniUtil.sayHello());
  }

  private void nativeCrash() {
    jniTv.setText(JniUtil.testNativeCrash());
  }

  private void anr() {
    javaAnr();
//    nativeAnr();
  }

  private void javaAnr() {
    try{
    Thread.sleep(20000);
    }catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void nativeAnr() {
    jniTv.setText(JniUtil.testNativeAnr());
  }

  private void javaCrash() {
    Object o = null;
    int i = (int) o;
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
//    return getFilesDir().getPath();
        return Environment.getExternalStorageDirectory().getAbsolutePath();
  }

  private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

  public void checkPermission() {
    if (Build.VERSION.SDK_INT >= 23) {
      List<String> permissionStrs = new ArrayList<>();
      int hasWriteSdcardPermission =
          ContextCompat.checkSelfPermission(
              MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
      if (hasWriteSdcardPermission != PackageManager.PERMISSION_GRANTED) {
        permissionStrs.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
      }

      int hasCameraPermission =
          ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
      if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
        permissionStrs.add(Manifest.permission.CAMERA);
      }
      String[] stringArray = permissionStrs.toArray(new String[0]);
      if (permissionStrs.size() > 0) {
        requestPermissions(stringArray, REQUEST_CODE_ASK_PERMISSIONS);
        return;
      }
    }
  }

  // 权限设置后的回调函数，判断相应设置，requestPermissions传入的参数为几个权限，则permissions和grantResults为对应权限和设置结果
  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case REQUEST_CODE_ASK_PERMISSIONS:
        // 可以遍历每个权限设置情况
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // 这里写你需要相关权限的操作
        } else {
          Toast.makeText(MainActivity.this, "权限没有开启", Toast.LENGTH_SHORT).show();
        }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }
}
