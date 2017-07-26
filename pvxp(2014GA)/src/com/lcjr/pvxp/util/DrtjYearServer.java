package com.lcjr.pvxp.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

import com.lcjr.pvxp.util.*;
import com.lcjr.pvxp.orm.*;
import com.lcjr.pvxp.bean.*;
import com.lcjr.pvxp.model.*;

import net.sf.hibernate.*;

public class DrtjYearServer extends Thread
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

	public DrtjYearServer(){

	}

	public void run(){
		//�������߳�

		try{
			PubUtil myPubUtil = new PubUtil();
			CharSet myCharSet = new CharSet();

			List result = new ArrayList();
			
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
				myStaMission.setStatesort( "10" );
				myStaMission.setCreatetype( "M" );
				myStaMission.setCurrentflag( "1" );
				myStaMission.setRemark1( " " );
				myStaMission.setRemark2( " " );
				myStaMission.setRemark3( " " );

				myStaMissionBean.addStaMission( myStaMission );

				//ִ�в�ѯ
				DevtjyearBean myDevYearBean = new DevtjyearBean();
				Devtjyear myDevYear = new Devtjyear();

//				result = (List)myDevYearBean.getQueryList( HQLstr );
				
//				��������豸��Ŵ���
//				�����ν��в�ѯ
//				�������ӵ��༯reault��

				int p1=HQLstr.indexOf("(");
				int p2=HQLstr.indexOf(")");
				String firstpart=HQLstr.substring(0, p1);
				String middlepart=HQLstr.substring(p1+1, p2)+"or";	
				String endpart=HQLstr.substring(p2+1, HQLstr.length());

				int pernum=devnoList.size()/150+1;
				for(int i=0;i<pernum;i++){
					middlepart="devno in (  ";

					for(int j=0;j<150 && (i*150+j)<devnoList.size();j++){
						middlepart+="'"+(String)devnoList.get(i*150+j)+"',";
					}

					middlepart=middlepart.substring(0,middlepart.length()-1)+")";
					HQLstr=firstpart+middlepart+endpart;
					result.addAll((List)myDevYearBean.getQueryList( HQLstr ));

				}
				
				int resize = 0;
				if(result != null){
					resize = result.size();
				}

				//����ÿ̨�豸ͨ��״̬����ʱ��
				for( int i = 0; i < devs; i++ ) {
					devno = (String)devnoList.get(i);
					timelen = 0;
					for( int k = 0; k < resize; k++ ) {
						myDevYear = (Devtjyear)result.get(k);
						statno = myDevYear.getStateno().trim();
						if( devno.equals( myDevYear.getDevno().trim() ) && statno.equals( "Z001" ) ) {
							timelen += Double.parseDouble( myDevYear.getTimelen().trim() );
						}
					}
					timelenArray[i] = timelen ;
					Devinfo devtemp = DevinfoModel.getDevFromList(devno);
					satuArray[i] = devtemp.getRemark2().trim();
					devnos[i] = devno;
				}
				

				//����ͳ�ƺ������
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

				//���������
				StringBuffer outstr = new StringBuffer("");
				outstr.append( "<html>\n" );
				outstr.append( "<head>\n" );
				outstr.append( "\t<title>�豸������ͳ��</title>\n" );
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
				outstr.append( "\t\t\t<font color=blue>PowerView XP </font>--- <font class='location'>�豸������ͳ��</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td>\n" );
				outstr.append( "\t\t\t<font class='location'>" + repnm + "</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t\t<td align='right'>\n" );
				outstr.append( "\t\t\t<font class='location'>(�걨)\n" );
				if( !qbegin.equals(qend) ) {
					outstr.append( "\t\t\t\t" + qbegin.substring(0,4) + "��" );
					outstr.append( "---" + qend.substring(0,4) + "��&nbsp;\n" );
				}else {
					outstr.append( "\t\t\t\t" + qend.substring(0,4) + "��&nbsp;\n" );
				}
				outstr.append( "\t\t\t</font>\n" );
				outstr.append( "\t\t</td>\n" );
				outstr.append( "\t</tr>\n" );
				outstr.append( "</table>\n" );

				//������ʾ����
				if( devs == 0 ){
					outstr.append( "There is no transaction on device you selected, so that is no Statistics.\n" );
				}else {
					outstr.append( "<table id='excel' width='100%' cellspacing='0' cellpadding='0' class='list_table_border'>\n" );
					//��ͷ
					outstr.append( "\t<tr align='center'>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>����</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>�豸���</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>��������ʱ��(ʱ/��)</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>��Ч����ʱ��</b></td>\n" );
					outstr.append( "\t\t<td class='list_td_prom'><b>������</b></td>\n" );

					
					//ͳ��
					double tmp = 0;
					for( int i = 0; i < devs; i++ ) {
						outstr.append( "\t<tr class='list_tr" + i%2 + "' align='center'>\n " );
						outstr.append( "\t\t<td class='list_td_title'><b>&nbsp;" + (i+1) + "</b></td>\n" );
						outstr.append( "\t\t<td class='list_td_title'><b>&nbsp;" + devnos[i] + "</b></td>\n" );
						outstr.append( "\t\t<td class='list_td_title'>&nbsp;" + satuArray[i] + "</td>\n" );
						tmp = 60*24*dtdiff-timelenArray[i];
						tmpstr = myPubUtil.double2String( tmp/60, 0 ) + "Сʱ" + myPubUtil.double2String( tmp%60, 0 ) + "����";
								
						outstr.append( "\t\t<td class='list_td_title' ><nobr>&nbsp;" + tmpstr + "</nobr></td>\n" );
						outstr.append( "\t\t<td class='list_td_title'>&nbsp;" + myPubUtil.double2String( percent[i], 2 ) + "%</td>\n" );
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
				String filename = "M_10_" + operid + "_" + DateTimeStr + ".htm";
				FileOutputStream outStream = new FileOutputStream( filepath + filename );
				OutputStreamWriter outStreamWriter = new OutputStreamWriter (outStream,"GB2312");
				outStreamWriter.write( OutStr );
				outStreamWriter.flush();
				outStreamWriter.close();
				//*/

				//����ϵͳ�����
				myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "10", "M", operid );
				myStaMission.setCurrentflag( "0" );
				myStaMission.setStatename( myPubUtil.dealNull(myStaMission.getStatename()).trim() );

				if( !myStaMissionBean.updateStaMission( myStaMission ) ) {
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "10", "M", operid );
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
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "10", "M", operid );
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
					myStaMission = (StaMission)myStaMissionModel.getOneStaMission( DateTimeStr, "10", "M", operid );
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


}