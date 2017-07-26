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
 * <p><b>Description:</b> 设备下拉选择框</p><br>
 * <p><b>Copyright:</b> Copyright (c) 2005</p><br>
 * <p><b>Company:</b> 浪潮金融事业部(LCJR)</p><br>
 * @author 杨旭
 * @version 1.0 2005/03/16
 */
public final class DevSelect extends TagSupport {
	
	/**对应ID*/
	private String styleId = null;
	/**对应name<I>(必须)</I>*/
	private String property = null;
	/**机构范围*/
	private String bankRange = null;
	/**对应style*/
	private String style = null;
	/**显示行数，默认为1*/
	private String size = null;
	/**onChange事件对应代码*/
	private String onChange = null;
	/**选择标签（第一项），对应值为""，如果为空则没有标签*/
	private String key = null;
	/**设置了key属性以后该标签对应的值*/
	private String keyValue=null;
  
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
	
	public void setBankRange(String bankRange){
		this.bankRange = bankRange;	
	}
	public String getBankRange(){
		return this.bankRange;	
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
	/*获取MessageResources所需*******************/
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
		/*获取MessageResources所需*******************/
		bundle = Globals.MESSAGES_KEY;
		localeKey = Globals.LOCALE_KEY;
	}
	
	public int doEndTag() throws JspException{
		/*获取MessageResources所需*******************/
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
	 	startstr = "<select name=\""+property+"\" onmousewheel=\"return false\" ";
		if( styleId!=null )	startstr += "id=\""+styleId+"\" ";
		
		try{
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
			DevinfoModel myDevinfoModel = new DevinfoModel();

			List devList = myDevinfoModel.getDevListOfBank(bankRange);
			 
			
			if( ( devList != null ) && ( !devList.isEmpty() ) ){
				int len = devList.size();
				CharSet myCharSet = new CharSet();
				for(int i=0;i<len;i++){
					Devinfo tmpdev = (Devinfo)devList.get(i);
					String tmpdevno = tmpdev.getDevno().trim();
					String tmptypeno = tmpdev.getTypeno().trim();
					String tmptypestate = tmpdev.getTypestate().trim();
					String tmpdevip = tmpdev.getDevip().trim();
					String tmpdevaddr = myCharSet.db2web(myPubUtil.dealNull(tmpdev.getDevaddr())).trim();
				
					outstr.append("<option value=\""+tmpdevno+"\" typeno=\""+tmptypeno+"\" typestate=\""+tmptypestate+"\" devip=\""+tmpdevip+"\" devaddr=\""+tmpdevaddr+"\">"+tmpdevno+"</option>\n");
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
	 	
		return super.doEndTag();
	}
}
