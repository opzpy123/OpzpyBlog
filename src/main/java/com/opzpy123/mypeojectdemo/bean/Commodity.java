package com.opzpy123.mypeojectdemo.bean;

import lombok.Data;

@Data
public class Commodity {
	private Long id;//商品id
	private Long userid;//商品属于的用户id
	private String name;//商品名
	private String image;//商品展示图片(链接)
	private String description;//商品描述


}
