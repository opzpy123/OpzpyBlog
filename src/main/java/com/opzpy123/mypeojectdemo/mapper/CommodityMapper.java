package com.opzpy123.mypeojectdemo.mapper;

import com.opzpy123.mypeojectdemo.bean.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommodityMapper {

	List<Commodity> selectUsersCommodity(Long userId);
}
