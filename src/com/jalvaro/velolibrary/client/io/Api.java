package com.jalvaro.velolibrary.client.io;

import com.jalvaro.velolibrary.client.exceptions.VeloException;
import com.jalvaro.velolibrary.client.models.FleetVO;

public abstract class Api {

	public abstract FleetVO getFleet() throws VeloException;
}
