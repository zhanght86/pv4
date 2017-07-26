package com.lcjr.pvxp.util;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.orm.util.HibernateUtil;
import com.lcjr.pvxp.pojo.CSResult;
import com.lcjr.pvxp.pojo.JGResult;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.model.*;

import net.sf.hibernate.*;

/**
 * 厂商故障率统计报表
 * @author 
 *
 */
public class DrtjCSDayServer extends Thread
{
	private String HQLstr 	= null;
	private String sql = null;
	private String bankid 	= null;
	private String operid 	= null;
	/**
	 * 报表名称
	 */
	private String repnm 	= null;
	private String qbegin 	= null;
	private String qend 	= null;
	private String filepath = null;
	private String statmode = null;
	private String typesql = null;

	private List  devnoList = new ArrayList();
	/**
	 * @return the bankid
	 */
	public String getBankid() {
		return bankid;
	}
	/**
	 * @param bankid the bankid to set
	 */
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	/**
	 * @return the devnoList
	 */
	public List getDevnoList() {
		return devnoList;
	}
	/**
	 * @param devnoList the devnoList to set
	 */
	public void setDevnoList(List devnoList) {
		this.devnoList = devnoList;
	}
	/**
	 * @return the filepath
	 */
	public String getFilepath() {
		return filepath;
	}
	/**
	 * @param filepath the filepath to set
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	/**
	 * @return the hQLstr
	 */
	public String getHQLstr() {
		return HQLstr;
	}
	/**
	 * @param lstr the hQLstr to set
	 */
	public void setHQLstr(String lstr) {
		HQLstr = lstr;
	}
	/**
	 * @return the operid
	 */
	public String getOperid() {
		return operid;
	}
	/**
	 * @param operid the operid to set
	 */
	public void setOperid(String operid) {
		this.operid = operid;
	}
	/**
	 * @return the qbegin
	 */
	public String getQbegin() {
		return qbegin;
	}
	/**
	 * @param qbegin the qbegin to set
	 */
	public void setQbegin(String qbegin) {
		this.qbegin = qbegin;
	}
	/**
	 * @return the qend
	 */
	public String getQend() {
		return qend;
	}
	/**
	 * @param qend the qend to set
	 */
	public void setQend(String qend) {
		this.qend = qend;
	}
	/**
	 * @return the repnm
	 */
	public String getRepnm() {
		return repnm;
	}
	/**
	 * @param repnm the repnm to set
	 */
	public void setRepnm(String repnm) {
		this.repnm = repnm;
	}
	/**
	 * @return the statmode
	 */
	public String getStatmode() {
		return statmode;
	}
	/**
	 * @param statmode the statmode to set
	 */
	public void setStatmode(String statmode) {
		this.statmode = statmode;
	}
	

	public DrtjCSDayServer(){

	}
	
