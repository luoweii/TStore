/**
 * 
 */
package com.luowei.tstore.config;

import android.os.Environment;

import com.luowei.tstore.App;

/**
 * @author 骆巍
 * @date 2015-2-1
 */
public class AppConfig {
	/**
	 * 服务器地址
	 */
	public static final String HTTP_SERVER = "http://apis.baidu.com/apistore/";
	/**
	 * 服务器端口
	 */
	public static final int HTTP_SERVER_PORT = 80;

	/**
	 * api服务key
	 */
	public static final String API_KEY = "54e612502a38ee6ea7e28404dddaf9c6";

	/**
	 * 默认照片保存路径
	 */
	public static final String DEFUALT_PATH_PHOTO = Environment.getExternalStorageDirectory() +"/"+ App.context.getPackageName()+"/photo/";

}
