package com.own.garbage.core.dao.po;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 描述：菜单表
 * 
 * @author liugs
 * @date 2023-08-09 15:40
 **/
@Data
public class SysMenuPO implements Serializable {
	private static final long serialVersionUID =  5782502631248551650L;

	/**
	 * 菜单id;主键
	 */
	private Long menuId;
	/**
	 * 子系统编码;应用编码
	 */
	private String applicationCode;
	/**
	 * 菜单编码
	 */
	private String menuCode;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 菜单子名称
	 */
	private String menuSubname;
	private String menuMeta;
	private String menuComponent;
	/**
	 * 菜单父级id;父菜单ID
	 */
	private Long parentId;
	/**
	 * 菜单树路径;树路径
	 */
	private String menuTreePath;
	/**
	 * 菜单深度
	 */
	private Integer deep;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 菜单域名
	 */
	private String domainCode;
	/**
	 * 菜单uri;请求地址
	 */
	private String menuUri;
	/**
	 * 菜单类型;M目录,C菜单,F按钮
	 */
	private String menuType;
	/**
	 * 按钮类型
	 */
	private String buttonType;
	/**
	 * 页面打开方式：0-路由跳转 1-iframe嵌套 2-原生跳转(当前窗口) 3-原生跳转(新窗口)
	 */
	private String openType;
	/**
	 * 路由跳转时，开启 replace 将不会向 history 添加新记录：1是 0否(默认值)
	 */
	private String menuReplace;
	/**
	 * 路由跳转的target，相当于 a 链接的 target 属性
	 */
	private String menuTarget;
	/**
	 * 菜单图标
	 */
	private String menuIcon;
	/**
	 * 菜单图片、背景图
	 */
	private String menuImg;
	/**
	 * 菜单状态;1显示,0隐藏
	 */
	private String menuStatus;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人
	 */
	private Long createOperId;
	/**
	 * 创建人名称
	 */
	private String createOperName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	private Date createTimeStart;
	private Date createTimeEnd;
	/**
	 * 修改人
	 */
	private Long updateOperId;
	/**
	 * 修改人名称
	 */
	private String updateOperName;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	private Date updateTimeStart;
	private Date updateTimeEnd;
	/**
	 * 扩展字段1
	 */
	private String extField1;
	/**
	 * 扩展字段2
	 */
	private String extField2;
	/**
	 * 扩展字段3
	 */
	private String extField3;
	/**
	 * 扩展字段4
	 */
	private String extField4;
	/**
	 * 扩展字段5
	 */
	private String extField5;
	/**
	 * 扩展字段6
	 */
	private String extField6;
	/**
	 * 扩展字段7
	 */
	private String extField7;
	/**
	 * 扩展字段8
	 */
	private String extField8;
	/**
	 * 扩展字段9
	 */
	private String extField9;
	/**
	 * 扩展字段10
	 */
	private String extField10;
	/**
	 * 删除标记;0生效  1已删除
	 */
	private String delFlag;
	/**
	 * 排序
	 */
	private String orderBy;

}
