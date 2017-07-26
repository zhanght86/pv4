<%@ include file="inc/taglib.jsp"%>
<%@ page contentType="text/html;charset=gb2312"%>

<app:validateCookie/>
<%@ page errorPage="Exception.jsp"%>
<app:checkpower funcid="4" minpower="2" />

<html:html locale="true">
<head>
	<title>ReadMe</title>
	<link href="style/pvxp.css" rel="stylesheet" type="text/css">
	<html:base />
	<style type="text/css">
	
<!--
.style1 {color: #0000FF}
.style2 {color: #000000}
.style3 {color: #FF0000}
.style5 {color: #FF0000; font-weight: bold; }
-->

    </style>
</head>
<body ondragstart="return false" onselectstart="return false"
	onselect="document.selection.empty()">

	<table width="100%" cellspacing="1" cellpadding="2"
		class="list_table_border">
		<tr>
			<td class="list_td_title">
				<b>设备列表监控使用说明</b>
			</td>
		</tr>

		<tr class="list_tr0">
			<td>
				当您选择了要监控的机构和设备以后，将进入监控主画面：
			</td>
		</tr>
		<tr class="list_tr1">
			<td>
				<div align="center">
					<img src="images/help/tmoni1.jpg">
				</div>
			</td>
		</tr>
		<tr class="list_tr0">
			<td>
				画面的中央是主监控区，默认情况下这里显示所有所有范围内的交易。在主监控区的左方和上方各有一个伸缩按钮（红色框），左边的是监控主控面板，上面的关注交易监控面板。主控面板：
			</td>
		</tr>
		<tr class="list_tr1">
			<td>
				<div align="center">
					<img src="images/help/tmoni2.jpg">
				</div>
			</td>
		</tr>
		<tr class="list_tr0">
			<td>
				在主控面板中您可以选择是否进行交易监控，并设置主监控区的自动滚动属性和显示内容。如果只需要监控关注交易，可以通过这里的相关设置节省监控端的系统资源。在关注交易监控面板上，可以对关注的交易条件进行定制：
			</td>
		</tr>
		<tr class="list_tr1">
			<td>
				<div align="center">
					<img src="images/help/tmoni3.jpg">
				</div>
			</td>
		</tr>
		<tr class="list_tr0">
			<td>
				如果想进行关注交易监控，首先要在控制区上面选择“
				<span class="style1">开启关注交易监控</span>”，然后至少选择一个
				<span class="style1">设备编号</span>和一个
				<span class="style1">交易类型</span>，如果需要对帐号和金额范围进行界定，就要输入
				<span class="style1">交易帐号</span>和
				<span class="style1">金额范围</span>的设置：
				<br>
				<span class="style1"><span class="style2">1</span> &nbsp;设备编号</span>和
				<span class="style1">交易类型</span>的选择（
				<span class="style5">※必须※</span>）：
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="style3">选择</span>：用鼠标拖动滚动条找到要选择的设备编号或交易类型，可用鼠标左键点选；
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="style3">多选</span>：如果需要选择若干项，可以按住键盘
				<span class="style1">CTRL</span>键后再用鼠标左键点选若干项即可；
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="style3">多项连续选择</span>：也可以按住键盘
				<span class="style1">Shift</span>键，并用鼠标左键点选起始和终止项来选择连续的若干项；
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="style3">取消选择</span>：要取消某一项的选择，按住键盘
				<span class="style1">CTRL</span>键，再用鼠标左键点击要取消的项即可；
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;结合全选、反选、取消按钮可以更方便的进行以上操作。
				<br>
				2&nbsp;&nbsp;交易帐号的输入（
				<span class="style1">可选</span>）：
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;如果不输入，则表示关注交易并不限制交易帐号。这里可以输入单个帐号或多个帐号。多个帐号用
				<span class="style1">逗号</span>“
				<span class="style1">,</span>”分割，每个帐号都可以是不完整的帐号片段，如果一个交易的帐号是某个输入的帐号或包含某个帐号片断，则视为符合这一条件。输入示例：
				<span class="style1">4560000000000000000,45635<br> </span>
				3&nbsp;&nbsp;金额范围的输入（
				<span class="style1">可选</span>）：
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;金额范围由两个边界组成，任何一个边界如果不输入，则表示没有上限或下限，如果都不输入则表示对金额没有条件限制。
			</td>
		</tr>
	</table>


	<br>
	<br>
	<div align="center">
		<a href="javascript:history.back()" onFocus="this.blur()"><img
				src="images/default/bt_back.gif" border="0">
		</a>
	</div>
</body>
</html:html>
