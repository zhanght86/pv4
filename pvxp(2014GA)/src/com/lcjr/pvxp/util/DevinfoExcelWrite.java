package com.lcjr.pvxp.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.lcjr.pvxp.model.DevinfoModel;
import com.lcjr.pvxp.orm.Devinfo;

/**
 * 
 * @author ������
 * 
 */
public class DevinfoExcelWrite {
 
	private Logger log = Logger.getLogger(this.getClass().getName());

	public void createExcel(OutputStream os) throws WriteException, IOException {
 
		log.info("��ʼ����excel");
		// ����������
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		// �����µ�һҳ
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);

		try {
			// ����Ҫ��ʾ������,����һ����Ԫ�񣬵�һ������Ϊ�����꣬�ڶ�������Ϊ�����꣬����������Ϊ����
			Label devno = new Label(0, 0, "�豸���");
			sheet.addCell(devno);

			Label typeno = new Label(1, 0, "�豸���ͱ��");
			sheet.addCell(typeno);

			Label packtype = new Label(2, 0, "���ݰ�����");
			sheet.addCell(packtype);

			Label typestate = new Label(3, 0, "�豸״̬");
			sheet.addCell(typestate);

			Label devip = new Label(4, 0, "ip��ַ");
			sheet.addCell(devip);

			Label devmac = new Label(5, 0, "����MACֵ ");
			sheet.addCell(devmac);

			Label devkey = new Label(6, 0, "�豸����Կ");
			sheet.addCell(devkey);

			Label pinkey = new Label(7, 0, "pin��Կ");
			sheet.addCell(pinkey);

			Label mackey = new Label(8, 0, "mac��Կ");
			sheet.addCell(mackey);

			Label waterno = new Label(9, 0, "�豸��ˮ��");
			sheet.addCell(waterno);

			Label organno = new Label(10, 0, "�豸����������");
			sheet.addCell(organno);

			Label tellerno = new Label(11, 0, "�豸��Ա��");
			sheet.addCell(tellerno);

			Label autherno = new Label(12, 0, "��Ȩ��Ա��");
			sheet.addCell(autherno);

			Label devaddr = new Label(13, 0, "�豸��װ�ص�");
			sheet.addCell(devaddr);

			Label organcontact = new Label(14, 0, "��ϵ�绰");
			sheet.addCell(organcontact);

			Label dutyname = new Label(15, 0, "�����ά��������");
			sheet.addCell(dutyname);

			Label polltag = new Label(16, 0, "�Ƿ���Ҫ��ѯ���");
			sheet.addCell(polltag);

			Label updatetag = new Label(17, 0, "�Զ����±�־");
			sheet.addCell(updatetag);

			Label sysdatet = new Label(18, 0, "ϵͳ��������");
			sheet.addCell(sysdatet);
 
			Label opentag = new Label(19, 0, "ϵͳ���ñ�־");
			sheet.addCell(opentag);

			Label sysid = new Label(20, 0, "ϵͳ���");
			sheet.addCell(sysid);

			Label localtag = new Label(21, 0, "���ر�־");
			sheet.addCell(localtag);

			Label remark1 = new Label(22, 0, "������Ϣ");
			sheet.addCell(remark1);

			DevinfoModel dm = new DevinfoModel();

			List<Devinfo> list = dm.getDevList();
			log.info("����豸��Ϣ��Ŀ�ǣ�" + list.size());

			for (int i = 0; i < list.size(); i++) {
				Devinfo aDevinfo = (Devinfo) list.get(i);
				Label adevno = new Label(0, i+1, aDevinfo.getDevno());
				sheet.addCell(adevno);

				Label atypeno = new Label(1, i+1, aDevinfo.getTypeno());
				sheet.addCell(atypeno);

				Label apacktype = new Label(2, i+1, aDevinfo.getPacktype());
				sheet.addCell(apacktype);

				Label atypestate = new Label(3, i+1, aDevinfo.getTypestate());
				sheet.addCell(atypestate);

				Label adevip = new Label(4, i+1, aDevinfo.getDevip());
				sheet.addCell(adevip);

				Label adevmac = new Label(5, i+1, aDevinfo.getDevmac());
				sheet.addCell(adevmac);

				Label adevkey = new Label(6, i+1, aDevinfo.getDevkey());
				sheet.addCell(adevkey);

				Label apinkey = new Label(7, i+1, aDevinfo.getPinkey());
				sheet.addCell(apinkey);

				Label amackey = new Label(8, i+1, aDevinfo.getMackey());
				sheet.addCell(amackey);

				Label awaterno = new Label(9, i+1, aDevinfo.getWaterno());
				sheet.addCell(awaterno);

				Label aorganno = new Label(10, i+1, aDevinfo.getOrganno());
				sheet.addCell(aorganno);

				Label atellerno = new Label(11, i+1, aDevinfo.getTellerno());
				sheet.addCell(atellerno);

				Label aautherno = new Label(12, i+1, aDevinfo.getAutherno());
				sheet.addCell(aautherno);

				Label adevaddr = new Label(13, i+1, aDevinfo.getDevaddr());
				sheet.addCell(adevaddr);

				Label aorgancontact = new Label(14, i+1, aDevinfo.getOrgancontact());
				sheet.addCell(aorgancontact);

				Label adutyname = new Label(15, i+1, aDevinfo.getDutyname());
				sheet.addCell(adutyname);

				Label apolltag = new Label(16, i+1, aDevinfo.getPolltag());
				sheet.addCell(apolltag);

				Label aupdatetag = new Label(17, i+1, aDevinfo.getUpdatetag());
				sheet.addCell(aupdatetag);

				Label asysdatet = new Label(18, i+1, aDevinfo.getSysdatet());
				sheet.addCell(asysdatet);

				Label aopentag = new Label(19, i+1, aDevinfo.getOpentag());
				sheet.addCell(aopentag);

				Label asysid = new Label(20, i+1, aDevinfo.getSysid());
				sheet.addCell(asysid);

				Label alocaltag = new Label(21, i+1, aDevinfo.getLocaltag());
				sheet.addCell(alocaltag);

				Label aremark1 = new Label(22, i+1, aDevinfo.getBankid());
				sheet.addCell(aremark1);
			}
			log.error("�������");

		} catch (HibernateException e) {
			log.error("DevinfoModel", e);
		}

		// �Ѵ���������д�뵽������У����ر������
		workbook.write();
		workbook.close();
		os.close();
	}

}