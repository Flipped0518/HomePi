package org.example.homepi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.example.homepi.model.City;
import org.example.homepi.model.County;
import org.example.homepi.model.HomePiDB;
import org.example.homepi.model.Province;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
	/*
	 * 解析和处理服务器返回的省级数据
	 */
	public synchronized static boolean handleProvincesResponse(HomePiDB 
			homePiDB, String response){
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			if (allProvinces != null && allProvinces.length > 0) {
				for(String p : allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					//将解析出来的数据存储到Province表
					homePiDB.saveProvince(province);
				}
				return true;
				
			}
		}
		return false;
	}
	
	/*
	 * 解析和处理服务器返回的市级数据
	 */
	public static boolean handleCitiesResponse(HomePiDB homePiDB,
			String response, int provinceId){
		if(!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0) {
				for(String c : allCities){
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					//将解析出来的数据存储到City表
					homePiDB.saveCity(city);
				}
				return true;
				
			}
		}
		return false;
	}
	
	/*
	 * 解析和处理服务器返回的县级数据
	 */
	public static boolean handleCountiesResponse(HomePiDB homePiDB,
			String response, int cityId){
		if(!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties != null && allCounties.length > 0) {
				for(String c : allCounties){
					String[] array = c.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					//将解析出来的数据存储到City表
					homePiDB.saveCounty(county);
				}
				return true;
				
			}
		}
		return false;
	}
	
	/*
	 * 解析服务器返回的JSON数据，并将解析出的数据存储到本地
	 */
	public static void handleWeatherResponse(Context context, String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishTime = weatherInfo.getString("ptime");
			saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, 
					weatherDesp, publishTime);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中
	 */
	public static void saveWeatherInfo(Context context, String cityName, 
			String weatherCode, String temp1, String temp2, String weatherDesp, 
			String publishTime){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日",
				Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", simpleDateFormat.format(new Date()));
		editor.commit();
	}
	
	/*
	 * 解析服务器返回的空气质量的JSON数据，并将数据存储到本地
	 */
	public  static void  handleTempResponse(Context context, String response) {
		try{
			JSONObject jsonObject = new JSONObject(response);
			String tempData = jsonObject.getString("value");
			String timeData = jsonObject.getString("timestamp");
			saveTempInfo(context,tempData,timeData);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	

	public  static void  handleCo2Response(Context context, String response) {
		try{
			JSONObject jsonObject = new JSONObject(response);
			String Co2Data = jsonObject.getString("value");
			String timeData = jsonObject.getString("timestamp");
			saveCo2Info(context,Co2Data,timeData);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	
	public  static void  handlePmResponse(Context context, String response) {
		try{
			JSONObject jsonObject = new JSONObject(response);
			String PmData = jsonObject.getString("value");
			String timeData = jsonObject.getString("timestamp");
			savePmInfo(context,PmData,timeData);
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	public static void  saveTempInfo(Context context, String tempData, String timeData) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日hh时mm分",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putString("temp_data", tempData);
		editor.putString("time_data1", timeData);
		editor.commit();

	}
	
	public static void  saveCo2Info(Context context, String co2Data, String timeData) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日hh时mm分",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putString("co2_data", co2Data);
		editor.putString("time_data2", timeData);
		editor.commit();

	}
	
	public static void  savePmInfo(Context context, String pmData, String timeData) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日hh时mm分",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putString("pm_data", pmData);
		editor.putString("time_data3", timeData);
		editor.commit();

	}
	
}
