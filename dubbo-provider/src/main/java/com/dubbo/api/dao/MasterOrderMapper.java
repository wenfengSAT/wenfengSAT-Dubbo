package com.dubbo.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dubbo.api.entity.MasterOrder;
import com.dubbo.api.entity.MasterOrderExample;
/**
 * 
 * @Description： 订单
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2019年5月29日上午9:46:35]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
public interface MasterOrderMapper {
	long countByExample(MasterOrderExample example);

	int deleteByExample(MasterOrderExample example);

	int insert(MasterOrder record);

	int insertSelective(MasterOrder record);

	List<MasterOrder> selectByExample(MasterOrderExample example);

	int updateByExampleSelective(@Param("record") MasterOrder record, @Param("example") MasterOrderExample example);

	int updateByExample(@Param("record") MasterOrder record, @Param("example") MasterOrderExample example);
	
	int updatePayChennel(Map<String,String> param);
	
	int updateOrderStatus(Map<String,Object> param);
	
	List<Map<String,Object>> selectOrder(Map<String,Object> param);
}