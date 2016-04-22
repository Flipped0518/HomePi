package org.example.homepi.util;

import android.text.TextUtils;
import org.example.homepi.model.City;
import org.example.homepi.model.County;
import org.example.homepi.model.HomePiDB;
import org.example.homepi.model.Province;

public class Utility {
	/*
	 * �����ʹ�����������ص�ʡ������
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
					//���������������ݴ洢��Province��
					homePiDB.saveProvince(province);
				}
				return true;
				
			}
		}
		return false;
	}
	
	/*
	 * �����ʹ�����������ص��м�����
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
					//���������������ݴ洢��City��
					homePiDB.saveCity(city);
				}
				return true;
				
			}
		}
		return false;
	}
	
	/*
	 * �����ʹ�����������ص��ؼ�����
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
					//���������������ݴ洢��City��
					homePiDB.saveCounty(county);
				}
				return true;
				
			}
		}
		return false;
	}
}
