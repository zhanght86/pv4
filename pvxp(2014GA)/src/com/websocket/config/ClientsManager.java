package com.websocket.config;

import java.util.LinkedList;

import javax.websocket.Session;

/**
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-10-21
 */
public class ClientsManager {

	private static final LinkedList<Session> clients = new LinkedList<Session>();

	/**
	 * @return the clients
	 */
	public static LinkedList<Session> getClients() {
		return clients;
	}

	/**
	 * @return the clients num
	 */
	public static int getClientsNum() {
		return clients.size();
	}

}
