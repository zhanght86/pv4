package com.websocket.listen;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.websocket.config.ClientsManager;

/**
 * 聆听 设备状态信息
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-10-21
 */
@ServerEndpoint("/websocket/devStatesMsg")
public class DevStatesMessage {

	Logger log = Logger.getLogger(DevStatesMessage.class.getName());

	@OnOpen
	public void onOpen(Session session) {
		log.info("新监听接入：" + session.getId());
		session.getUserProperties().put("type", "devstates");
		ClientsManager.getClients().add(session);
	}

	@OnClose
	public void onClose(Session session) {
		log.info("监听离开：" + session.getId());
		ClientsManager.getClients().remove(session);
	}

	@OnError
	public void onError(Throwable t) {
		log.error("Sth", t);
	}

	/**
	 * Process a received pong. This is a NO-OP.
	 * 
	 * @param pm
	 *            Ignored.
	 */
	@OnMessage
	public void echoPongMessage(PongMessage pm) {
		// NO-OP
		System.out.println(pm.toString());
	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println(message);
	}
}
