package com.lcjr.pvxp.tags;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspTagException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.MessageResources;

import com.lcjr.pvxp.model.*;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.util.*;

/**
 * <p><b>Title:</b> PowerViewXP</p><br>
 * <p><b>Description:</b> �豸��������ѡ���</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2010</p><br>
 * <p><b>Company:</b> �˳�������ҵ��(LCJR)</p><br>
 * @author xucc
 * @version 1.0 2010/09/26
 */
public final class DevftSelect extends TagSupport {
	
	/**��ӦID*/
	private String styleId = null;	
	/**��Ӧname<I>(����)</I>*/		
	private String property = null;	
	/**��Ӧstyle*/
	private String style = null;
	/**
	 * ��ʾ������Ĭ��Ϊ1  
	 * ����myeclips�е��Գ�����ԭʼ����Ϊ	private String size = null; ��null��
	 * ���⽫null��Ϊ��1��
	 */
	private String size = "1";
	/**onChange�¼���Ӧ����*/
	private String onChange = null;
	/**Ĭ��ѡ�еĲ��ű��*/
	private String defaultValue = null;
	/**��ʾ���ű�Ż��ǲ������ƣ�1-��ʾ���ƣ�������ʾ��ţ�Ĭ��Ϊ1*/
	private String showName = null;
	/**ѡ���ǩ����һ�����ӦֵΪ""�����Ϊ����û�б�ǩ*/
	private String key = null;
	/**������key�����Ժ�ñ�ǩ��Ӧ��ֵ*/
	private String keyValue=null;

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}  
	public String getKeyValue() {
		return this.keyValue;
	}
	public void setShowName(String showName){
		this.showName = showName;	
	}
	public String getShowName(){
		return this.showName;	
	}
	
	public void setStyleId(String styleId){
		this.styleId = styleId;	
	}
	public String getStyleId(){
		return this.styleId;	
	}
	
	public void setStyle(String style){
		this.style = style;	
	}
	public String getStyle(){
		return this.style;	
	}
	
	public void setProperty(String property){
		this.property = property;	
	}
	public String getProperty(){
		return this.property;	
	}
	
	public void setSize(String size){
		this.size = size;	
	}
	public String getSize(){
		return this.size;	
	}
	
	public void setOnChange(String onChange){
		this.onChange = onChange;	
	}
	public String getOnChange(){
		return this.onChange;	
	}
	public void setDefaultValue(String defaultValue){
		this.defaultValue = defaultValue;	
	}
	public String getDefaultValue(){
		return this.defaultValue;	
	}
	
	
	/*��ȡMessageResources����*******************/
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
	protected static MessageResources messages =
		MessageResources.getMessageResources(
			"org.apache.struts.taglib.bean.LocalStrings");
	/********************************************/
	
	
	public void release() {
		styleId = null;
		property = null;
		size = null;
		onChange = null;
		key = null;
		/*��ȡMessageResources����*******************/
		bundle = Globals.MESSAGES_KEY;
		localeKey = Globals.LOCALE_KEY;
	}


	public int doEndTag() throws JspException {
		
		/*��ȡMessageResources����*******************/
		String key = this.key;
		String message = "";
		if( key!=null&&key.trim().length()>0 ){
			message =
				TagUtils.getInstance().message(
					pageContext,
					this.bundle,
					this.localeKey,
					key,
					null);
	            
			if (message == null) {
				JspException e =
					new JspException(
						messages.getMessage("message.message", "\"" + key + "\""));
				TagUtils.getInstance().saveException(pageContext, e);
				throw e;
			}
		}
		/**********************************************/
		
		
		
		JspWriter out = pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer outstr = new StringBuffer();
		String startstr = "";
		String selected = "";
		if( showName==null||!showName.equals("0") ) showName = "1";
		startstr = "<select name=\""+property+"\" onmousewheel=\"return false\" ";
		if( styleId!=null )	startstr += "id=\""+styleId+"\" ";
		
		try{
			if(size==null||size.length()==1){size="1";}
			size = String.valueOf(Integer.parseInt( size.trim() ) );
			startstr += "size=\""+size+"\" ";
		}catch(Exception e){}
			
		if( onChange!=null ){
			startstr += "onChange=\""+onChange+"\" ";
		}
		
		if( style!=null ){
			startstr += "style=\""+style+"\"";
		}
		
		startstr += ">\n";
		outstr.append(startstr);
		if( message!=null&&message.length()>0 ){
			if( keyValue==null ) keyValue="";
			outstr.append("<option value=\""+keyValue+"\">"+message+"</option>\n");
		}
		
		try{
		  
			PubUtil myPubUtil = new PubUtil();
			DevftModel myDevftModel = new DevftModel();
			List myDevftList = myDevftModel.getDevftList();

			if( ( myDevftList != null ) && ( !myDevftList.isEmpty() ) ){
				CharSet myCharSet = new CharSet();
				int len = myDevftList.size();
				String displaystr = "";
				if(defaultValue==null) defaultValue = "";
				for(int i=0;i<len;i++){
					Devftinfo tmpdevft = (Devftinfo)myDevftList.get(i);
					if( tmpdevft!=null ){
						String devftname = myCharSet.db2web(myPubUtil.dealNull(tmpdevft.getDevftname())).trim();
						String devftno = myPubUtil.dealNull(tmpdevft.getDevftno()).trim();
						if( showName.equals("1") ) displaystr = devftname;
						else displaystr = devftno;
						
						if( devftno.equals(defaultValue) ) selected = " selected";
						else selected = "";
						
						outstr.append("<option value=\""+devftno+"\" typename=\""+devftname+"\""+selected+">"+displaystr+"</option>\n");
					}
				}
			}
			
		}catch (Exception ex){
			throw new JspTagException("IOException:" + ex.toString());
		}
	 	outstr.append("</select>\n");
	 	try{
	 		out.write(outstr.toString());
	 	}catch(Exception e){
	 		try{
	 			out.write(startstr+"</select>");
	 		}catch(Exception ex){}
	 	}
	 	//TagUtils.getInstance().write(pageContext, message);
		return super.doEndTag();
	}
}
