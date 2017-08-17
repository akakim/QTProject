package tripath.com.qsalesprototypeapp.manager;

import android.content.Context;

/**
 * Created by SSLAB on 2017-06-23.
 */

public class SharedManager {

    public static void putBoolean( Context context,String key,boolean value){
        context.getSharedPreferences( "user",Context.MODE_PRIVATE ).edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean( Context context,String key){
        return context.getSharedPreferences("user",Context.MODE_PRIVATE).getBoolean(key,false);
    }


    public static void putString(Context context,String key,String value){
        context.getSharedPreferences("user",Context.MODE_PRIVATE).edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key){
        return context.getSharedPreferences("user",Context.MODE_PRIVATE).getString(key,"");
    }


    public static void putLong(Context context,String key,Long value){
        context.getSharedPreferences("user",Context.MODE_PRIVATE).edit().putLong(key,value).commit();
    }

    public static Long getLong(Context context,String key){
        return context.getSharedPreferences("user",Context.MODE_PRIVATE).getLong(key,-1);
    }

    public static void putInteager(Context context,String key,int value){
        context.getSharedPreferences("user",Context.MODE_PRIVATE).edit().putInt(key,value).commit();
    }

    public static int getInteger(Context context,String key){
        return context.getSharedPreferences("user",Context.MODE_PRIVATE).getInt(key,-1);
    }

    public static void clearUserInfo(Context context){
        context.getSharedPreferences("user",Context.MODE_PRIVATE).edit().clear().apply();
    }
}
