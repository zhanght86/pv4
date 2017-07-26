package com.lcjr.pvxp.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ����������Ϣ
 * 
 * @author wukp
 * @version Update
 * @date 2014-12-26
 */
public class UpdateObj implements Serializable {

	/**
	 * ������Ϣ
	 */
	private Map<String, String> sendMap = null;

	/**
	 * ������Ϣ�б�
	 */
	private List<Map<String, String>> recvList = new ArrayList<Map<String, String>>();

	/**
	 * 
	 * @param sendMap
	 * @param recvList
	 */
	public UpdateObj(Map<String, String> sendMap, List<Map<String, String>> recvList) {
		this.sendMap = sendMap;
		this.recvList = recvList;
	}

	public UpdateObj() {
	}

	@Override
	public String toString() {
		return "UpdateObj [sendMap=" + sendMap + ", recvList=" + recvList + "]";
	}

	/**
	 * @return the sendMap
	 */
	public Map<String, String> getSendMap() {
		return sendMap;
	}

	/**
	 * @param sendMap
	 *            the sendMap to set
	 */
	public void setSendMap(Map<String, String> sendMap) {
		this.sendMap = sendMap;
	}

	/**
	 * @return the recvList
	 */
	public List<Map<String, String>> getRecvList() {
		return recvList;
	}

	/**
	 * @param recvList
	 *            the recvList to set
	 */
	public void setRecvList(List<Map<String, String>> recvList) {
		this.recvList = recvList;
	}

}
