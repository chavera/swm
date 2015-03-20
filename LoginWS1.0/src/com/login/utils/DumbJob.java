package com.login.utils;

public class DumbJob {
	
	public static String num2Char(Long num){
		return ""+(char)(num+96);
	}
	
	public static Object[] anything2Array(Object obj){
		if (obj instanceof String){
			return string2Array((String)obj);
		}
		else if (obj instanceof Long || obj instanceof Integer){
			return string2Array(obj.toString());
		}
		else
			return null;
	}
	
	public static Object[] string2Array(String str){
		Object[] objArr = new Object[str.length()];
		for(int i = 0; i<str.length() ; i++){
			if(str.charAt(i)!='-')
				objArr[i] = str.charAt(i);
			else
				objArr[i] = '0';
		}
		return objArr;
	}
	
	public static Long numBtw1And26(Long num){
		if(num>=1 && num<=26)
			return num;
		else{
			num = num - 26;
			return numBtw1And26(num);
		}
	}
}
