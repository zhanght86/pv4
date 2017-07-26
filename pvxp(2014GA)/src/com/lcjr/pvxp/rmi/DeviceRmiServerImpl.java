package com.lcjr.pvxp.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.hibernate.HibernateException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import com.lcjr.pvxp.bean.interfac.DevInfoDAO;
import com.lcjr.pvxp.bean.interfac.DevsStateDAO;
import com.lcjr.pvxp.bean.interfac.TdRecordsDAO;
import com.lcjr.pvxp.orm.Devinfo;
import com.lcjr.pvxp.rmi.interfac.IDeviceMsgRmiServer;
import com.lcjr.pvxp.util.SaveObjectToFile;
import com.websocket.sent.interfac.ISendMessage;

/**
 * 远程调用RMI服务端
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-9-11
 */
public class DeviceRmiServerImpl extends UnicastRemoteObject implements IDeviceMsgRmiServer {

	Logger log = Logger.getLogger(DeviceRmiServerImpl.class.getName());

	/**
	 * 处理设备状态
	 */
	@Resource
	private DevsStateDAO devsStateDAO;

	/**
	 * 处理设备信息
	 */
	@Resource
	private DevInfoDAO devInfoDAO;

	/**
	 * 插入交易类型
	 */
	@Resource
	private TdRecordsDAO tdRecordsDAO;

	/**
	 * 发送交易信息
	 */
	@Resource
	private ISendMessage sendTradeMessage;

	/**
	 * 发送设备状态信息
	 */
	@Resource
	private ISendMessage sendDevStatesMessage;

	protected DeviceRmiServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Map<String, String> sendMsg(Map<String, String> map) throws RemoteException {
		// TODO Auto-generated method stub
		String result = "success";
		try {

			log.info("交易类型:" + map.get("ENTITY.XML_TYPE_NAME"));
			
			// 获得当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
			df.format(new Date());
			String currDate = df.format(new Date()).toString();
			String currTime = tf.format(new Date()).toString();
			map.put("Server_Time", currTime);
			map.put("Server_Date", currDate);

			
			// 查询历史 概要 数据报文
			if (map.get("ENTITY.XML_TYPE_NAME").equals("QUERY_PREFILL_LIST")) {
				result = tdRecordsDAO.selectGYProc(map);
				log.info("交易概要记录结果:" + result);
				map.put("List", result);
				map.put("result", "success");
			}

			
			// 查询历史 详细 数据报文
			else if (map.get("ENTITY.XML_TYPE_NAME").equals("QUERY_PREFILL_DETAIL")) {

				result = tdRecordsDAO.selectMXProc(map);
				log.info("交易明细记录结果:" + result);
				map.put("result", "success");
			}

			// 设备状态信息报文
			else if (map.get("ENTITY.XML_TYPE_NAME").equals("DEVICE_STATE")) {

				// 将设备状态存入到数据库中
				result = devsStateDAO.insertProc(map);
				log.info("设备状态保存结果:" + result);
				
				// 与数据库进行对比，存在且允许使用，修改版本号。
				Devinfo devinfo = devInfoDAO.select("Devinfo", "DEVNO", "'" + map.get("DevNo").trim() + "'");

				// 如果设备存在且运行开启
				if (devinfo != null && devinfo.getBankid().trim().equals(map.get("Ref_No").trim()) && devinfo.getOpentag().trim().equals("1")) {
					map.put("Allow_Start", "1");
				} else {
					map.put("Allow_Start", "2");
				}

				map.put("result", "success");
				log.info("设备是否允许开机:" + map.get("Allow_Start"));

				// 发送给前台页面
				JSONObject aJSONObject = new JSONObject();
				aJSONObject.putAll(map);
				System.out.println(map.get("Server_Time") + "  " + map.get("Server_Date"));
				log.info("发送给web端的交易报文：" + aJSONObject.toString());
				sendDevStatesMessage.sendTradeMsg(aJSONObject.toString(), true);

			}
			// 填单交易信息报文
			else {
			log.info("记录交易:" + map.get("Allow_Start"));
				result = tdRecordsDAO.insertProc(map);
				log.info("交易记录结果:" + result);
				JSONObject aJSONObject = new JSONObject();
		
			
				aJSONObject.putAll(map);
//				SaveObjectToFile aSave=new SaveObjectToFile();
//				aSave.writeObjectToFile(map, "c:/a.bat");
				
				log.info("发送给web端的交易报文：" + aJSONObject.toString());
				sendTradeMessage.sendTradeMsg(aJSONObject.toString(), true);

//				Map<String, String> map1=(Map<String, String>)aSave.readObjectFromFile("c:/a.bat");
//				map1.get("Allow_Start");
				
			}

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			log.error(e);
			result = "result";
		}
		return map;
	}

}