package com.jalvaro.velobleu.client.controllers;

import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.jalvaro.velobleu.client.activities.AboutActivity;
import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.exceptions.VeloException;

public class Controller {
	public final static int TASK_KO = 0;
	public final static int TASK_OK = 1;
	
	private VeloApp mApp;
	
	public Controller(VeloApp app) {
		this.mApp = app;
	}
	
	public VeloApp getApplication() {
		return mApp;
	}
	
	public final static void startAboutActivity(Context context) {
		Intent intent = new Intent(context, AboutActivity.class);
		context.startActivity(intent);
	}
	
	public static abstract class VeloCallback {
		protected VeloHandler handler;

		public VeloCallback(VeloHandler handler) {
			this.handler = handler;
		}

		public abstract void response(Message msg);

		public abstract void error(VeloException e);
	}
	
	public static abstract class VeloHandler extends Handler {
		private AtomicBoolean isWorking;
		private VeloApp mApp;
		
		public VeloHandler(VeloApp app, AtomicBoolean isWorking) {
			this.isWorking = isWorking;
			this.mApp = app;
		}

		public void handleError(Message msg) {
			super.handleMessage(msg);
			Toast.makeText(mApp, "Error!!!", Toast.LENGTH_LONG).show();
		}
		
		public void setWorking(boolean isWorking) {
			this.isWorking.set(isWorking);
		}
	}
}
