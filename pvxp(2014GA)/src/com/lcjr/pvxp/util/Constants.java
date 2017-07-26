package com.lcjr.pvxp.util;

/**
 * <p>
 * <b>Title:</b> PowerViewXP
 * </p>
 * <br>
 * <p>
 * <b>Description:</b>各模块共享常量数据定义
 * </p>
 * <br>
 * <p>
 * <b>Copyright:</b> Copyright (c) 2005
 * </p>
 * <br>
 * <p>
 * <b>Company:</b> 浪潮金融事业部(LCJR)
 * </p>
 * <br>
 * 
 * @author 杨旭
 * @version 1.0 2004/12/14
 */
public final class Constants {
	
	
	// Cookie 操作员方面属性
	
	/** Cookie中存储的操作员编号 */
	public static final String COOKIE_OPER_OPERID = "pvxp_cookie_operid";
	
	
	
	/** Cookie中存储的操作员名称 */
	public static final String COOKIE_OPER_OPERNAME = "pvxp_cookie_opername";
	
	
	
	/** Cookie中存储的操作员所属机构编号 */
	public static final String COOKIE_OPER_BANKID = "pvxp_cookie_operbkid";
	
	
	
	/** Cookie中存储的操作员权限编码 */
	public static final String COOKIE_OPER_AUTHLIST = "pvxp_cookie_operauthlist";
	
	
	
	/** Cookie中存储的操作员类型 */
	public static final String COOKIE_OPER_OPERTYPE = "pvxp_cookie_opertype";
	
	
	
	/** Cookie中存储的操作员登录次数 */
	public static final String COOKIE_OPER_OPERLOGINNUM = "pvxp_cookie_operloginnum";
	
	
	
	/** Cookie中存储的操作员最后登录时间 */
	public static final String COOKIE_OPER_OPERLASTLOGIN = "pvxp_cookie_operlastlogin";
	
	
	
	// request
	
	/** Request中存储的操作员进行当前操作所具有的权限 */
	public static final String REQUEST_OPER_POWER = "pvxp_request_operpower";
	
	
	
	/** Request中存储的当前设备类型总数 */
	public static final String REQUEST_DEVTP_TOTALDEVCOUNT = "pvxp_request_devtp_ttcount";
	
	
	
	/** Request中存储的当前设备类型总页数 */
	public static final String REQUEST_DEVTP_TOTALPAGES = "pvxp_request_devtp_pagecount";
	
	
	
	/** Request中存储的设备类型当前页数 */
	public static final String REQUEST_DEVTP_CURRENTPAGE = "pvxp_request_devtp_page";
	
	
	
	/** Request中存储的设备类型Vector */
	public static final String REQUEST_DEVTP_VECTOR = "pvxp_request_devtp_vector";
	
	
	public static final String REQUEST_DEVTP_MESSAGE = "pvxp_request_devtp_message";
	
	
	
	/** Request中存储的删除设备厂商的结果 */
	public static final String REQUEST_DEVFT_DEL_DONE = "pvxp_request_devft_del_done";
	
	
	
	/** Request中存储的删除厂商信息 */
	public static final String REQUEST_DEVFT = "pvxp_request_devft";
	
	
	
	/** Request中存储的当前厂商总数 */
	public static final String REQUEST_DEVFT_TOTALDEVFTCOUNT = "pvxp_request_devft_ttcount";
	
	
	
	/** Request中存储的当前厂商总页数 */
	public static final String REQUEST_DEVFT_TOTALPAGES = "pvxp_request_devft_pagecount";
	
	
	
	/** Request中存储的厂商当前页数 */
	public static final String REQUEST_DEVFT_CURRENTPAGE = "pvxp_request_devft_page";
	
	
	
	/** Request中存储的厂商Vector */
	public static final String REQUEST_DEVFT_VECTOR = "pvxp_request_devft_vector";
	
	
	public static final String REQUEST_DEVFT_MESSAGE = "pvxp_request_devft_message";
	
	
	public static final String REQUEST_MAINTAIN = "pvxp_request_maintain";
	
	
	
	/** Request中存储的当前维修记录总数 */
	public static final String REQUEST_MAINTAIN_TOTALMAINTAINCOUNT = "pvxp_request_maintain_ttcount";
	
	
	
	/** Request中存储的当前维修记录总页数 */
	public static final String REQUEST_MAINTAIN_TOTALPAGES = "pvxp_request_maintain_pagecount";
	
	
	
