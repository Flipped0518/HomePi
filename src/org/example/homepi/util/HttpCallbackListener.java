package org.example.homepi.util;

public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e); 

}
