package com.xinji.test;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author yuanchun
 * @content 本地渠道缓存工具类
 * @time 2016/08/14
 */
public class SharedPreferencesHelper {

    /**
     * 配置文件名称
     * 注：文件名称不能修改，聚合也要根据此获取配置
     */
    public static final String SettingFile = "xj_text";

    private Context mContext;

    protected SharedPreferences mSettings;

    protected SharedPreferences.Editor mEditor;

    private static SharedPreferencesHelper mInstance = null;




    private SharedPreferencesHelper(Context c) {
        mContext = c;
        mSettings = mContext.getSharedPreferences(SettingFile, 0);
        mEditor = mSettings.edit();
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SharedPreferencesHelper.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferencesHelper(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public void saveChannelType(String type){
        saveKey("channelType",type);
    }
    public String getChannelType(){
        return loadKey("channelType");
    }

    public boolean contains(String key) {
        return mSettings.contains(key);
    }

    public String loadKey(String key) {
        return mSettings.getString(key, "");
    }

    public String loadKey(String key,String defaultValue) {
        return mSettings.getString(key, defaultValue);
    }

    public void saveKey(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void removeKey(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public void clearkeys() {
        mEditor.clear();
        mEditor.commit();
    }

    public boolean loadBooleanKey(String key, boolean defValue) {
        return mSettings.getBoolean(key, defValue);
    }

    public void saveBooleanKey(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();

    }




    public int loadIntKey(String key, int defValue) {
        return mSettings.getInt(key, defValue);
    }

    public void saveIntKey(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public long loadLongKey(String key, long defValue) {
        return mSettings.getLong(key, defValue);
    }

    public void saveLongKey(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }


}
