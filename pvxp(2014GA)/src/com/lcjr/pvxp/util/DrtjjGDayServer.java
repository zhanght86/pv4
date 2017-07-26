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

/**
 * ����������ͳ�Ʊ���
 * @author 
 *
 */
public class DrtjjGDayServer extends Thread
{
	private String HQLstr 	= null;
	private String bankid 	= null;
	private String operid 	= null;
	/**
	 * ��������
	 */
	private String repnm 	= null;
	private String qbegin 	= null;
	private String qend 	= null;
	private String filepath = null;
	private String statmode = null;

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
	

	public DrtjjGDayServer(){

	}
	
	/**
	 * �������߳�
	 */
	public void run(){

		try{
			//ʵ������
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			List<JGResult> result = new ArrayList<JGResult>();
			
			//������� ��ʼ��
			String statno 	= "";	//״̬���
			String devno 	= "";	//�豸���
			double timelen 	= 0;	//ʱ�䳤��
			int 	iflag 	= 0;	//��־
			int 	devs 	= devnoList.size();	//�豸����

			//ʱ�䳤������
			double[] timelenArray 	= new double[devs];
			String[] satuArray 	= new String[devs];
			String[] devnos 	= new String[devs];

			String TimeStr = myPubUtil.getNowTime();
			TimeStr = myPubUtil.replace( TimeStr, ":", "" );
			String DateTimeStr = myPubUtil.getNowDate(1) + TimeStr;


			try{
				//��ͳ�Ʊ���

				//��¼ϵͳ�����
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
				

				//���������
				StringBuffer outstr = new StringBuffer("");
				outstr.append( "<html>\n" );
				outstr.append( "<head>\n" );
				outstr.append( "\t<title>����������ͳ��</title>\n" );
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

				//����
				outstr.append( "<table width='100%' cellspacing='0' cellpadding='0'>\n" );
				outstr.append( "\t<tr>\n" );
				outstr.append( "\t\t<td align='left' valign='center' width='30' height='40'>\n" );
				outstr.append( "\t\t\t&nbsp;\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td>\n" );
				outstr.append( "\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>����������ͳ��</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td>\n" );
				outstr.append( "\t\t\t<font class='location'>" + repnm + "</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td align='right'>\n" );
				outstr.append( "\t\t\t<font class='location'>(�ձ�)\n" );
				if( !qbegin.equals(qend) ) {
					outstr.append( "\t\t\t\t" + qbegin.substring(0,4) + "��" + Integer.parseInt(qbegin.substring(4,6)) + "��" + Integer.parseInt(qbegin.substring(6,8)) + "��" );
					outstr.append( "---" + qend.substring(0,4) + "��" + Integer.parseInt(qend.substring(4,6)) + "��" + Integer.parseInt(qend.substring(6,8)) + "��&nbsp;\n" );
				}else {
					outstr.append( "\t\t\t\t" + qend.substring(0,4) + "��" + Integer.parseInt(qend.substring(4,6)) + "��" + Integer.parseInt(qend.substring(6,8)) + "��&nbsp;\n" );
				}
				outstr.append( "\t\t\t</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t</tr>\n" );
				outstr.append( "</table>\n" );

				//������ʾ����
				if( result.size() == 0 ){
					outstr.append( "There is no transaction on device you selected, so that is no Statistics.\n" );
				}else {
					outstr.append( "<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n" );
					//��ͷ
					outstr.append( "\t<tr align='center'>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>����</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>��������</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>�豸����</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>���ϴ���</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>����ʱ��</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>������</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>������</b></td>\n" );
					outstr.append( "\t</tr>\n" );
					//ͳ��
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
							outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;" + Integer.parseInt(result.get(q).getTimelen())/60 + "Сʱ"+Integer.parseInt(result.get(q).getTimelen())%60+"����</nobr></td>\n" );
							sumgztime+=Float.parseFloat(result.get(q).getTimelen());
							suntim +=Integer.parseInt(result.get(q).getTimelen());
							float a =(Float.parseFloat(result.get(i).getTimelen()))/(Float.parseFloat(result.get(q).getNormaltimes())*60)*100;
							//��֤���ܴ���100%
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
						outstr.append( "\t\t<td class='list_td_title'>&nbsp;�ϼƣ�</td>\n" );			
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+sumgzseq+"</nobr></td>\n" );
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;"+suntim/60+"Сʱ"+suntim%60+"����</nobr></td>\n" );
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

				//��ӡ��ť
				outstr.append( "<p align='center'>\n" );
				outstr.append( "\t<OBJECT id='WebBrowser' classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' height='0' width='0' VIEWASTEXT></OBJECT>\n" );
				outstr.append( "\t<input type='button' value=' ��  ӡ ' onclick='document.all.WebBrowser.ExecWB(6,1)' class='noprint'>\n" );
				outstr.append( "\t<input type='button' value='ֱ�Ӵ�ӡ' onclick='document.all.WebBrowser.ExecWB(6,6)' class='noprint'>\n" );
				outstr.append( "\t<input type='button' value='ҳ������' onclick='document.all.WebBrowser.ExecWB(8,1)' class='noprint'>\n" );
				outstr.append( "\t<input type='button' value='��ӡԤ��' onclick='document.all.WebBrowser.ExecWB(7,1)' class='noprint'>\n" );
				outstr.append( "\t<input type='button' value='����Excel' onclick='javascript:PrintTableToExcelEx1(excel)' class='noprint'>\n" );
				outstr.append( "</p>\n" );

				outstr.append( "</body>\n" );
				outstr.append( "</html>\n" );


				String OutStr = outstr.toString();
				
				//��¼�ļ�
				String filename = "M_25_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream( filepath + filename );
				OutputStreamWriter outStreamWriter = new OutputStreamWriter (outStream,"GB2312");
				outStreamWriter.write( OutStr );
				outStreamWriter.flush();
				outStreamWriter.close();
				//*/

				//����ϵͳ�����
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
				System.out.println("���ִ���0��" +he);

				try{
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "25", "M", operid );
					myStaMission.setCurrentflag( "2" );
					myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );
					myStaMissionBean.updateStaMission( myStaMission );
				}catch ( Exception e ) {
					System.out.println("���ִ���1��" +e);
				}

			}catch ( Exception ex ){
				System.out.println("���ִ���2��" +ex);
				try{
					StaMissionModel myStaMissionModel = new StaMissionModel();
					StaMissionBean myStaMissionBean = new StaMissionBean();
					StaMission myStaMission = new StaMission();
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "25", "M", operid );
					myStaMission.setCurrentflag( "2" );
					myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );
					myStaMissionBean.updateStaMission( myStaMission );
				}catch ( Exception e ) {
					System.out.println("���ִ���3��" +e);
				}

			}
		}catch ( Exception e ){
			System.out.println("���ִ���4��" +e);
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