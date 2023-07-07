package com.own.garbage.core.dao;

import com.own.garbage.common.dto.ConditionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：condition
 * 
 * @author liugs
 * @date 2023-07-07 16:00
 **/
@Mapper
@Repository
public interface ConditionMapper {

	/**
	 * 插入单条数据
	 *
	 * @param ConditionDTO 参数对象
	 * @return int 影响数量
	 */
	int insert(ConditionDTO ConditionDTO);

	/**
	 * 根据条件删除数据
	 *
	 * @param ConditionDTO 参数对象
	 * @return int 影响数量
	 */
	int deleteBy(ConditionDTO ConditionDTO);

	/**
	 * 根据主键更新数据
	 *
	 * @param ConditionDTO 参数对象
	 * @return int 影响数量
	 * @deprecated 无主键表或者由于数据库工具没有读取到主键的情况下不可用
	 */
	@Deprecated
	int updateById(ConditionDTO ConditionDTO);

	/**
	 * 根据条件更新数据
	 *
	 * @param set set新值
	 * @param where where条件（旧值）
	 * @return int 影响数量
	 */
	int updateBy(@Param("set") ConditionDTO set, @Param("where") ConditionDTO where);

	/**
	 * 根据条件校验有多少条数据
	 *
	 * @param ConditionDTO 参数对象
	 * @return int 总数量
	 */
	int getCheckBy(ConditionDTO ConditionDTO);

	/**
	 * 根据条件查询单条数据
	 *
	 * @param ConditionDTO 参数对象
	 * @return ConditionDTO 结果对象
	 */
	ConditionDTO getModelBy(ConditionDTO ConditionDTO);

	/**
	 * 根据条件查询列表数据
	 *
	 * @param ConditionDTO 参数对象
	 * @return List<ConditionDTO> 结果对象
	 */
	List<ConditionDTO> getList(ConditionDTO ConditionDTO);

	/**
	 * 根据条件分页查询列表数据
	 *
	 * @param ConditionDTO 参数对象
	 * @return List<ConditionDTO> 结果对象
	 */
	List<ConditionDTO> getListPage(ConditionDTO ConditionDTO);

	/**
	 * 批量插入数据
	 *
	 * @param list 参数列表
	 */
	void insertBatch(List<ConditionDTO> list);

}
