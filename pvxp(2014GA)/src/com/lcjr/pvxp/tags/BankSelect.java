package com.lcjr.pvxp.tags;

import java.util.Vector;
import javax.servlet.http.*;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.util.*;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * <p>
 * 用于在页面中的下拉列表框的显示，大小，属性，属性值，列 <b>Description:</b> 机构下拉选择框 <b>Copyright:</b>
 * Copyright (c) 2005 <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 杨旭
 * @version 1.0 2005/03/16
 */
public final class BankSelect extends TagSupport {
	
	
	/** 对应ID */
	private String styleId = null;
	
	
	
	/** 对应name<I>(必须)</I> */
	private String property = null;
	
	
	
	/** 对应style */
	private String style = null;
	
	
	
	/** 当前开始的根机构，默认为当前操作员所属机构 */
	private String currentBankid = null;
	
	
	
	/** 是否包含当前机构，可选值：true,false，默认true */
	private String incSelf = null;
	
	
	
	/** size的值用来设置下拉列表框中显示的信息行数，默认为1 */
	private String size = "1";
	
	
	
	/** onChange事件对应代码 */
	private String onChange = null;
	
	
	
	/** 默认选中的机构编号 */
	private String defaultValue = null;
	
	
	
	/** 选择标签（第一项），对应值为""，如果为空则没有标签 */
	private String key = null;
	
	
	
	/** 设置了key属性以后该标签对应的值 */
	private String keyValue = null;
	
	
	
	public String getKey() {
		return (this.key);
	}
	
	
	public void setKey(String key) {
		this.key = key;
	}
	
	
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
	
	public String getKeyValue() {
		return (this.keyValue);
	}
	
	
	public void setCurrentBankid(String currentBankid) {
		this.currentBankid = currentBankid;
	}
	
	
	public String getCurrentBankid() {
		return this.currentBankid;
	}
	
	
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	
	
	public String getStyleId() {
		return this.styleId;
	}
	
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	
	public String getStyle() {
		return this.style;
	}
	
	
	public void setIncSelf(String incSelf) {
		this.incSelf = incSelf;
	}
	
	
	public String getIncSelf() {
		return this.incSelf;
	}
	
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	
	public String getProperty() {
		return this.property;
	}
	
	
	public void setSize(String size) {
		this.size = size;
	}
	
	
	public String getSize() {
		return this.size;
	}
	
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	
	
	public String getOnChange() {
		return this.onChange;
	}
	
	
	
	
	/** 获取MessageResources所需***************** */
	protected String bundle = null;
	
	
	
	public String getBundle() {
		return (this.bundle);
	}
	
	
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	
	
	
	protected static final Locale defaultLocale = Locale.getDefault();
	
	
	protected String localeKey = Globals.LOCALE_KEY;
	
	
	
	public String getLocale() {
		return (this.localeKey);
	}
	
	
	public void setLocale(String localeKey) {
		this.localeKey = localeKey;
	}
	
	
	
	protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.bean.LocalStrings");
	
	
	
	
	/**
	 * <font color=red size='5' >重新设置数据</font>
	 */
	public void release() {
		styleId = null;
		property = null;
		size = "1";
		onChange = null;
		/**
		 * 获取MessageResources所需
		 */
		bundle = Globals.MESSAGES_KEY;
		localeKey = Globals.LOCALE_KEY;
	}
	
	
	