	/** Request中存储的维修记录当前页数 */
	public static final String REQUEST_MAINTAIN_CURRENTPAGE = "pvxp_request_maintain_page";
	
	
	
	/** Request中存储的维修记录Vector */
	public static final String REQUEST_MAINTAIN_VECTOR = "pvxp_request_maintain_vector";
	
	
	public static final String REQUEST_MAINTAIN_MESSAGE = "pvxp_request_maintain_message";
	
	
	
	/** Request中存储的当前更新类型总数 */
	public static final String REQUEST_UPDATETYPE_TOTALDEVCOUNT = "pvxp_request_updatetype_ttcount";
	
	
	
	/** Request中存储的当前更新类型总页数 */
	public static final String REQUEST_UPDATETYPE_TOTALPAGES = "pvxp_request_updatetype_pagecount";
	
	
	
	/** Request中存储的更新类型当前页数 */
	public static final String REQUEST_UPDATETYPE_CURRENTPAGE = "pvxp_request_updatetype_page";
	
	
	
	/** Request中存储的更新类型Vector */
	public static final String REQUEST_UPDATETYPE_VECTOR = "pvxp_request_updatetype_vector";
	
	
	public static final String REQUEST_UPDATETYPE_MESSAGE = "pvxp_request_updatetype_message";
	
	
	
	/** Request中存储的当前设备地图总数 */
	public static final String REQUEST_DEVMAP_TOTALDEVCOUNT = "pvxp_request_devmap_ttcount";
	
	
	
	/** Request中存储的当前设备地图总页数 */
	public static final String REQUEST_DEVMAP_TOTALPAGES = "pvxp_request_devmap_pagecount";
	
	
	
	/** Request中存储的设备地图当前页数 */
	public static final String REQUEST_DEVMAP_CURRENTPAGE = "pvxp_request_devmap_page";
	
	
	
	/** Request中存储的设备地图Vector */
	public static final String REQUEST_DEVMAP_VECTOR = "pvxp_request_devmapbank_vector";
	
	
	
	/** Request中存储的错误信息 */
	public static final String REQUEST_SYSTEM_ERRMSG = "pvxp_request_system_errmsg";
	
	
	
	/** Request中存储的删除设备类型的结果 */
	public static final String REQUEST_DEVTP_DEL_DONE = "pvxp_request_devtp_del_done";
	
	
	
	/** Request中存储的删除设备类型 */
	public static final String REQUEST_DEVTP = "pvxp_request_devtp";
	
	
	
	/** Request中存储的删除更新类型的结果 */
	public static final String REQUEST_UPDATETYPE_DEL_DONE = "pvxp_request_updatetype_del_done";
	
	
	
	/** Request中存储的删除更新类型 */
	public static final String REQUEST_UPDATETYPE = "pvxp_request_updatetype";
	
	
	
	/** Request中存储的当前机构总数 */
	public static final String REQUEST_BANKINFO_TTCOUNT = "pvxp_request_bankinfo_ttcount";
	
	
	
	/** Request中存储的当前机构总页数 */
	public static final String REQUEST_BANKINFO_PAGECOUNT = "pvxp_request_bankinfo_pagecount";
	
	
	
	/** Request中存储的机构当前页数 */
	public static final String REQUEST_BANKINFO_PAGE = "pvxp_request_bankinfo_page";
	
	
	
	/** Request中存储的机构Vector */
	public static final String REQUEST_BANKINFO_VECTOR = "pvxp_request_bankinfo_vector";
	
	
	
	/** Request中存储的删除机构的结果 */
	public static final String REQUEST_BANKINFO_DEL_DONE = "pvxp_request_bankinfo_del_done";
	
	
	
	/** Request中存储的删除机构 */
	public static final String REQUEST_BANKINFO = "pvxp_request_bankinfo";
	
	
	
	/** Request中存储的当前操作员总数 */
	public static final String REQUEST_OPERATOR_TTCOUNT = "pvxp_request_operator_ttcount";
	
	
	
	/** Request中存储的当前操作员总页数 */
	public static final String REQUEST_OPERATOR_PAGECOUNT = "pvxp_request_operator_pagecount";
	
	
	
	/** Request中存储的操作员当前页数 */
	public static final String REQUEST_OPERATOR_PAGE = "pvxp_request_operator_page";
	
	
	
