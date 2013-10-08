package com.jalvaro.velobleu.client.controllers;

import android.os.Message;

import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.asynctasks.DeleteFavouriteStationAsyncTask;
import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.StationVO;

public class DeleteFavouriteStationController extends Controller {

	public DeleteFavouriteStationController(VeloApp app) {
		super(app);
	}

	public void delete(VeloHandler handler, int id) {
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

		new DeleteFavouriteStationAsyncTask(callback, getApplication(), id).execute();
	}

}
