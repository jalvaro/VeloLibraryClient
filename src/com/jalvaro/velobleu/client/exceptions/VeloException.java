package com.jalvaro.velobleu.client.exceptions;

public class VeloException extends Exception {
	public static int VELOBLEU_EXCEPTION_UNKNOWN = -1;
	// 0 is kept to error code OK
	public static int VELOBLEU_EXCEPTION_INVALID_USER = 1;
	public static int VELOBLEU_EXCEPTION_INVALID_SERVER_CALL = 2;
	public static int VELOBLEU_EXCEPTION_SERVER_ERROR = 3;
	public static int VELOBLEU_EXCEPTION_INVALID_PHONE_NUMBER = 4;
	public static int VELOBLEU_EXCEPTION_REGISTER_ERROR = 5;
	public static int VELOBLEU_EXCEPTION_CONNECTION_ERROR = 6;
	public static int VELOBLEU_EXCEPTION_VALIDATION_ERROR = 7;
	public static int VELOBLEU_EXCEPTION_INVALID_STREET = 8;
	public static int VELOBLEU_EXCEPTION_RADIOTAXI_REJECTED = 9;
	public static int VELOBLEU_EXCEPTION_SUBSCRIBER_USER_PASS_ERROR = 10;
	public static int VELOBLEU_EXCEPTION_SUBSCRIBER_VALIDATE_ERROR = 11;

	public final static int VELOBLEU_EXCEPTION_BDD_ERROR = 12;

	private static int messages[] = {};
	// private static int messages[] = {0};

	private int type;

	private static final long serialVersionUID = -7515953679233610305L;

	public VeloException(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static int getResourceMessage(int exception) {
		return messages[exception];
	}

	public int getResourceMessage() {
		return messages[type];
	}

}
