<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie />
<html:html locale="true">
<head>
<title></title>
<script language="JavaScript" type="text/JavaScript">
	function showit() {
		document.all['waiting'].style.visibility = "visible";
	}
	function hidit() {
		document.all['waiting'].style.visibility = "hidden";
	}
</script>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0">
	<div id="pv_forms"
		style="position:absolute; z-index:-10; left: -1000; top: -1000;visibility: hidden;">

		<!-- 设备类型列表 -->
		<html:form action="/DevTpList.do" styleId="DevTpList_form"
			target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>

		<!-- 设备类型详细 -->
		<html:form action="/DevTpDetail.do" styleId="DevTpDetail_form"
			target="pvmain">
			<html:hidden property="devTypeNo" styleId="devTypeNo" />
		</html:form>

		<!-- 设备厂商列表 -->
		<html:form action="/DevftList.do" styleId="DevftList_form"
			target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>

		<!-- 设备厂商详细 -->
		<html:form action="/DevftDetail.do" styleId="DevftDetail_form"
			target="pvmain">
			<html:hidden property="devftNo" styleId="devftNo" />
		</html:form>

		<!-- 更新类型列表 -->
		<html:form action="/UpdateTypeList.do" styleId="UpdateTypeList_form"
			target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>

		<!-- 更新类型详细 -->
		<html:form action="/UpdateTypeDetail.do"
			styleId="UpdateTypeDetail_form" target="pvmain">
			<html:hidden property="updateno" styleId="updateno" />
		</html:form>

		<!-- 设备列表 -->
		<html:form action="/DevList.do" styleId="DevList_form" target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>

		<!-- 设备详细信息 -->
		<html:form action="/DevDetail.do" styleId="DevDetail_form"
			target="pvmain">
			<html:hidden property="devNo" styleId="devNo" />
		</html:form>

		<!-- 报修记录列表 -->
		<html:form action="/MaintainList.do" styleId="MaintainList_form"
			target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>

		<!-- 报修记录详细 -->
		<html:form action="/MaintainDetail.do" styleId="MaintainDetail_form"
			target="pvmain">
			<html:hidden property="devno" styleId="devno" />
			<html:hidden property="trbtype" styleId="trbtype" />
			<html:hidden property="trbdate" styleId="trbdate" />
			<html:hidden property="trbtime" styleId="trbtime" />
		</html:form>

		<!-- 交易监控 -->
		<html:form action="/TradeMoni.do" styleId="TradeMoni_form"
			target="pvmain">
		</html:form>

		<!-- 操作员列表 -->
		<html:form action="/OperList.do" styleId="OperList_form"
			target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>

		<!-- 现金交易明细列表 -->
		<html:form action="/cashDetail.do?method=list"
			styleId="CashDetail_form" target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>
		<!-- 操作员详细 -->
		<html:form action="/OperDetail.do" styleId="OperDetail_form"
			target="pvmain">
			<html:hidden property="operid" styleId="operid" />
		</html:form>

		<!-- 操作员修改详细 -->
		<html:form action="/OperModifyShow.do" styleId="OperModifyShow_form"
			target="pvmain">
			<html:hidden property="operid" styleId="operid" />
		</html:form>

		<!-- 操作员修改 -->
		<html:form action="/OperModify.do" styleId="OperModify_form"
			target="pvmain">
			<html:hidden property="operid" styleId="operid" />
			<html:hidden property="opername" styleId="opername" />
			<html:hidden property="operstate" styleId="operstate" />
			<html:hidden property="opertype" styleId="opertype" />
			<html:hidden property="authlist" styleId="authlist" />
			<html:hidden property="operpasswd" styleId="operpasswd" />
		</html:form>

		<!-- 添加操作员 -->
		<html:form action="/OperAdd.do" styleId="OperAdd_form" target="pvmain">
			<html:hidden property="operid" styleId="operid" />
			<html:hidden property="opername" styleId="opername" />
			<html:hidden property="operstate" styleId="operstate" />
			<html:hidden property="operpasswd" styleId="operpasswd" />
			<html:hidden property="bankid" styleId="bankid" />
			<html:hidden property="opertype" styleId="opertype" />
			<html:hidden property="authlist" styleId="authlist" />
		</html:form>

		<!-- 机构列表 -->
		<html:form action="/BankinfoList.do" styleId="BankinfoList_form"
			target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>

		<!-- 机构详细 -->
		<html:form action="/BankinfoDetail.do" styleId="BankinfoDetail_form"
			target="pvmain">
			<html:hidden property="bankid" styleId="bankid" />
		</html:form>

		<!-- 机构修改详细 -->
		<html:form action="/BankinfoModifyShow.do"
			styleId="BankinfoModifyShow_form" target="pvmain">
			<html:hidden property="bankid" styleId="bankid" />
		</html:form>

		<!-- 机构修改 -->
		<html:form action="/BankinfoModify.do" styleId="BankinfoModify_form"
			target="pvmain">
			<html:hidden property="bankid" styleId="bankid" />
			<html:hidden property="bankname" styleId="bankname" />
			<html:hidden property="bankaddr" styleId="bankaddr" />
			<html:hidden property="banktel" styleId="banktel" />
			<html:hidden property="bankstate" styleId="bankstate" />
		</html:form>

		<!-- 添加机构 -->
		<html:form action="/BankinfoAdd.do" styleId="BankinfoAdd_form"
			target="pvmain">
			<html:hidden property="banklevel" styleId="banklevel" />
			<html:hidden property="parent_bankid" styleId="parent_bankid" />
			<html:hidden property="bankname" styleId="bankname" />
			<html:hidden property="bankaddr" styleId="bankaddr" />
			<html:hidden property="banktel" styleId="banktel" />
			<html:hidden property="bankstate" styleId="bankstate" />
		</html:form>

		<!-- 设备地图列表 -->
		<html:form action="/DevMapList.do" styleId="DevMapList_form"
			target="pvmain">
			<html:hidden property="page" styleId="page" />
			<html:hidden property="pagesize" styleId="pagesize" />
		</html:form>

		<!-- 设备WebSocket 监控列表 -->
		<html:form action="/devWSMoniList.do?method=list" styleId="DevMoniWSList_form"
			target="pvmain">
			<html:hidden property="operid" styleId="operid" />
		</html:form>

		<!-- 插件管理-列表 -->
		<form action="SystemPlugin.do" id="SysPluginList_form" target="pvmain"
			method="get">
			<input type="hidden" name="pagesize" id="pagesize">
		</form>

		<!-- 插件管理-删除 -->
		<form action="SystemPluginDel.do" id="SysPluginDel_form"
			target="pvmain" method="get">
			<input type="hidden" name="plugid" id="plugid">
		</form>

		<!-- 插件列表 -->
		<form action="PluginList.do" id="PluginList_form" target="pvmain"
			method="get">
			<input type="hidden" name="pagesize" id="pagesize">
		</form>

		<script>
			function goCreateMap() {
				document.all.gocreatemap.submit();
			}
		</script>
		<!-- 创建设备地图 -->
		<html:form action="/DevMapCreate.do" styleId="DevMapCreate_form"
			target="pvmain">
			<html:hidden property="bankid" styleId="bankid" />
			<html:hidden property="imgname" styleId="imgname" />
		</html:form>


	</div>

	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0" id="maintable">
		<tr>
			<td id="tdbar" width="1" style="CURSOR: hand"
				background="images/default/in_bk.gif"
				onClick="javascript:parent.pagebar()"><img
				src="images/default/in.gif" name="pbar" width="8" height="194"
				border="0" id="pbar"></td>


			<td width="100%" valign="top" align="center"><iframe id="pvmain"
					name="pvmain" width="100%" height="100%" src="main.jsp"
					frameborder="0" align="center"> </iframe>
				<div id="waiting"
					style="position:absolute; z-index:1; left: 0px; top: 0px; width: 100%; height: 100%;visibility: hidden;">
					<div id="Layer2"
						style="position:absolute; z-index:10; left: 8px; top: 0px; width: 100%; height: 100%;">
						<table width="100%" height="100%" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td align="center" valign="middle"><object
										classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
										codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
										width="456" height="149">
										<param name="movie" value="swf/waiting.swf">
										<param name="quality" value="high">
										<param name="menu" value="false">
										<embed src="swf/waiting.swf" quality="high"
											pluginspage="http://www.macromedia.com/go/getflashplayer"
											type="application/x-shockwave-flash" width="456" height="149"></embed>
									</object></td>
							</tr>
						</table>
					</div>


					<div id="Layer1"
						style="position:absolute; width:100%; height:100%; z-index:9; left: 8; top: 0;"
						align="left">
						<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
							codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
							width="100%" height="100%">
							<param name="movie" value="swf/waitingback.swf">
							<param name="quality" value="high">
							<PARAM NAME=bgcolor VALUE=#FFFFFF>
							<PARAM NAME=wmode VALUE=Window>
							<param name="menu" value="false">
							<embed src="swf/waitingback.swf" width="100%" height="100%"
								quality="high"
								pluginspage="http://www.macromedia.com/go/getflashplayer"
								type="application/x-shockwave-flash" menu="no"></embed>
						</object>
					</div>
				</div></td>
	</table>
</body>
</html:html>
