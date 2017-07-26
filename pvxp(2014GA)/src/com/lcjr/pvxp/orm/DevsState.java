package com.lcjr.pvxp.orm;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-9-10
 */
public class DevsState implements Serializable {

	/**
	 * 
	 */
	private String devno;



	/**
	 * 设备状态 DS
	 */
	private String DevState;

	/**
	 * 打印机状态 PS
	 */
	private String Prt_State;

	/**
	 * 二代证状态 ICS
	 */
	private String IdCard_State;

	/**
	 * 凭条打印机状态 BS
	 */
	private String Bill_State;

	/**
	 * 刷卡器状态 BCS
	 */
	private String BrushCard_State;

	/**
	 * 开始日期
	 */
	private Timestamp  bdate;

	/**
	 * @return the devno
	 */
	public String getDevno() {
		return devno;
	}

	/**
	 * @param devno the devno to set
	 */
	public void setDevno(String devno) {
		this.devno = devno;
	}

	/**
	 * @return the devState
	 */
	public String getDevState() {
		return DevState;
	}

	/**
	 * @param devState the devState to set
	 */
	public void setDevState(String devState) {
		DevState = devState;
	}

	/**
	 * @return the prt_State
	 */
	public String getPrt_State() {
		return Prt_State;
	}

	/**
	 * @param prt_State the prt_State to set
	 */
	public void setPrt_State(String prt_State) {
		Prt_State = prt_State;
	}

	/**
	 * @return the idCard_State
	 */
	public String getIdCard_State() {
		return IdCard_State;
	}

	/**
	 * @param idCard_State the idCard_State to set
	 */
	public void setIdCard_State(String idCard_State) {
		IdCard_State = idCard_State;
	}

	/**
	 * @return the bill_State
	 */
	public String getBill_State() {
		return Bill_State;
	}

	/**
	 * @param bill_State the bill_State to set
	 */
	public void setBill_State(String bill_State) {
		Bill_State = bill_State;
	}

	/**
	 * @return the brushCard_State
	 */
	public String getBrushCard_State() {
		return BrushCard_State;
	}

	/**
	 * @param brushCard_State the brushCard_State to set
	 */
	public void setBrushCard_State(String brushCard_State) {
		BrushCard_State = brushCard_State;
	}

	/**
	 * @return the bdate
	 */
	public Timestamp getBdate() {
		return bdate;
	}

	/**
	 * @param bdate the bdate to set
	 */
	public void setBdate(Timestamp bdate) {
		this.bdate = bdate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Bill_State == null) ? 0 : Bill_State.hashCode());
		result = prime * result + ((BrushCard_State == null) ? 0 : BrushCard_State.hashCode());
		result = prime * result + ((DevState == null) ? 0 : DevState.hashCode());
		result = prime * result + ((IdCard_State == null) ? 0 : IdCard_State.hashCode());
		result = prime * result + ((Prt_State == null) ? 0 : Prt_State.hashCode());
		result = prime * result + ((bdate == null) ? 0 : bdate.hashCode());
		result = prime * result + ((devno == null) ? 0 : devno.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevsState other = (DevsState) obj;
		if (Bill_State == null) {
			if (other.Bill_State != null)
				return false;
		} else if (!Bill_State.equals(other.Bill_State))
			return false;
		if (BrushCard_State == null) {
			if (other.BrushCard_State != null)
				return false;
		} else if (!BrushCard_State.equals(other.BrushCard_State))
			return false;
		if (DevState == null) {
			if (other.DevState != null)
				return false;
		} else if (!DevState.equals(other.DevState))
			return false;
		if (IdCard_State == null) {
			if (other.IdCard_State != null)
				return false;
		} else if (!IdCard_State.equals(other.IdCard_State))
			return false;
		if (Prt_State == null) {
			if (other.Prt_State != null)
				return false;
		} else if (!Prt_State.equals(other.Prt_State))
			return false;
		if (bdate == null) {
			if (other.bdate != null)
				return false;
		} else if (!bdate.equals(other.bdate))
			return false;
		if (devno == null) {
			if (other.devno != null)
				return false;
		} else if (!devno.equals(other.devno))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DevsState [devno=" + devno + ", DevState=" + DevState + ", Prt_State=" + Prt_State + ", IdCard_State=" + IdCard_State + ", Bill_State=" + Bill_State + ", BrushCard_State="
				+ BrushCard_State + ", bdate=" + bdate + "]";
	}

 
	 
}