	/**
	 * 进入主线程
	 */
	public void run(){

		try{
			//实例化类
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			List<CSResult> result = new ArrayList<CSResult>();
			
			//定义变量 初始化
			String statno 	= "";	//状态编号
			String devno 	= "";	//设备编号
			double timelen 	= 0;	//时间长度
			int 	iflag 	= 0;	//标志
			int 	devs 	= devnoList.size();	//设备数量

			//时间长度数组
			double[] timelenArray 	= new double[devs];
			String[] satuArray 	= new String[devs];
			String[] devnos 	= new String[devs];

			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace( TimeStr, ":", "" );
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;


			try{
				//日统计报表

				//记录系统任务表
				StaMissionModel myStaMissionModel = new StaMissionModel();
				StaMissionBean myStaMissionBean = new StaMissionBean();
				StaMission myStaMission = new StaMission();

				myStaMission.setTimeid( DateTimeStr );

				myStaMission.setStatename(  myCharSet.form2db(repnm)  );
				repnm = myCharSet.form2file(repnm);

				myStaMission.setBankid( bankid );
				myStaMission.setOwnerid( operid );
				myStaMission.setStatesort( "26" );
				myStaMission.setCreatetype( "M" );
				myStaMission.setCurrentflag( "1" );
				myStaMission.setRemark1( " " );
				myStaMission.setRemark2( " " );
				myStaMission.setRemark3( " " );

				myStaMissionBean.addStaMission( myStaMission );
				Map<String, String> mynumMap = getMap();
				List< String> typelist = gettypeMap();
				Session session = HibernateUtil.currentSession();
				Transaction trans = null;
				trans = session.beginTransaction();
				CSResult csr = null;
				Statement statement;
				try {
					
					statement = session.connection().createStatement();
					ResultSet rs = statement.executeQuery(HQLstr);
					ResultSetMetaData md = rs.getMetaData();
					int columnCount = md.getColumnCount();
					while (rs.next()) {
						csr = new CSResult();
						
						csr.setDevname(rs.getObject(1).toString().trim());
						csr.setTimes(rs.getObject(2).toString().trim());
						 csr.setTimelen(rs.getObject(3).toString().trim());
						 csr.setNormaltimes(rs.getObject(4).toString().trim());
						 csr.setFactoryname(rs.getObject(5).toString().trim());
						 csr.setDevnum(mynumMap.get(csr.getFactoryname()+csr.getDevname()));
						 result.add(csr);
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
				outstr.append( "\t<title>厂商故障率统计</title>\n" );
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
				outstr.append( "\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>厂商故障率统计</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td>\n" );
				outstr.append( "\t\t\t<font class='location'>" + repnm + "</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td align='right'>\n" );
				outstr.append( "\t\t\t<font class='location'>(日报)\n" );
				if( !qbegin.equals(qend) ) {
					outstr.append( "\t\t\t\t" + qbegin.substring(0,4) + "年" + Integer.parseInt(qbegin.substring(4,6)) + "月" + Integer.parseInt(qbegin.substring(6,8)) + "日" );
					outstr.append( "---" + qend.substring(0,4) + "年" + Integer.parseInt(qend.substring(4,6)) + "月" + Integer.parseInt(qend.substring(6,8)) + "日&nbsp;\n" );
				}else {
					outstr.append( "\t\t\t\t" + qend.substring(0,4) + "年" + Integer.parseInt(qend.substring(4,6)) + "月" + Integer.parseInt(qend.substring(6,8)) + "日&nbsp;\n" );
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
					outstr.append( "\t\t<td class='list_td_prom'><b>厂商名称</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>设备类型</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>设备数量</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>故障次数</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>故障时间</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>故障率</b></td>\n" );
					//统计
					double tmp = 0;
					DecimalFormat decimalFormat=new DecimalFormat("0.00");
					for(int j=0;j<typelist.size();j++){
						String nums = typelist.get(j).split(",")[1];
						outstr.append( "\t<tr class='list_tr" + j%2 + "' align='center'>\n " );
						outstr.append( "\t\t<td class='list_td_title' rowspan='"+(Integer.parseInt(nums)+1)+"'><b>&nbsp;" + (j+1) + "</b></td>\n" );
						outstr.append( "\t\t<td class='list_td_title' rowspan='"+(Integer.parseInt(nums)+1)+"'><b>&nbsp;" +typelist.get(j).split(",")[0] + "</b></td>\n" );
						int sumgzseq = 0;
						float sumgztime = 0;
						float sumnormal=0;
						int suntim=0;
						int sumtimes=0;
						for(int i=0;i<Integer.parseInt(nums);i++){
							int q;
							if(j==0){
								q=i;
							}else{
								q=Integer.parseInt(typelist.get(j-1).split(",")[1])+i;
							}
							if(i!=0){
								outstr.append( "\t<tr align='center'>\n " );
							}
							if(q<result.size()){
							outstr.append( "\t\t<td class='list_td_title'>&nbsp;" + result.get(q).getDevname() + "</td>\n" );			
							outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;" + mynumMap.get(result.get(q).getFactoryname().trim()+result.get(q).getDevname().trim())+ "</nobr></td>\n" );
							sumgzseq+=Integer.parseInt(mynumMap.get(result.get(q).getFactoryname().trim()+result.get(q).getDevname().trim()));
							outstr.append( "\t\t<td class='list_td_title'>&nbsp;" + result.get(q).getTimes()+ "</td>\n" );		
							sumtimes += Integer.parseInt(result.get(q).getTimes());
							outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;" + Integer.parseInt(result.get(q).getTimelen())/60+ "小时"+Integer.parseInt(result.get(q).getTimelen())%60+"分钟</nobr></td>\n" );
							sumgztime+=Float.parseFloat(result.get(q).getTimelen());
							suntim +=Integer.parseInt(result.get(q).getTimelen());
							float a = (Float.parseFloat(result.get(i).getTimelen()))/(Float.parseFloat(result.get(q).getNormaltimes())*60);
							//保证数据不出现大于100%的情况
							if(a>1){
								a=1;
							}
							sumnormal +=Float.parseFloat(result.get(q).getNormaltimes())*60;
							String gzrate = decimalFormat.format(a*100)+"%";
							outstr.append( "\t\t<td class='list_td_title'>&nbsp;" +  gzrate+ "</td>\n" );
							outstr.append( "\t</tr>\n" );
							}
						}
						outstr.append( "\t<tr class='list_tr0' align='center'>\n " );
						outstr.append( "\t\t<td class='list_td_title'>&nbsp;合计：</td>\n" );			
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+sumgzseq+"</nobr></td>\n" );
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+sumtimes+"</nobr></td>\n" );

						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+suntim/60+"小时"+suntim%60+"分钟</nobr></td>\n" );
						float avggz = sumgztime/sumnormal*100;
						if(avggz>100){
							avggz=100;
						}
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+decimalFormat.format(avggz)+"%</nobr></td>\n" );
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
				String filename = "M_26_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream( filepath + filename );
				OutputStreamWriter outStreamWriter = new OutputStreamWriter (outStream,"GB2312");
				outStreamWriter.write( OutStr );
				outStreamWriter.flush();
				outStreamWriter.close();
				//*/

				//更新系统任务表
				myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "26", "M", operid );
				myStaMission.setCurrentflag( "0" );
				myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );

				if( !myStaMissionBean.updateStaMission( myStaMission ) ) {
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "26", "M", operid );
					myStaMission.setCurrentflag( "2" );
					myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );
					myStaMissionBean.updateStaMission( myStaMission );
				}

			}catch ( HibernateException he ){
				System.out.println("出现错误0：" +he);

				try{
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "26", "M", operid );
					myStaMission.setCurrentflag( "2" );
					myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );
					myStaMissionBean.updateStaMission( myStaMission );
				}catch ( Exception e ) {
					System.out.println("出现错误1：" +e);
				}

			}catch ( Exception ex ){
				System.out.println("出现错误2：" +ex);
				try{
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "26", "M", operid );
					myStaMission.setCurrentflag( "2" );
					myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );
					myStaMissionBean.updateStaMission( myStaMission );
				}catch ( Exception e ) {
					System.out.println("出现错误3：" +e);
				}

			}
		}catch ( Exception e ){
			System.out.println("出现错误4：" +e);
		}
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	private Map<String, String> getMap(){
		Map<String , String> numMap = new HashMap<String, String>();
		Session session;
		try {
			session = HibernateUtil.currentSession();
			Transaction trans = null;
			trans = session.beginTransaction();
			Statement statement;
		
				statement = session.connection().createStatement();
				ResultSet rs = statement.executeQuery(sql);
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				while (rs.next()) {
					
					 String factoryname = rs.getObject(1).toString();
					 String devname = rs.getObject(2).toString();
					String devnum = rs.getObject(3).toString();
					numMap.put(factoryname.trim()+devname.trim(), devnum.trim());
				}
		
			trans.commit();
		} catch (HibernateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numMap;
	}
	
	private List<String> gettypeMap(){
		List<String> typelist = new ArrayList<String>();
		Session session;
		try {
			session = HibernateUtil.currentSession();
			Transaction trans = null;
			trans = session.beginTransaction();
			Statement statement;
		
				statement = session.connection().createStatement();
				ResultSet rs = statement.executeQuery(typesql);
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				while (rs.next()) {
					
					 String factoryname = rs.getObject(1).toString();
					String typenum = rs.getObject(2).toString();
					typelist.add(factoryname.trim()+","+typenum.trim());
				}
		
			trans.commit();
		} catch (HibernateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return typelist;
	}
	public String getTypesql() {
		return typesql;
	}
	public void setTypesql(String typesql) {
		this.typesql = typesql;
	}



}