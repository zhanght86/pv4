package com.lcjr.pvxp.rmi.interfac;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * 
 * @author Œ‰¿§≈Ù
 * @version pvxp(2014GA)
 * @date 2014-9-5
 */
public interface IRmiServer {

	/**
	 * 
	 * @param msg
	 * @return
	 * @throws RemoteException
	 */
	public Map<String, String> sendMsg(Map<String, String> map) throws RemoteException;

}
