package com.lcjr.pvxp.rmi.interfac;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import com.lcjr.pvxp.pojo.UpdateObj;

/**
 * 
 * @author
 * @version Update
 * @date 2014-12-26
 */
public interface IUpdate extends Remote {

	/**
	 * 
	 * @param Msgs
	 * @return
	 * @throws RemoteException
	 */
	public UpdateObj checkIn(Map<String, String> Msgs) throws RemoteException;
}