	/** Request中存储的操作员Vector */
	public static final String REQUEST_OPERATOR_VECTOR = "pvxp_request_operator_vector";
	
	
	
	/** Request中存储的删除操作员的结果 */
	public static final String REQUEST_OPERATOR_DEL_DONE = "pvxp_request_operator_del_done";
	
	
	
	/** Request中存储的删除操作员 */
	public static final String REQUEST_OPERATOR = "pvxp_request_operator";
	
	
	
	/** Request中存储的远程控制结果 */
	public static final String REQUEST_REMOTECONTROL_RESULT = "pvxp_request_rc_result";
	
	
	
	/** Request中存储的文件分发结果 */
	public static final String REQUEST_FILEDISTRIBUTE_RESULT = "pvxp_request_fd_result";
	
	
	public static final String REQUEST_FILEDISTRIBUTE_FILES = "pvxp_request_fd_files";
	
	
	
	/** Request中存储的文件获取结果 */
	public static final String REQUEST_FILEUPLOAD_RESULT = "pvxp_request_fu_result";
	
	
	
	/** Request中存储的操作员流水结果 */
	public static final String REQUEST_OPLOG_RESULT = "pvxp_request_oplog_result";
	
	
	
	/** Request中存储的操作员流水pagebean结果 */
	public static final String REQUEST_OPLOG_PAGEBEAN = "pvxp_request_oplog_pagebean";
	
	
	
	/** Request中存储的系统表结果 */
	public static final String REQUEST_SYSPARAM = "pvxp_request_sysparam";
	
	
	
	/** Request中存储的地图所属机构编号 */
	public static final String REQUEST_DEVMAP_BANKID = "pvxp_request_devmap_bankid";
	
	
	
	/** 系统登录页面 */
	public static final String SYS_LOGIN_JSP = "/index.jsp";
	
	
	
	/** 系统错误页面 */
	public static final String SYS_ERROR_JSP = "/Sys_Error.jsp";
	
	
	
	/** 机构最大级别 */
	public static final int INT_BANKLEVEL_TOTAL = 3;
	
	
	
	/** 机构编号总长度 */
	public static final int INT_BANKID_LEN = 10;
	
	
	
	/** 机构每级长度 */
	public static final int[] INT_BANKLEVEL_LEN = { 2, 4, 4 };
	
	
	
	/** 设备列表-当前页 */
	public static final String REQUEST_DEVLIST_CURRENTPAGE = "pvxp_request_devlist_currentPage";
	
	
	
	/** 设备列表-设备Vector */
	public static final String REQUEST_DEVLIST_DEVVECTOR = "pvxp_request_devlist_devVector";
	
	
	
	/** 设备列表-设备总数 */
	public static final String REQUEST_DEVLIST_TOTALDEVCOUNT = "pvxp_request_devlist_totalDevCount";
	
	
	
	/** 设备列表-总页数 */
	public static final String REQUEST_DEVLIST_TOTALPAGES = "pvxp_request_devlist_totalPages";
	
	
	
	/** 设备列表-设备删除 */
	public static final String REQUEST_DEVDELETE_DONE = "pvxp_request_devdelet_done";
	
	
	
	/** 设备列表-设备添加 */
	public static final String REQUEST_ADDDEV_DONE = "pvxp_request_adddev_done";
	
	
	
	/** 设备列表-设备详细 */
	public static final String REQUEST_DEVDETAIL = "pvxp_request_devdetail";
	
	
	
	/** 设备列表-设备修改 */
	public static final String REQUEST_MODIFYDEV_DONE = "pvxp_request_modifydev_done";
	
	
	
	/** 插件管理-插件列表 */
	public static final String REQUEST_PLUGINMANAGE_LIST = "pvxp_request_pluginmanage_list";
	
	
	
	/** 插件管理-插件列表分页Bean */
	public static final String REQUEST_PLUGINMANAGE_PAGEBEAN = "pvxp_request_pluginmanage_pagebean";
	
	
	
	/** 插件管理-插件添加执行标志 */
	public static final String REQUEST_PLUGINADD_FLAG = "pvxp_request_pluginadd_flag";
	
	
	
	/** 插件用户Cookie标志 */
	public static final String COOKIE_USEROF_PLUGIN = "pvxp_cookie_userof_plugin";
	
	
	
	/** Oracle版本 */
	public static final String DB_OP_TYPE = (new Config("pvxp.properties")).getProperty("advanced_DBOP");
	
}
