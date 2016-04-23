package org.example.homepi.model;

import android.app.Application;

public class Data extends Application{

	private String tempData;
	private String co2Data;
	private String pmData;
	
	public String  getTemPData() {
		return tempData;
	}
	public void setTempData(String tempData){
		this.tempData = tempData;
	}
	public String getCo2Data(){
		return co2Data;
	}
	public void setCo2Data(String co2Data){
		this.co2Data = co2Data;
	}
	public String getPmData(){
		return pmData;
	}
	public void setPmData(String pmData){
		this.pmData = pmData;
	}
}
