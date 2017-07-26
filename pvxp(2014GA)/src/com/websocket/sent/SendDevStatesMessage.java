package com.websocket.sent;

import java.io.IOException;

import javax.websocket.Session;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.websocket.config.ClientsManager;
import com.websocket.sent.interfac.ISendMessage;

/**
 * 发送设备状态
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-10-21
 */
@Service
public class SendDevStatesMessage implements ISendMessage {

	Logger log = Logger.getLogger(SendDevStatesMessage.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.websocket.sent.ISendMessage#sendTradeMsg(java.lang.String,
	 * boolean)
	 */
	@Override
	public void sendTradeMsg(String msg, boolean last) {

		for (int i = 0; i < ClientsManager.getClients().size(); i++) {
			Session sess = ClientsManager.getClients().get(i);

			if (sess.isOpen() && sess.getUserProperties().get("type").equals("devstates"))
				try {
					sess.getBasicRemote().sendText(msg, last);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("sendText()", e);
					try {
						sess.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						log.error("close()", e1);
					}
				}

		}
	}

	@Override
	public void sendDevStaMsg(String msg, boolean last) {
		// TODO Auto-generated method stub

	}

}
