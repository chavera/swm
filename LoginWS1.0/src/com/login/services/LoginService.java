package com.login.services;

import com.login.beans.User;
import com.login.constants.Actions;
import com.login.daos.CommonDaos;
import com.login.utils.Commons;

public class LoginService {
	public Object postLoginAction(User user) throws Exception{
		User validUser = new User();
		User actionUser = new User();
		String cookie = Commons.generateCookie(System.currentTimeMillis());
		String timeStamp = Commons.getTime();
		String role = CommonDaos.getRole(user).getRole();
		actionUser.setUsername(user.getUsername());
		actionUser.setCookie(cookie);
		actionUser.setTimeStamp(timeStamp);
		actionUser.setAction(Actions.LOGIN);
		if(CommonServices.isValidUserWithPassword(user)){
			if(CommonServices.insertAction(actionUser) && CommonServices.insertNewCookie(actionUser)){
				validUser.setUsername(user.getUsername());
				validUser.setCookie(cookie);
				validUser.setTimeStamp(timeStamp);
				validUser.setRole(role);
				return validUser;
			}
			else
				return "LOGIN UNSUCCESSFULL";
		}
		else
			return "LOGIN UNSUCCESSFUL";
	}
	
}
