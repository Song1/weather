package db;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Country;
import model.Province;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherDB {
	/*
	 * 数据库名称
	 */
	public static final String DB_NAME = "cool_weather";

	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;

	private static WeatherDB weatherDB;

	private SQLiteDatabase db;

	private WeatherDB(Context context) {
		WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME,
				null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/*
	 * 获取WeatherDB的实例
	 */

	public synchronized static WeatherDB getInstance(Context context) {
		if (weatherDB == null) {
			weatherDB = new WeatherDB(context);
		}
		return weatherDB;
	}

	/*
	 * 将Province实例存储到数据库
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvicneCode());
			db.insert("Province", null, values);
		}
	}

	/*
	 * 从数据库中读取全国所有的省份信息
	 */
	public List<Province> loadProvinces() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setProvicneCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/*
	 * 将City实例存储到数据库
	 */

	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	/*
	 * 从数据库中读取某省下所有的城市信息
	 */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?",
				new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/*
	 * 将Country实例存储到数据库
	 */

	public void saveCountry(Country country) {
		if (country != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", country.getCountryName());
			values.put("city_code", country.getCountryCode());
			values.put("province_id", country.getCityId());
			db.insert("City", null, values);
		}
	}

	/*
	 * 从数据库中读取某省下所有的城市信息
	 */
	public List<Country> loadCountries(int cityId) {
		List<Country> list = new ArrayList<Country>();
		Cursor cursor = db.query("Country", null, "city_id = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor
						.getColumnIndex("country_name")));
				country.setCountryCode(cursor.getString(cursor
						.getColumnIndex("country_code")));
				country.setCityId(cityId);
				list.add(country);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
