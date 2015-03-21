package org.chavera.swm.login.services;

import org.chavera.swm.login.beans.User;
import org.chavera.swm.login.constants.Actions;
import org.chavera.swm.login.daos.ChangePasswordDao;
import org.chavera.swm.login.utils.Commons;

public class ChangePasswordService {
	
	public Object changePassword(User user) throws Exception{
		ChangePasswordDao changePasswordDao = new ChangePasswordDao();
		User actionUser = new User();
		User newUser = new User();
		String timeStamp = Commons.getTime();
		String cookie = Commons.generateCookie(System.currentTimeMillis());
		actionUser.setTimeStamp(timeStamp);
		actionUser.setCookie(cookie);
		actionUser.setPassword(user.getPassword());
		actionUser.setUsername(user.getUsername());
		actionUser.setAction(Actions.CHANGE_PASSWORD);
		if(CommonServices.isValidCookieForAnUser(user))
			if(changePasswordDao.changePassword(actionUser))
				if(CommonServices.insertAction(actionUser) && 
						CommonServices.insertNewCookie(actionUser)&& 
						CommonServices.changeStatusOfCookieToExpired(user)){
					newUser.setUsername(user.getUsername());
					newUser.setRole(user.getRole());
					newUser.setCookie(cookie);
					newUser.setTimeStamp(timeStamp);
					return newUser;
				}
				else
					return "NEW COOKIE ERROR";
			else
				return "PASSWORD CHANGE ERROR";
		else
			return "GETTING STATUS OF OLD COOKIE ERROR";
	}
}
