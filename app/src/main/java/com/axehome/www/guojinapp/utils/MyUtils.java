package com.axehome.www.guojinapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.axehome.www.guojinapp.GuoJinApp;
import com.axehome.www.guojinapp.beans.User;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static java.util.regex.Pattern.matches;

/**
 * Created by axehome on 2017/12/2.
 */

public class MyUtils {
    public static void putSpuString(String key, String value) {
        if (SPUtils.contains(GuoJinApp.getContext(), key)) {
            SPUtils.remove(GuoJinApp.getContext(), key);
            SPUtils.put(GuoJinApp.getContext(), key, value);
        } else {
            SPUtils.put(GuoJinApp.getContext(), key, value);
        }
    }

    public static void cleanVideoCacheDir(Context context) throws IOException {
        File videoCacheDir = getVideoCacheDir(context);
        cleanDirectory(videoCacheDir);
    }

    public static File getVideoCacheDir(Context context) {
        return new File(context.getExternalCacheDir(), "video-cache");
    }

    private static void cleanDirectory(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        File[] contentFiles = file.listFiles();
        if (contentFiles != null) {
            for (File contentFile : contentFiles) {
                delete(contentFile);
            }
        }
    }

    private static void delete(File file) throws IOException {
        if (file.isFile() && file.exists()) {
            deleteOrThrow(file);
        } else {
            cleanDirectory(file);
            deleteOrThrow(file);
        }
    }

    private static void deleteOrThrow(File file) throws IOException {
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new IOException(String.format("File %s can't be deleted", file.getAbsolutePath()));
            }
        }
    }

    public static User getUser() {
        return MyUtils.Touser((String) SPUtils.get("user", ""));
    }

    public static User Touser(String str) {
        Gson gson = new Gson();
        User user = gson.fromJson(str, User.class);
        return user;
    }

    public static String getJson(Map<String, String> map) {
        Gson gson = new Gson();
        String sss = gson.toJson(map);
        return sss;
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void disableSubControls(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                if (v instanceof Spinner) {
                    Spinner spinner = (Spinner) v;
                    spinner.setClickable(false);
                    spinner.setEnabled(false);
                } else if (v instanceof ListView) {
                    ((ListView) v).setClickable(false);
                    ((ListView) v).setEnabled(false);
                } else {
                    disableSubControls((ViewGroup) v);
                }
            } else if (v instanceof EditText) {
                ((EditText) v).setEnabled(false);
                ((EditText) v).setClickable(false);
            } else if (v instanceof Button) {
                ((Button) v).setEnabled(false);
            } else if (v instanceof TextView) {
                ((TextView) v).setEnabled(false);
                ((TextView) v).setClickable(false);
            } else if (v instanceof ImageView) {
                ((ImageView) v).setEnabled(false);
                ((ImageView) v).setClickable(false);
            }
        }
    }

    /**
     * 生成一个startNum 到 endNum之间的随机数(不包含endNum的随机数)
     *
     * @param startNum
     * @param endNum
     * @return
     */
    public static int getNum(int startNum, int endNum) {
        if (endNum > startNum) {
            Random random = new Random();
            return random.nextInt(endNum - startNum) + startNum;
        }
        return 0;
    }

    public static void closeInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            //第一个参数为需要关闭的Editext
             imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
//            imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*** 验证电话号码
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsTelephone(String str) {
        String regex = "^0(10|2[0-5789]-|\\d{3})-?\\d{7,8}$";
//        Logger.e("TAG", "phoneNum4:" + matches(regex, str));
        return matches(regex, str);
    }

     public static boolean isMobileNO(String mobiles) {
        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[01256789]\\d{8}|17[0678]\\d{8}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
     }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
