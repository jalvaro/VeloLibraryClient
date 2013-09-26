package com.jalvaro.velobleu.client.controllers;

import android.os.Message;

import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.asynctasks.UpdateAsyncTask;
import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;

/**
 * 
 * @author jordi
 *
 * Aquesta classe no s«utilitza mes!!!
 * MainController la substitueix
 */

public class MapController extends Controller{
	private VeloHandler handler;
	
	public MapController(VeloApp app, VeloHandler handler) {
		super(app);
		this.handler = handler;
	}
	
	/*public void update(FleetVO fleetVO) {
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
	}*/
	
}
