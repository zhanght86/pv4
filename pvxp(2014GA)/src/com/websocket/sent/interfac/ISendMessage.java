package com.websocket.sent.interfac;


/**
 * 用于发送给web端的接口，主要是交易实时监控和设备状态实时监控
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-10-22
 */
public interface ISendMessage {

	/**
	 * 
	 * @param msg
	 *            信息
	 * @param last
	 *            判断
	 */
	public abstract void sendTradeMsg(String msg, boolean last);

	/**
	 * 
	 * @param msg
	 *            设备状态信息
	 * @param last
	 *            判断
	 */
	public abstract void sendDevStaMsg(String msg, boolean last);

}