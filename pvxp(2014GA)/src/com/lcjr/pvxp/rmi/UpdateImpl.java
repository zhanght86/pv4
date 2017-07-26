package com.lcjr.pvxp.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lcjr.pvxp.pojo.UpdateObj;
import com.lcjr.pvxp.rmi.interfac.IUpdate;

public class UpdateImpl  implements IUpdate {

	protected UpdateImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public UpdateObj checkIn(Map<String, String> Msgs) throws RemoteException {

		System.err.println("开始反馈 升级信息");
		UpdateObj updateObj = new UpdateObj();
		List<Map<String, String>> recvList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("10.jpg", "F:/update/20150101093000/10.jpg");
		map.put("host-manager.2014-12-01.log", "F:/update/20150101093000/host-manager.2014-12-01.log");
		map.put("12.jpg", "F:/update/20150101093000/12.jpg");
		map.put("catalina.2014-12-01.log", "F:/update/20150101093000/catalina.2014-12-01.log");
		map.put("NAME", "20150101093000");
		map.put("DevVersion", "5");
		recvList.add(map);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("11.jpg", "F:/update/20150101093001/11.jpg");
		map2.put("localhost.2014-12-01.log", "F:/update/20150101093001/localhost.2014-12-01.log");
		map2.put("localhost_access_log.2014-11-19.txt", "F:/update/20150101093001/localhost_access_log.2014-11-19.txt");
		map2.put("localhost_access_log.2014-11-22.txt", "F:/update/20150101093001/localhost_access_log.2014-11-22.txt");
		map2.put("NAME", "20150101093001");
		map2.put("DevVersion", "6");
		recvList.add(map2);

		updateObj.setRecvList(recvList);
		System.err.println("结束  反馈 升级信息:"+updateObj.toString());
		return updateObj;
	}

}
