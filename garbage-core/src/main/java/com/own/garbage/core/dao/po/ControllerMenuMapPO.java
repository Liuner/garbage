package com.own.garbage.core.dao.po;

import lombok.Data;
import java.io.Serializable;

/**
 * 描述：controller_menu_map
 * 
 * @author liugs
 * @date 2023-08-09 15:32
 **/
@Data
public class ControllerMenuMapPO implements Serializable {
	private static final long serialVersionUID =  -4459168980043143691L;

	private Long id;
	/**
	 * 请求地址
	 */
	private String controller;
	/**
	 * 低代码页面名称
	 */
	private String pageName;
	/**
	 * 路由
	 */
	private String route;
	/**
	 * 菜单名称
	 */
	private String menuName;
	private String functionName;
	/**
	 * 排序
	 */
	private String orderBy;

}
