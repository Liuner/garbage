package com.own.garbage.core.dao;

import com.own.garbage.core.dao.po.SysMenuPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：菜单表
 * 
 * @author liugs
 * @date 2023-08-09 15:40
 **/
@Mapper
@Repository
public interface SysMenuMapper {

	/**
	 * 插入单条数据
	 *
	 * @param sysMenuPo 参数对象
	 * @return int 影响数量
	 */
	int insert(SysMenuPO sysMenuPo);

	/**
	 * 根据条件删除数据
	 *
	 * @param sysMenuPo 参数对象
	 * @return int 影响数量
	 */
	int deleteBy(SysMenuPO sysMenuPo);

	/**
	 * 根据主键更新数据
	 *
	 * @param sysMenuPo 参数对象
	 * @return int 影响数量
	 * @deprecated 无主键表或者由于数据库工具没有读取到主键的情况下不可用
	 */
	@Deprecated
	int updateById(SysMenuPO sysMenuPo);

	/**
	 * 根据条件更新数据
	 *
	 * @param set set新值
	 * @param where where条件（旧值）
	 * @return int 影响数量
	 */
	int updateBy(@Param("set") SysMenuPO set, @Param("where") SysMenuPO where);

	/**
	 * 根据条件校验有多少条数据
	 *
	 * @param sysMenuPo 参数对象
	 * @return int 总数量
	 */
	int getCheckBy(SysMenuPO sysMenuPo);

	/**
	 * 根据条件查询单条数据
	 *
	 * @param sysMenuPo 参数对象
	 * @return SysMenuPO 结果对象
	 */
	SysMenuPO getModelBy(SysMenuPO sysMenuPo);

	/**
	 * 根据条件查询列表数据
	 *
	 * @param sysMenuPo 参数对象
	 * @return List<SysMenuPO> 结果对象
	 */
	List<SysMenuPO> getList(SysMenuPO sysMenuPo);

	/**
	 * 批量插入数据
	 *
	 * @param list 参数列表
	 */
	void insertBatch(List<SysMenuPO> list);

}
