package com.jalvaro.velobleu.client.io;

import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;

public abstract class Api {

	public abstract FleetVO getFleet() throws VeloException;
}
