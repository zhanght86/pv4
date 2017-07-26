<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>
<app:validateCookie />
<html:html locale="true">
<head>
<title><bean:message key="pvxp.application.title.main" /></title>
<SCRIPT language="JavaScript">
	var out = 1;
	var leftw = "";
	function pagebar() {
		if (out == 1) {
			leftw = document.all['divline'].cols;
			document.all['divline'].cols = "0,*";
			main.pbar.src = "images/default/out.gif";
			main.tdbar.background = "images/default/out_bk.gif";
			out = 0;
		} else {
			document.all['divline'].cols = leftw;
			main.pbar.src = "images/default/in.gif";
			main.tdbar.background = "images/default/in_bk.gif";
			out = 1;
		}
	}

	//设备类型列表
	function getDevTpList(page) {
		main.showit();
		main.document.all['DevTpList_form'].pagesize.value = "10";
		main.document.all['DevTpList_form'].page.value = page;
		main.document.all['DevTpList_form'].submit();
	}

	//设备类型详细
	function getDevTpDetail(typeno) {
		main.showit();
		main.document.all['DevTpDetail_form'].devTypeNo.value = typeno;
		main.document.all['DevTpDetail_form'].submit();
	}

	//设备厂商列表
	function getDevftList(page) {
		main.showit();
		main.document.all['DevftList_form'].pagesize.value = "10";
		main.document.all['DevftList_form'].page.value = page;
		main.document.all['DevftList_form'].submit();
	}

	//设备厂商详细
	function getDevftDetail(devftNo) {
		main.showit();
		main.document.all['DevftDetail_form'].devftNo.value = devftNo;
		main.document.all['DevftDetail_form'].submit();
	}

	/////////////////////////////////////////////
	//设备交易次数详细
	function getDevtraDetail(devNo, devName) {

		main.showit();
		main.document.all['DevtraDetail_form'].devNo.value = devNo;
		main.document.all['DevtraDetail_form'].devName.value = devName;
		main.document.all['DevtraDetail_form'].submit();
	}

	//////////////////////////////////////////////

	//更新类型列表
	function getUpdateTypeList(page) {
		main.showit();
		main.document.all['UpdateTypeList_form'].pagesize.value = "10";
		main.document.all['UpdateTypeList_form'].page.value = page;
		main.document.all['UpdateTypeList_form'].submit();
	}

	//更新类型详细
	function getUpdateTypeDetail(typeno) {
		main.showit();
		main.document.all['UpdateTypeDetail_form'].updateno.value = typeno;
		main.document.all['UpdateTypeDetail_form'].submit();
	}

	//练习设备交易次数列表
	function getDevtra() {
		main.showit();
		main.document.all['Devtra_form'].submit();
	}

	//设备列表
	function getDevList(page) {
		main.showit();
		main.document.all['DevList_form'].page.value = page;
		main.document.all['DevList_form'].pagesize.value = "10";
		main.document.all['DevList_form'].submit();
	}

	//设备详细信息
	function getDevDetail(devNo) {
		main.showit();
		main.document.all['DevDetail_form'].devNo.value = devNo;
		main.document.all['DevDetail_form'].submit();
	}

	//交易监控
	function startTradeMoni() {
		main.showit();
		main.document.all['TradeMoni_form'].submit();
	}

	//管理员列表
	function getOperList(page) {
		main.showit();
		main.document.all['OperList_form'].pagesize.value = "10";
		main.document.all['OperList_form'].page.value = page;
		main.document.all['OperList_form'].submit();
	}

	//现金模块交易明细列表
	function getDetailList(page) {
		main.showit();
		main.document.all['CashDetail_form'].pagesize.value = "1";
		main.document.all['CashDetail_form'].page.value = page;
		main.document.all['CashDetail_form'].submit();
	}

	//管理员详细
	function getOperDetail(operid) {
		main.showit();
		main.document.all['OperDetail_form'].operid.value = operid;
		main.document.all['OperDetail_form'].submit();
	}

	//管理员修改显示
	function getOperModifyShow(operid) {
		main.showit();
		main.document.all['OperModifyShow_form'].operid.value = operid;
		main.document.all['OperModifyShow_form'].submit();
	}

	//机构列表
	function getBankinfoList(page) {
		main.showit();
		main.document.all['BankinfoList_form'].pagesize.value = "10";
		main.document.all['BankinfoList_form'].page.value = page;
		main.document.all['BankinfoList_form'].submit();
	}

	//机构详细
	function getBankinfoDetail(bankid) {
		main.showit();
		main.document.all['BankinfoDetail_form'].bankid.value = bankid;
		main.document.all['BankinfoDetail_form'].submit();
	}

	//机构修改显示
	function getBankinfoModifyShow(bankid) {
		main.showit();
		main.document.all['BankinfoModifyShow_form'].bankid.value = bankid;
		main.document.all['BankinfoModifyShow_form'].submit();
	}

	//报修记录列表
	function getMaintainList(page) {
		main.showit();
		main.document.all['MaintainList_form'].pagesize.value = "10";
		main.document.all['MaintainList_form'].page.value = page;
		main.document.all['MaintainList_form'].submit();
	}

	//报修记录详细
	function getMaintainDetail(devno, trbtype, trbdate, trbtime) {
		main.showit();
		main.document.all['MaintainDetail_form'].devno.value = devno;
		main.document.all['MaintainDetail_form'].trbtype.value = trbtype;
		main.document.all['MaintainDetail_form'].trbdate.value = trbdate;
		main.document.all['MaintainDetail_form'].trbtime.value = trbtime;

		main.document.all['MaintainDetail_form'].submit();
	}

	//显示远程管理
	function Display_RemoteControl() {
		main.showit();
		main.goDisp_RC();
	}
	//设备地图列表
	function getDevMapList(page) {
		main.showit();
		main.document.all['DevMapList_form'].pagesize.value = "6";
		main.document.all['DevMapList_form'].page.value = page;
		main.document.all['DevMapList_form'].submit();
	}
	
	//设备地图列表
	function getDevWSMoniList(opid) {
		main.showit();
		main.document.all['DevMoniWSList_form'].operid.value = opid;
		main.document.all['DevMoniWSList_form'].submit();
	}
	
	//插件管理-列表
	function systemPlugin() {
		main.showit();
		main.document.all['SysPluginList_form'].pagesize.value = "10";
		main.document.all['SysPluginList_form'].submit();
	}
	//插件列表
	function plugin() {
		main.showit();
		main.document.all['PluginList_form'].pagesize.value = "10";
		main.document.all['PluginList_form'].submit();
	}
	function goUrl(myurl) {
		try {
			main.showit();
			main.document.all['pvmain'].src = myurl;
		} catch (e) {
		}
	}
	function logOut() {
		if (confirm("您确定要退出PowerView XP吗？")) {
			//main.document.all['pvmain'].src="LogOut.jsp";
			goUrl("LogOut.jsp");
		}
	}

	function logOutByBrowser() {
		//main.document.all['pvmain'].src="LogOut.jsp";
		goUrl("LogOut.jsp");
	}
</SCRIPT>
</head>

<frameset rows='102,*' border='false' frameBorder='0' frameSpacing='0'
	onload=''>
	<frame src="top.jsp" scrolling="no" noresize>
	<frameset rows="*,25" cols="*" framespacing="1" border="1"
		bordercolor="#666666">
		<frameset id="divline" rows="*" cols="180,*" framespacing="0"
			border="0">
			<frame id="left" src="left.jsp" noresize>
			<frame id="main" src="mainfram.jsp" noresize scrolling="no"
				border="0">
		</frameset>
		<frame id="foot" src="foot.jsp" scrolling="no" noresize>
	</frameset>
</frameset>

<noframes>
	<body>
	</body>
</noframes>
</html:html>
