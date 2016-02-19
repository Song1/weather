package util;

import model.City;
import model.Country;
import model.Province;
import android.text.TextUtils;
import db.WeatherDB;

public class Utility {
	/*
	 * 解析和处理服务器返回的省级数据
	 */
	
	public synchronized static boolean handleProvincesResponse(WeatherDB
			weatherDB, String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces != null && allProvinces.length > 0){
				for(String p : allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvicneCode(array[0]);
					province.setProvinceName(array[1]);
					//将解析出来的数据存储到Province表
					weatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 解析和处理服务器返回的市级数据
	 */
	
	public synchronized static boolean handleCitiesResponse(WeatherDB
			weatherDB, String response, int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			if(allCities != null && allCities.length > 0){
				for(String c : allCities){
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					//将解析出来的数据存储到Province表
					weatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 解析和处理服务器返回的县级数据
	 */
	
	public synchronized static boolean handleCountriesResponse(WeatherDB
			weatherDB, String response, int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCountries = response.split(",");
			if(allCountries != null && allCountries.length > 0){
				for(String c : allCountries){
					String[] array = c.split("\\|");
					Country country = new Country();
					country.setCountryCode(array[0]);
					country.setCountryName(array[1]);
					country.setCityId(cityId);
					//将解析出来的数据存储到Province表
					weatherDB.saveCountry(country);
				}
				return true;
			}
		}
		return false;
	}
	

}
