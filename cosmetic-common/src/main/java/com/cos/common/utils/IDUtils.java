package com.cos.common.utils;

import java.util.Date;
import java.util.Random;

/**
 * 各种id生成策略
 * <p>Title: IDUtils</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年7月22日下午2:32:10
 * @version 1.0
 */
public class IDUtils {

	/**
	 * 图片名生成
	 */
	public static String genImageName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		
		return str;
	}
	
	/**
	 * 商品id生成
	 */
	public static long getGoodsId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//加上三位随机数
		Random random = new Random();
		int end2 = random.nextInt(999);
		//如果不足两位前面补0
		String str = millis + String.format("%03d", end2);
		long id = new Long(str);
		return id;
	}

	/**
	 * 订单id生成
	 * 规则 DDID + 当前时间的时间戳 + 三位随机数
	 */
	public static  String getOrderId() {
		StringBuilder sb = new StringBuilder("DDID");
		sb.append(System.currentTimeMillis());
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		sb.append(end3);
		return sb.toString();
	}

	
	public static void main(String[] args) {
		for(int i=0;i< 100;i++){
		System.out.println(getGoodsId());
		System.out.println(getOrderId());
		System.out.println();}
	}
}
