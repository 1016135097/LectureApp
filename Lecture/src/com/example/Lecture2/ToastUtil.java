package com.example.Lecture2;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 荣坤 on 2015/12/6.
 */
public class ToastUtil {
    public static void toastLong(Context ct,String msg){
        Toast.makeText(ct,msg,Toast.LENGTH_LONG).show();
    }
    public static void toastShort(Context ct,String msg){
        Toast.makeText(ct,msg,Toast.LENGTH_SHORT).show();
    }
    public static void toastLong(Context ct,int StringId){
        Toast.makeText(ct,StringId,Toast.LENGTH_LONG).show();
    }
    public static void toastShort(Context ct,int StringId){
        Toast.makeText(ct,StringId,Toast.LENGTH_SHORT).show();
    }
}
