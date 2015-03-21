package org.chavera.swm.login.services;

import org.chavera.swm.login.beans.User;
import org.chavera.swm.login.constants.Actions;
import org.chavera.swm.login.utils.Commons;

public class LogoutService {
	public boolean postLogoutAction(User user) throws Exception{
		User logoutUser = new User();
		logoutUser.setUsername(user.getUsername());
		logoutUser.setCookie(user.getCookie());
		logoutUser.setAction(Actions.LOGOUT);
		logoutUser.setTimeStamp(Commons.getTime());
		if(CommonServices.isValidCookieForAnUser(logoutUser) && 
				CommonServices.insertAction(logoutUser) && 
				CommonServices.changeStatusOfCookieToExpired(logoutUser))
			return true;
		else
			return false;
	}
}
