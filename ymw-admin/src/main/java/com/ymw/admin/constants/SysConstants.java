package com.ymw.admin.constants;

/**
 * 常量管理
 * @author Louis
 * @date Oct 29, 2018
 */ 
public interface SysConstants {

	/**
	 * 系统管理员用户名
	 */
	String ADMIN = "admin";

	/**
	 * 是
	 */
	String IS_TRUE = "1";

	/**
	 * 否
	 */
	String IS_FALSE = "0";

	/**
	 * 图片
	 */
	String IMG = "1";

	/**
	 * 文件
	 */
	String DOC = "2";

	/**
	 * 视频
	 */
	String VIDEO = "3";

	/**
	 * 音频
	 */
	String AUDIO = "4";

	/**
	 * 其他
	 */
	String OTHER ="5";

	/**
	 * 成功
	 */
	String SUCCESS = "1";

	/**
	 * 失败
	 */
	String  FAULT="0";

	/**
	 * redis库0  保存基本信息
	 */
	Integer DATABASE0=0;

	/**
	 * redis库1  暂时未启用
	 */
	Integer DATABASE1=1;

	/**
	 * 所有人员
	 */
	String ALL_USER = "all_user";

	/**
	 * 所有部门
	 */
	String ALL_DEPT = "all_dept";

	/**
	 * 所有字典
	 */
	String ALL_DICT = "all_dict";

	/**
	 * 所有菜单
	 */
	String ALL_MENU = "all_menu";

	/**
	 * 所有角色
	 */
	String ALL_ROLE = "all_role";

	/**
	 * 已读
	 */
	String READ = "1";

	/**
	 * 未读
	 */
	String UNREAD = "0";
}
