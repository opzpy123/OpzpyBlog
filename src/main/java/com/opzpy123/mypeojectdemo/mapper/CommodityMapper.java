package com.opzpy123.mypeojectdemo.mapper;

import com.opzpy123.mypeojectdemo.bean.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommodityMapper {

	List<Commodity> selectUsersCommodity(Long userId);

	void deleteCommodity(Long commodyId);

	Commodity selectById(Long commodyId);

	void updateCommodity(Commodity commodity);

	@Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
	void addCommodity(Commodity commodity);
}
