package com.jalvaro.velobleu.client.application;

import com.jalvaro.velobleu.client.io.VeloBleuApi;

public class VeloBleuApp extends VeloApp {

	public VeloBleuApp() {
		super();
		setVeloApi(new VeloBleuApi());
	}
}
