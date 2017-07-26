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
 * Զ�̵���RMI�����
 * 
 * @author ������
 * @version pvxp(2014GA)
 * @date 2014-9-11
 */
public class DeviceRmiServerImpl extends UnicastRemoteObject implements IDeviceMsgRmiServer {

	Logger log = Logger.getLogger(DeviceRmiServerImpl.class.getName());

	/**
	 * �����豸״̬
	 */
	@Resource
	private DevsStateDAO devsStateDAO;

	/**
	 * �����豸��Ϣ
	 */
	@Resource
	private DevInfoDAO devInfoDAO;

	/**
	 * ���뽻������
	 */
	@Resource
	private TdRecordsDAO tdRecordsDAO;

	/**
	 * ���ͽ�����Ϣ
	 */
	@Resource
	private ISendMessage sendTradeMessage;

	/**
	 * �����豸״̬��Ϣ
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

			log.info("��������:" + map.get("ENTITY.XML_TYPE_NAME"));
			
			// ��õ�ǰʱ��
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
			SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
			df.format(new Date());
			String currDate = df.format(new Date()).toString();
			String currTime = tf.format(new Date()).toString();
			map.put("Server_Time", currTime);
			map.put("Server_Date", currDate);

			
			// ��ѯ��ʷ ��Ҫ ���ݱ���
			if (map.get("ENTITY.XML_TYPE_NAME").equals("QUERY_PREFILL_LIST")) {
				result = tdRecordsDAO.selectGYProc(map);
				log.info("���׸�Ҫ��¼���:" + result);
				map.put("List", result);
				map.put("result", "success");
			}

			
			// ��ѯ��ʷ ��ϸ ���ݱ���
			else if (map.get("ENTITY.XML_TYPE_NAME").equals("QUERY_PREFILL_DETAIL")) {

				result = tdRecordsDAO.selectMXProc(map);
				log.info("������ϸ��¼���:" + result);
				map.put("result", "success");
			}

			// �豸״̬��Ϣ����
			else if (map.get("ENTITY.XML_TYPE_NAME").equals("DEVICE_STATE")) {

				// ���豸״̬���뵽���ݿ���
				result = devsStateDAO.insertProc(map);
				log.info("�豸״̬������:" + result);
				
				// �����ݿ���жԱȣ�����������ʹ�ã��޸İ汾�š�
				Devinfo devinfo = devInfoDAO.select("Devinfo", "DEVNO", "'" + map.get("DevNo").trim() + "'");

				// ����豸���������п���
				if (devinfo != null && devinfo.getBankid().trim().equals(map.get("Ref_No").trim()) && devinfo.getOpentag().trim().equals("1")) {
					map.put("Allow_Start", "1");
				} else {
					map.put("Allow_Start", "2");
				}

				map.put("result", "success");
				log.info("�豸�Ƿ�������:" + map.get("Allow_Start"));

				// ���͸�ǰ̨ҳ��
				JSONObject aJSONObject = new JSONObject();
				aJSONObject.putAll(map);
				System.out.println(map.get("Server_Time") + "  " + map.get("Server_Date"));
				log.info("���͸�web�˵Ľ��ױ��ģ�" + aJSONObject.toString());
				sendDevStatesMessage.sendTradeMsg(aJSONObject.toString(), true);

			}
			// �������Ϣ����
			else {
			log.info("��¼����:" + map.get("Allow_Start"));
				result = tdRecordsDAO.insertProc(map);
				log.info("���׼�¼���:" + result);
				JSONObject aJSONObject = new JSONObject();
		
			
				aJSONObject.putAll(map);
//				SaveObjectToFile aSave=new SaveObjectToFile();
//				aSave.writeObjectToFile(map, "c:/a.bat");
				
				log.info("���͸�web�˵Ľ��ױ��ģ�" + aJSONObject.toString());
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