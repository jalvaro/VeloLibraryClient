package com.jalvaro.velolibrary.client.controllers;

import android.os.Message;

import com.jalvaro.velolibrary.client.application.VeloApp;
import com.jalvaro.velolibrary.client.asynctasks.UpdateAsyncTask;
import com.jalvaro.velolibrary.client.exceptions.VeloException;

public class UpdateController extends Controller{
	
	public UpdateController(VeloApp app) {
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
