package com.jalvaro.velobleu.client.controllers;

import android.os.Message;

import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.asynctasks.UpdateAsyncTask;
import com.jalvaro.velobleu.client.exceptions.VeloException;

public class MainController extends Controller{
	
	public MainController(VeloApp app) {
		super(app);
	}
	
	public void update(VeloHandler handler) {
		VeloCallback callback = new VeloCallback(handler) {
			
			@Override
			public void response(Message msg) {
				handler.handleMessage(msg);
			}
			
			@Override
			public void error(VeloException e) {
				handler.handleError(new Message());
			}
		};
		
		new UpdateAsyncTask(callback, getApplication()).execute();
	}
	
}
