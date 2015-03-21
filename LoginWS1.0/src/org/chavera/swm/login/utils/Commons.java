package org.chavera.swm.login.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Commons {
	private static Random random = new Random(); 
	
	public static String generateCookie(Long num){
		int randonNum = random.nextInt();
		if(randonNum<0){
			randonNum = randonNum*-1;
		}
		Long newNum = Long.reverse(num) + randonNum;
		return num2StringRandom(newNum);
	}
	
	public static String num2StringRandom(Long num){
		String code = "";
		int rflag = 1+(int)(Math.random()*25);
		Object[] base = DumbJob.anything2Array(num);
		for(Object i : base){
			Long longval = Long.parseLong(i.toString());
			code += DumbJob.num2Char(DumbJob.numBtw1And26(rflag + longval));
		}
		return code;
	}
	
	public static String getTime(){
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
	}
}
