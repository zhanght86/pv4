package com.lcjr.pvxp.orm;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 * @author Œ‰¿§≈Ù
 * @version pvxp(2014GA)
 * @date 2014-9-10
 */
public class Devstate implements Serializable {
	private String devno;

	private String stateno;

	private String statelevel;

	private String statecode;

	private String bdate;

	private String btime;

	private String edate;

	private String etime;

	public void setDevno(String mdevno) {
		devno = mdevno;
	}

	public void setStateno(String mstateno) {
		stateno = mstateno;
	}

	public void setStatelevel(String mstatelevel) {
		statelevel = mstatelevel;
	}

	public void setStatecode(String mstatecode) {
		statecode = mstatecode;
	}

	public void setBdate(String mbdate) {
		bdate = mbdate;
	}

	public void setBtime(String mbtime) {
		btime = mbtime;
	}

	public void setEdate(String medate) {
		edate = medate;
	}

	public void setEtime(String metime) {
		etime = metime;
	}

	public String getDevno() {
		return devno;
	}

	public String getStateno() {
		return stateno;
	}

	public String getStatelevel() {
		return statelevel;
	}

	public String getStatecode() {
		return statecode;
	}

	public String getBdate() {
		return bdate;
	}

	public String getBtime() {
		return btime;
	}

	public String getEdate() {
		return edate;
	}

	public String getEtime() {
		return etime;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Devstate))
			return false;
		Devstate castOther = (Devstate) other;
		return new EqualsBuilder().append(this.getDevno(), castOther.getDevno()).append(this.getStateno(), castOther.getStateno()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getDevno()).append(getStateno()).toHashCode();
	}

}
