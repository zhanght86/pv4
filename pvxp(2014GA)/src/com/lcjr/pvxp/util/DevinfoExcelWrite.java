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
 * @author 武坤鹏
 * 
 */
public class DevinfoExcelWrite {
 
	private Logger log = Logger.getLogger(this.getClass().getName());

	public void createExcel(OutputStream os) throws WriteException, IOException {
 
		log.info("开始生成excel");
		// 创建工作薄
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		// 创建新的一页
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);

		try {
			// 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
			Label devno = new Label(0, 0, "设备编号");
			sheet.addCell(devno);

			Label typeno = new Label(1, 0, "设备类型编号");
			sheet.addCell(typeno);

			Label packtype = new Label(2, 0, "数据包类型");
			sheet.addCell(packtype);

			Label typestate = new Label(3, 0, "设备状态");
			sheet.addCell(typestate);

			Label devip = new Label(4, 0, "ip地址");
			sheet.addCell(devip);

			Label devmac = new Label(5, 0, "网卡MAC值 ");
			sheet.addCell(devmac);

			Label devkey = new Label(6, 0, "设备主密钥");
			sheet.addCell(devkey);

			Label pinkey = new Label(7, 0, "pin密钥");
			sheet.addCell(pinkey);

			Label mackey = new Label(8, 0, "mac密钥");
			sheet.addCell(mackey);

			Label waterno = new Label(9, 0, "设备流水号");
			sheet.addCell(waterno);

			Label organno = new Label(10, 0, "设备管理网点编号");
			sheet.addCell(organno);

			Label tellerno = new Label(11, 0, "设备柜员号");
			sheet.addCell(tellerno);

			Label autherno = new Label(12, 0, "授权柜员号");
			sheet.addCell(autherno);

			Label devaddr = new Label(13, 0, "设备安装地点");
			sheet.addCell(devaddr);

			Label organcontact = new Label(14, 0, "联系电话");
			sheet.addCell(organcontact);

			Label dutyname = new Label(15, 0, "具体的维护责任人");
			sheet.addCell(dutyname);

			Label polltag = new Label(16, 0, "是否需要轮询检查");
			sheet.addCell(polltag);

			Label updatetag = new Label(17, 0, "自动更新标志");
			sheet.addCell(updatetag);

			Label sysdatet = new Label(18, 0, "系统帐务日期");
			sheet.addCell(sysdatet);
 
			Label opentag = new Label(19, 0, "系统启用标志");
			sheet.addCell(opentag);

			Label sysid = new Label(20, 0, "系统编号");
			sheet.addCell(sysid);

			Label localtag = new Label(21, 0, "本地标志");
			sheet.addCell(localtag);

			Label remark1 = new Label(22, 0, "机构信息");
			sheet.addCell(remark1);

			DevinfoModel dm = new DevinfoModel();

			List<Devinfo> list = dm.getDevList();
			log.info("获得设备信息数目是：" + list.size());

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
			log.error("生成完毕");

		} catch (HibernateException e) {
			log.error("DevinfoModel", e);
		}

		// 把创建的内容写入到输出流中，并关闭输出流
		workbook.write();
		workbook.close();
		os.close();
	}

}