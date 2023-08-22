package com.own.garbage.core.dao;

import com.own.garbage.core.dao.po.ControllerMenuMapPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：controller_menu_map
 * 
 * @author liugs
 * @date 2023-08-09 15:31
 **/
@Mapper
@Repository
public interface ControllerMenuMapMapper {

	/**
	 * 插入单条数据
	 *
	 * @param controllerMenuMapPo 参数对象
	 * @return int 影响数量
	 */
	int insert(ControllerMenuMapPO controllerMenuMapPo);

	/**
	 * 根据条件删除数据
	 *
	 * @param controllerMenuMapPo 参数对象
	 * @return int 影响数量
	 */
	int deleteBy(ControllerMenuMapPO controllerMenuMapPo);

	/**
	 * 根据主键更新数据
	 *
	 * @param controllerMenuMapPo 参数对象
	 * @return int 影响数量
	 * @deprecated 无主键表或者由于数据库工具没有读取到主键的情况下不可用
	 */
	@Deprecated
	int updateById(ControllerMenuMapPO controllerMenuMapPo);

	/**
	 * 根据条件更新数据
	 *
	 * @param set set新值
	 * @param where where条件（旧值）
	 * @return int 影响数量
	 */
	int updateBy(@Param("set") ControllerMenuMapPO set, @Param("where") ControllerMenuMapPO where);

	/**
	 * 根据条件校验有多少条数据
	 *
	 * @param controllerMenuMapPo 参数对象
	 * @return int 总数量
	 */
	int getCheckBy(ControllerMenuMapPO controllerMenuMapPo);

	/**
	 * 根据条件查询单条数据
	 *
	 * @param controllerMenuMapPo 参数对象
	 * @return ControllerMenuMapPO 结果对象
	 */
	ControllerMenuMapPO getModelBy(ControllerMenuMapPO controllerMenuMapPo);

	/**
	 * 根据条件查询列表数据
	 *
	 * @param controllerMenuMapPo 参数对象
	 * @return List<ControllerMenuMapPO> 结果对象
	 */
	List<ControllerMenuMapPO> getList(ControllerMenuMapPO controllerMenuMapPo);

	/**
	 * 批量插入数据
	 *
	 * @param list 参数列表
	 */
	void insertBatch(List<ControllerMenuMapPO> list);

}
