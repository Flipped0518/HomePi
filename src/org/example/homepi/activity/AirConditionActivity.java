package org.example.homepi.activity;

import org.example.homepi.R;
import org.example.homepi.util.HttpCallbackListener;
import org.example.homepi.util.HttpUtil;
import org.example.homepi.util.Utility;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;



public class AirConditionActivity extends Activity{
	
	private LinearLayout airConditionLayout;
	
	public static String temp_data;
	public static String co2_data;
	public static String pm_data;
	/*
	 * 用于显示温度值
	 */
	private TextView tempDataText;
	/*
	 * 用于显示CO2浓度值
	 */
	private TextView co2DataText;
	/*
	 * 用于显示PM2.5值
	 */
	private TextView pmDataText;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aircondition_layout);
		//初始化控件
		airConditionLayout = (LinearLayout) findViewById(R.id.air_condition);
		tempDataText = (TextView) findViewById(R.id.temp_data_text);
		co2DataText = (TextView) findViewById(R.id.co2_data_text);
		pmDataText = (TextView) findViewById(R.id.pm_data_text);
		
		getCurrentTemp();

		getCurrentCo2();

		getCurrentPm();

	}
	
	
	
	
	private void getCurrentTemp(){
		
		
		 String url="http://api.yeelink.net/v1.0/device/20896/sensor/38465/datapoints";
		 HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
			@Override
			public void onFinish(final String response) {
				// TODO Auto-generated method stub
				Utility.handleTempResponse(AirConditionActivity.this, response);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//showAirCondition();
						getTemp();
					}
				});
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						tempDataText.setText("error");
					}
				});
			}
		});
	}
	
	
	private void getCurrentCo2()
	{
		 String url="http://api.yeelink.net/v1.0/device/20896/sensor/387006/datapoints";
			
		 HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
			@Override
			public void onFinish(final String response) {
				// TODO Auto-generated method stub
				Utility.handleCo2Response(AirConditionActivity.this, response);			
				runOnUiThread(new Runnable() {
		
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//showAirCondition();
					
						getCo2();

					}
				});
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						co2DataText.setText("error");
					}
				});
			}
		});
	}
	
	
	private void getCurrentPm()
	{
		 String url="http://api.yeelink.net/v1.0/device/20896/sensor/387007/datapoints";
			
		 HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
			@Override
			public void onFinish(final String response) {
				Utility.handlePmResponse(AirConditionActivity.this, response);			
				runOnUiThread(new Runnable() {
		
					@Override
					public void run() {
						// TODO Auto-generated method stub
						getPm();

					}
				});
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						pmDataText.setText("error");
					}
				});
			}
		});
	}

    
	private void getTemp(){
		SharedPreferences prefs = PreferenceManager
    			.getDefaultSharedPreferences(this);
		temp_data = prefs.getString("temp_data", "");
		tempDataText.setText(temp_data+" ℃");
		
	}
	
	private void getCo2(){
		SharedPreferences prefs = PreferenceManager
    			.getDefaultSharedPreferences(this);
		co2_data = prefs.getString("co2_data", "");
		co2DataText.setText(co2_data+" ppm");
	}
	private void getPm(){
		SharedPreferences prefs = PreferenceManager
    			.getDefaultSharedPreferences(this);
		pm_data = prefs.getString("pm_data", "");
		pmDataText.setText(pm_data+" mg/m³");
	}
    

    
    public void showAirCondition(String tempData, String co2Data, String pmData) 
    {
//		new Thread(new Runnable() {
//			public void run() {
//				tempDataText.setText(temp_data+" ℃");
//		    	co2DataText.setText(co2_data+" ppm");
//		    	pmDataText.setText(pm_data+" mg/m³");
//			}
//		}).start();
    	airConditionLayout.setVisibility(View.VISIBLE);
    	tempDataText.setText(tempData+" ℃");
    	co2DataText.setText(co2Data+" ppm");
    	pmDataText.setText(pmData+" mg/m³");
	}

    
}