	/**
	 * 
	 */
	public int doEndTag() throws JspException {
		// 获取MessageResources所需
		String key = this.key;
		String message = "";
		if (key != null && key.trim().length() > 0) {
			message = TagUtils.getInstance().message(pageContext, this.bundle, this.localeKey, key, null);
			if (message == null) {
				JspException e = new JspException(messages.getMessage("message.message", "\"" + key + "\""));
				TagUtils.getInstance().saveException(pageContext, e);
				throw e;
			}
		}
		
		JspWriter out = pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer outstr = new StringBuffer();
		String startstr = "";
		String selected = "";
		startstr = "<select name=\"" + property + "\" onmousewheel=\"return false\" ";
		if (styleId != null)
			startstr += "id=\"" + styleId + "\" ";
		
		try {
			// 为解决myeclips运行时进入编辑状态而添加if else 语句,size的值用来设置下拉列表框中显示的信息条数
			
			size = String.valueOf(Integer.parseInt(size.trim()));
			startstr += "size=\"" + size + "\" ";
			
		} catch (Exception e) {
			
		}
		
		if (onChange != null) {
			startstr += "onChange=\"" + onChange + "\" ";
		}
		
		if (style != null) {
			startstr += "style=\"" + style + "\"";
		}
		
		startstr += ">\n";
		
		outstr.append(startstr);
		if (message != null && message.length() > 0) {
			if (keyValue == null)
				keyValue = "";
			outstr.append("<option value=\"" + keyValue + "\">" + message + "</option>\n");
		}
		try {
			
			PubUtil myPubUtil = new PubUtil();
			BankinfoModel myBankinfoModel = new BankinfoModel();
			String mybankid = "";
			String curOperPower = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_AUTHLIST, request)).trim();
			if (currentBankid == null || currentBankid.trim().length() == 0) {
				mybankid = myPubUtil.dealNull(myPubUtil.getValueFromCookie(Constants.COOKIE_OPER_BANKID, request)).trim();
			} else {
				mybankid = currentBankid.trim();
			}
			int intincSelf = 1;
			if (incSelf != null && incSelf.equalsIgnoreCase("false"))
				intincSelf = 0;
			Vector bankVector = myBankinfoModel.getSubBank(mybankid, intincSelf);
			
			if ((bankVector != null) && (!bankVector.isEmpty())) {
				CharSet myCharSet = new CharSet();
				int len = bankVector.size();
				int minbanktag = 1;
				if (len > 0) {
					Bankinfo tmpbank = (Bankinfo) bankVector.get(0);
					String fisrtBankid = tmpbank.getBankid().trim();
					String tmpbankname = myCharSet.db2web(tmpbank.getBanknm());
					String tmpbanktag = tmpbank.getBanktag().trim();
					String tmpbankaddr = myCharSet.db2web(tmpbank.getBankaddr()).trim();
					if (tmpbanktag == null) {
						minbanktag = 1;
					} else {
						try {
							minbanktag = Integer.parseInt(tmpbanktag);
						} catch (Exception e) {
							minbanktag = 1;
						}
					}
					if (defaultValue == null)
						defaultValue = "";
					if (fisrtBankid.equals(defaultValue))
						selected = " selected";
					
					outstr.append("<option value=\"" + fisrtBankid + "\" banktag=\"" + tmpbanktag + "\" bankaddr=\"" + tmpbankaddr + "\"" + selected + ">" + tmpbankname.trim() + "</option>\n");
					
				}
				for (int i = 1; i < len; i++) {
					Bankinfo tmpbank = (Bankinfo) bankVector.get(i);
					String tmpbankname = myCharSet.db2web(tmpbank.getBanknm());
					String tmpbanktag = tmpbank.getBanktag().trim();
					String tmpbankaddr = myCharSet.db2web(myPubUtil.dealNull(tmpbank.getBankaddr())).trim();
					int thisbanktag = 1;
					if (tmpbanktag == null) {
						thisbanktag = 1;
					} else {
						try {
							thisbanktag = Integer.parseInt(tmpbanktag);
						} catch (Exception e) {
							thisbanktag = 1;
						}
					}
					int spacenum = thisbanktag - minbanktag;
					String spacestr = "";
					for (int j = 0; j < spacenum; j++) {
						spacestr += "&nbsp;&nbsp;";
					}
					if (spacestr.length() > 0)
						spacestr += "|-";
					String thisbankid = tmpbank.getBankid().trim();
					if (thisbankid.equals(defaultValue))
						selected = " selected";
					else
						selected = "";
					outstr.append("<option value=\"" + thisbankid + "\" banktag=\"" + tmpbanktag + "\" bankaddr=\"" + tmpbankaddr + "\"" + selected + ">" + spacestr + tmpbankname.trim()
							+ "</option>\n");
				}
			}
		} catch (Exception ex) {
			throw new JspTagException("IOException:" + ex.toString());
		}
		outstr.append("</select>\n");
		try {
			out.write(outstr.toString());
		} catch (Exception e) {
			try {
				out.write(startstr + "</select>");
			} catch (Exception ex) {

			}
		}
		return super.doEndTag();
	}
}
