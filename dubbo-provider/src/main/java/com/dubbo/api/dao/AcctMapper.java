package com.dubbo.api.dao;

import com.dubbo.api.entity.Acct;
import com.dubbo.api.entity.AcctExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AcctMapper {
    long countByExample(AcctExample example);

    int deleteByExample(AcctExample example);

    int insert(Acct record);

    int insertSelective(Acct record);

    List<Acct> selectByExample(AcctExample example);

    int updateByExampleSelective(@Param("record") Acct record, @Param("example") AcctExample example);

    int updateByExample(@Param("record") Acct record, @Param("example") AcctExample example);
}