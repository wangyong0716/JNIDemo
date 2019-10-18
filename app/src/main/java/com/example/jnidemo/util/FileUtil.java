package com.example.jnidemo.util;

import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
  // 该方法实现取得目录内容
  public static List<String> getDirContent(String path) {
    File file = new File(path); // 用路径实例化一个文件对象
    List<String> fileList = new ArrayList<>();
    if (!file.exists()) {
      Log.i("QQQ", "file path not exists!");
      return fileList;
    }
    File[] files = file.listFiles(); // 重点:取得目录内所有文件列表
    if (files == null || files.length < 1) {
      Log.i("QQQ", "no file in the explicit dir!");
      return fileList;
    }

    for (int i = 0; i < files.length; i++) {
      // 判断是否为一个目录
      if (files[i].isDirectory()) {
        fileList.add("<dir>\t" + files[i].getName()); // 增加目录标识
      } else {
        fileList.add(files[i].getName());
      }
    }
    return fileList;
  }

  public static String getFileContent(String fileName) {
    File file = new File(fileName);
    BufferedReader reader = null;
    StringBuffer sbf = new StringBuffer();
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempStr;
      while ((tempStr = reader.readLine()) != null) {
        sbf.append("-> " + tempStr + "\n");
      }
      reader.close();
      return sbf.toString();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
    return sbf.toString();
  }

  /**
   * 删除文件夹以及目录下的文件
   *
   * @param filePath 被删除目录的文件路径
   * @return 目录删除成功返回true，否则返回false
   */
  public static boolean clearFiles(String filePath) {
    boolean flag = false;
    // 如果filePath不以文件分隔符结尾，自动添加文件分隔符
    if (!filePath.endsWith(File.separator)) {
      filePath = filePath + File.separator;
    }
    File dirFile = new File(filePath);
    if (!dirFile.exists() || !dirFile.isDirectory()) {
      return false;
    }
    flag = true;
    File[] files = dirFile.listFiles();
    // 遍历删除文件夹下的所有文件(包括子目录)
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        // 删除子文件
        flag = deleteFile(files[i].getAbsolutePath());
        if (!flag) break;
      } else {
        // 删除子目录
        flag = clearFiles(files[i].getAbsolutePath());
        if (!flag) break;
      }
    }
    if (!flag) return false;
    // 删除当前空目录
    return dirFile.delete();
  }

  /**
   * 删除单个文件
   * @param   filePath    被删除文件的文件名
   * @return 文件删除成功返回true，否则返回false
   */
  public static boolean deleteFile(String filePath) {
    File file = new File(filePath);
    if (file.isFile() && file.exists()) {
      return file.delete();
    }
    return false;
  }


}
