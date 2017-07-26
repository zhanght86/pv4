package com.lcjr.pvxp.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.pojo.JGResult;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.model.*;

import net.sf.hibernate.*;

public class DrtjjGYearServer extends Thread
{
	private String HQLstr 	= null;
	private String bankid 	= null;
	private String operid 	= null;
	private String repnm 	= null;
	private String qbegin 	= null;
	private String qend 	= null;
	private String filepath = null;
	private String statmode = null;
	
	private List  devnoList = new ArrayList();

	public String getHQLstr(){
		return (this.HQLstr);
	}
	public String getBankid(){
		return (this.bankid);
	}
	public String getOperid(){
		return (this.operid);
	}
	public String getRepnm(){
		return (this.repnm);
	}
	public String getQbegin(){
		return (this.qbegin);
	}
	public String getQend(){
		return (this.qend);
	}
	public String getFilepath(){
		return (this.filepath);
	}
	public String getStatmode() {
		return (this.statmode);
	}
	public List getDevnoList() {
		return (this.devnoList);
	}

	public void setHQLstr(String HQLstr){
		this.HQLstr = HQLstr;
	}
	public void setBankid(String bankid){
		this.bankid = bankid;
	}
	public void setOperid(String operid){
		this.operid = operid;
	}
	public void setRepnm(String repnm){
		this.repnm = repnm;
	}
	public void setQbegin(String qbegin){
		this.qbegin = qbegin;
	}
	public void setQend(String qend){
		this.qend = qend;
	}
	public void setFilepath(String filepath){
		this.filepath = filepath;
	}
	public void setStatmode(String statmode) {
		this.statmode = statmode;
	}
	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}

	public DrtjjGYearServer(){

	}

	public void run(){
		//进入主线程

		try{
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			List<JGResult> result = new ArrayList<JGResult>();
			
			String statno 	= "";
			String devno 	= "";
			double timelen 	= 0;
			int 	iflag 	= 0;
			int 	devs 	= devnoList.size();
			
			double[] timelenArray 	= new double[devs];
			String[] satuArray 	= new String[devs];
			String[] devnos 	= new String[devs];

			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace( TimeStr, ":", "" );
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;

			try{
				//年统计报表

				//记录系统任务表
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();

				myStaMission.setTimeid( DateTimeStr );

				myStaMission.setStatename(  myCharSet.form2db(repnm)  );
				repnm = myCharSet.form2file(repnm);

				myStaMission.setBankid( bankid );
				myStaMission.setOwnerid( operid );
				myStaMission.setStatesort( "25" );
				myStaMission.setCreatetype( "M" );
				myStaMission.setCurrentflag( "1" );
				myStaMission.setRemark1( " " );
				myStaMission.setRemark2( " " );
				myStaMission.setRemark3( " " );

				myStaMissionBean.addStaMission( myStaMission );

			

				//处理统计后的数据
				String 	 tmpstr 	= "";
				String   saturate 	= "";
				double[] percent 	= new double[devs];
				double 	 dtdiff 	= 0;
				double 	 dpercent 	= 0.00;

				for( int i=0; i<365*4; i++ ) {
					tmpstr = myPubUtil.getOtherDate( qend+"1231", -i );
					if( tmpstr.equals( qbegin+"0101" ) ) {
						dtdiff = i+1;
						break;
					}
				}

				for( int i = 0; i < devs; i++ ) {
					if(satuArray[i]==null||satuArray[i].equals("-")||satuArray[i].equals("")){
						percent[i] = 100.00;
					}else{
						dpercent    =  ( 60*24*dtdiff - timelenArray[i] ) / ( 60*dtdiff*Double.parseDouble( satuArray[i] ) ) * 100.00;
						
						percent[i] = dpercent;
					}
					
				}
				
				for( int i = 1; i < devs; i++ ) {
					dpercent = percent[i];
					timelen  = timelenArray[i];
					saturate = satuArray[i];
					devno 	 = devnos[i];
					int j = i - 1;
					while(j >= 0 && dpercent > percent[j]){
						percent[j+1] = percent[j];
						timelenArray[j+1] = timelenArray[j];
						satuArray[j+1] = satuArray[j];
						devnos[j+1] = devnos[j];
						j--;
					}
					percent[j+1] = dpercent;
					timelenArray[j+1] = timelen;
					satuArray[j+1] = saturate;
					devnos[j+1] = devno;
				}
				List<String> liststring = getList();
				Session session = HibernateUtil.currentSession();
				Transaction trans = null;
				trans = session.beginTransaction();
				JGResult jgr = null;
				Statement statement;
				try {
					
					statement = session.connection().createStatement();
					ResultSet rs = statement.executeQuery(HQLstr);
					ResultSetMetaData md = rs.getMetaData();
					int columnCount = md.getColumnCount();
					while (rs.next()) {
						jgr = new JGResult();
						
						 jgr.setDevname(rs.getObject(1).toString());
						 jgr.setBankname(rs.getObject(2).toString());
						 jgr.setTimes(rs.getObject(3).toString());
						 jgr.setTimelen(rs.getObject(4).toString());
						 jgr.setNormaltimes(rs.getObject(5).toString());
						 result.add(jgr);
					}
				} catch (HibernateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				trans.commit();
				
				//整理输出流
				StringBuffer outstr = new StringBuffer("");
				outstr.append( "<html>\n" );
				outstr.append( "<head>\n" );
				outstr.append( "\t<title>机构开机率统计</title>\n" );
				outstr.append( "\t<style>\n" );
				outstr.append( "\t\tbody {color: #000000;font-family:'Tahoma','Helvetica','Arial','sans-serif';font-size: 9pt;}\n" );
				outstr.append( "\t\ttd {font-size: 9pt;text-decoration: none;line-height: 17pt;}\n" );
				outstr.append( "\t\t.location {font-size: 12px;font-weight: bold;text-decoration: none;}\n" );
				outstr.append( "\t\t.list_table_border {border: 1px solid #333333;}\n" );
				outstr.append( "\t\t.list_td_title {border: 1px solid #333333;color: #000000E;font-size: 12px;}\n" );
				outstr.append( "\t\t.list_tr0 {border: 1px solid #333333;background: #E7EBF7;font-size: 12px;}\n" );
				outstr.append( "\t\t.list_tr1 {border: 1px solid #333333;background: #E7EEF7;font-size: 12px;}\n" );
				outstr.append( "\t\t.list_td_prom{border: 1px solid #333333;background: #E1EDFF;font-size: 12px;}\n" );
				outstr.append( "\t</style>\n" );
				outstr.append( "\t<style media='print'>\n" );
				outstr.append( "\t\t.noprint{display:none;}\n" );
				outstr.append( "\t</style>\n" );
				outstr.append( "\t<script language='javascript' src='../js/excel.js'></script>\n" );
				outstr.append( "</head>\n" );
				outstr.append( "<body>\n" );

				//标题
				outstr.append( "<table width='100%' cellspacing='0' cellpadding='0'>\n" );
				outstr.append( "\t<tr>\n" );
				outstr.append( "\t\t<td align='left' valign='center' width='30' height='40'>\n" );
				outstr.append( "\t\t\t&nbsp;\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td>\n" );
				outstr.append( "\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>机构开机率统计</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td>\n" );
				outstr.append( "\t\t\t<font class='location'>" + repnm + "</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td align='right'>\n" );
				outstr.append( "\t\t\t<font class='location'>(年报)\n" );
				if( !qbegin.equals(qend) ) {
					outstr.append( "\t\t\t\t" + qbegin.substring(0,4) + "年" );
					outstr.append( "---" + qend.substring(0,4) + "年&nbsp;\n" );
				}else {
					outstr.append( "\t\t\t\t" + qend.substring(0,4) + "年&nbsp;\n" );
				}
				outstr.append( "\t\t\t</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t</tr>\n" );
				outstr.append( "</table>\n" );

				//处理显示报表
				if( result.size() == 0 ){
					outstr.append( "There is no transaction on device you selected, so that is no Statistics.\n" );
				}else {
					outstr.append( "<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n" );
					//表头
					outstr.append( "\t<tr align='center'>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>次序</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>机构名称</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>设备类型</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>故障次数</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>故障时间</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>故障率</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>开机率</b></td>\n" );
					outstr.append( "\t</tr>\n" );
					
					//统计
					double tmp = 0;
					DecimalFormat decimalFormat=new DecimalFormat("0.00");
					for(int j=0;j<liststring.size();j++){
						String nums = liststring.get(j).split(",")[1];
						outstr.append( "\t<tr align='center'>\n " );
						outstr.append( "\t\t<td class='list_td_title' rowspan='"+Integer.parseInt(nums)+1+"'><b>&nbsp;" + (j+1) + "</b></td>\n" );
						outstr.append( "\t\t<td class='list_td_title' rowspan='"+Integer.parseInt(nums)+1+"'><b>&nbsp;" +liststring.get(j).split(",")[0] + "</b></td>\n" );
						int sumgzseq = 0;
						float sumgztime = 0;
						float sumnormal=0;
						int suntim=0;
						for(int i=0;i<Integer.parseInt(nums);i++){
							int q;
							if(j==0){
								q=i;
							}else{
								q=Integer.parseInt(liststring.get(j-1).split(",")[1])+i;
							}
							if(i!=0){
								outstr.append( "\t<tr align='center'>\n " );
							}
							if(q<result.size()){
							outstr.append( "\t\t<td class='list_td_title'>&nbsp;" + result.get(q).getDevname() + "</td>\n" );			
							outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;" + result.get(q).getTimes()+ "</nobr></td>\n" );
							sumgzseq+=Integer.parseInt(result.get(i).getTimes());
							outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;" + Integer.parseInt(result.get(q).getTimelen())/60 + "小时"+Integer.parseInt(result.get(q).getTimelen())%60+"分钟</nobr></td>\n" );
							sumgztime+=Float.parseFloat(result.get(q).getTimelen());
							suntim +=Integer.parseInt(result.get(q).getTimelen());
							float a =(Float.parseFloat(result.get(i).getTimelen()))/(Float.parseFloat(result.get(q).getNormaltimes())*60)*100;
							if(a>100){
								a=100;
							}
							sumnormal +=Float.parseFloat(result.get(q).getNormaltimes())*60;
							
							String gzrate = decimalFormat.format(a)+"%";
							outstr.append( "\t\t<td class='list_td_title'>&nbsp;" +  gzrate+ "</td>\n" );
							outstr.append( "\t\t<td class='list_td_title'>&nbsp;" + decimalFormat.format(100-a) + "%</td>\n" );
							outstr.append( "\t</tr>\n" );
							}
						}
						outstr.append( "\t<tr class='list_tr0' align='center'>\n " );
						outstr.append( "\t\t<td class='list_td_title'>&nbsp;合计：</td>\n" );			
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+sumgzseq+"</nobr></td>\n" );
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+suntim/60+"小时"+suntim%60+"分钟</nobr></td>\n" );
						float avggz = sumgztime/sumnormal;
						if(avggz>1){
							avggz=1;
						}
						float avgkj = (1-avggz);
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+decimalFormat.format(avggz*100)+"%</nobr></td>\n" );
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+decimalFormat.format(avgkj*100)+"%</nobr></td>\n" );
						outstr.append( "\t</tr>\n" );
					}

					outstr.append( "</table>\n" );
				}

				//打印按钮
				outstr.append( "<p align='center'>\n" );
				outstr.append( "\t<OBJECT id='WebBrowser' classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' height='0' width='0' VIEWASTEXT></OBJECT>\n" );
				outstr.append( "\t<input type='button' value=' 打  印 ' onclick='document.all.WebBrowser.ExecWB(6,1)' class='noprint'>\n" );
				outstr.append( "\t<input type='button' value='直接打印' onclick='document.all.WebBrowser.ExecWB(6,6)' class='noprint'>\n" );
				outstr.append( "\t<input type='button' value='页面设置' onclick='document.all.WebBrowser.ExecWB(8,1)' class='noprint'>\n" );
				outstr.append( "\t<input type='button' value='打印预览' onclick='document.all.WebBrowser.ExecWB(7,1)' class='noprint'>\n" );
				outstr.append( "\t<input type='button' value='导出Excel' onclick='javascript:PrintTableToExcelEx1(excel)' class='noprint'>\n" );
				outstr.append( "</p>\n" );

				outstr.append( "</body>\n" );
				outstr.append( "</html>\n" );


				String OutStr = outstr.toString();
				
				//记录文件
				String filename = "M_25_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream( filepath + filename );
				OutputStreamWriter outStreamWriter = new OutputStreamWriter (outStream,"GB2312");
				outStreamWriter.write( OutStr );
				outStreamWriter.flush();
				outStreamWriter.close();
				//*/

				//更新系统任务表
				myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "25", "M", operid );
				myStaMission.setCurrentflag( "0" );
				myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );

				if( !myStaMissionBean.updateStaMission( myStaMission ) ) {
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "25", "M", operid );
					myStaMission.setCurrentflag( "2" );
					myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );
					myStaMissionBean.updateStaMission( myStaMission );
				}

			}catch ( HibernateException he ){
				System.out.println("wrong 1: "+he);
				try{
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "25", "M", operid );
					myStaMission.setCurrentflag( "2" );
					myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );
					myStaMissionBean.updateStaMission( myStaMission );
				}catch ( Exception e ) {
					System.out.println("wrong 12: "+e);
				}

			}catch ( Exception ex ){
				System.out.println("wrong 2: "+ex);
				try{
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "25", "M", operid );
					myStaMission.setCurrentflag( "2" );
					myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );
					myStaMissionBean.updateStaMission( myStaMission );
				}catch ( Exception e ) {
					System.out.println("wrong 21: "+e);
				}

			}
		}catch ( Exception e ){
			System.out.println("wrong 3: "+e);
		}
	}
	private List<String> getList(){
		List<String> returnstring = new ArrayList<String>();
		try {
			BankinfoBean bb = new BankinfoBean();
			List<Bankinfo> blist = bb.getAllBankinfoList();
			DevinfoBean db = new DevinfoBean();
			for(Bankinfo bi:blist){
				int typenum = db.getTypeNum(bi.getBankid());
				returnstring.add(bi.getBanknm().trim()+","+typenum);
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnstring;
	}

}