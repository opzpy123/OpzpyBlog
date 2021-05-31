package com.opzpy123.mypeojectdemo.service;

import com.opzpy123.mypeojectdemo.bean.Commodity;
import com.opzpy123.mypeojectdemo.mapper.CommodityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommodityService {

	@Autowired
	private CommodityMapper commodityMapper;

	public List<Commodity> selectUsersCommodity(Long userId){
		return commodityMapper.selectUsersCommodity(userId);
	}

}
