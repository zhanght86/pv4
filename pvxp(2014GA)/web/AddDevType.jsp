<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>


<%@ page errorPage="Exception.jsp"%>

<html:html locale="true">
<head>
	<title><bean:message key="pvxp.devtype.add" />
	</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<script language="JavaScript">
    <!--
      function validate() {
        if (document.all.devTypeNo.value == "") {
          alert("请输入设备类型编号。");
          document.all.devTypeNo.focus();
          return false;
        }
 
        if (document.all.devTypeNo.value.match(/\W/) != null) {
        	alert("设备类型编号只能包含字母和数字。");
        	document.all.devTypeNo.focus();
        	return false;
        }

        if (document.all.devName.value == "") {
        	alert("请输入设备名称。");
        	document.all.devName.focus();
        	return false;
        }

        return true;
      }
    //-->
    </script>
</head>
<body onload="javascript:parent.hidit();">
	<html:form action="/AddDevType.do" focus="devTypeNo"
		onsubmit="javascript:parent.showit();">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" align="center" width="30" height="40">
					<img src="images/default/nav.gif">
				</td>
				<td>
					<font color=blue>PowerView XP</font> ---
					<font class="location"><bean:message key="pvxp.devtype.add" />
					</font>
				</td>
			</tr>
		</table>

		<table width="100%" cellspacing="1" cellpadding="3"
			class="list_table_border">
			<!-- header -->
			<tr align="center">
				<td colspan="4" class="list_td_title">
					<b><bean:message key="pvxp.devtype.add" />
					</b>
				</td>
			</tr>
			<!-- body -->
			<tr>
				<td width="15%" class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.typeno" />
					</b>
				</td>
				<td width="35%" class="list_td_detail">
					&nbsp;
					<html:text size="40" property="devTypeNo" size="15" maxlength="8" />
				</td>
				<td width="15%" class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.typestate" />
					</b>
				</td>
				<td width="35%" class="list_td_detail">
					&nbsp;
					<html:select property="devTypeState">
						<html:option value="a">正常</html:option>
						<html:option value="s">停用</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.devtype" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="devType" maxlength="20" />
				</td>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.devname" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="devName" maxlength="60" />
				</td>
			</tr>
			<tr>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.devinfo" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:text size="30" property="devEquipt" maxlength="200" />
				</td>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.packtype" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<html:select property="dataPackageType">
						<html:option value="b">B/S结构数据包</html:option>
						<html:option value="c">C/S结构数据包</html:option>
					</html:select>
				</td>
			</tr>
			
			
			<tr>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b><bean:message key="pvxp.devtype.devfactory" />
					</b>
				</td>
				<td class="list_td_detail">
					&nbsp;
					<app:devftselect property="devftno" />
				</td>
				<td class="list_td_prom" nowrap>
					&nbsp;
					<b></b>
				</td>
				<td class="list_td_detail">
					&nbsp;
				</td>
			</tr>
			
			
		</table>

		<!-- footer -->
		<p>
		<table align="center" width="40%">
			<tr align="center">
				<td>
					<input type="image" src="images/default/bt_ok.gif"
						onClick="return validate()">
				</td>
				<td>
					<img src="images/default/bt_reset.gif" onclick="reset()"
						style="cursor:hand">
				</td>
				<td>
					<img src="images/default/bt_back.gif" onClick="history.back()"
						style="cursor:hand">
				</td>
			</tr>
		</table>
	</html:form>

</body>
</html:html>
