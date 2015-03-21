package org.chavera.swm.login.services;

import org.chavera.swm.login.beans.User;
import org.chavera.swm.login.constants.Actions;
import org.chavera.swm.login.daos.CommonDaos;
import org.chavera.swm.login.daos.UserDao;
import org.chavera.swm.login.utils.Commons;

public class CommonServices {
	public static boolean isValid(User user) throws Exception{
		return isValidUserWithPassword(user);
	}
	
	public static boolean isValidUser(User user) throws Exception{
		User validUser = new UserDao().getValidUser(user);
		if(validUser.getUsername().equals(user.getUsername()))
			return true;
		else{
			return false;
		}
	}
	
	public static boolean insertAction(User user) throws Exception{
		return CommonDaos.insertAction(user);
	}
	
	public static boolean isValidUserWithPassword(User user) throws Exception{
		User validUser = new UserDao().getValidUserWithPassword(user);
		if(isValidUser(user))
			if(validUser.getPassword().equals(user.getPassword()))
				return true;
			else{
				return false;
			}
		else
			return false;
	}
	
	public static boolean isValidAdminUser(User user) throws Exception{
		if(CommonDaos.getRole(user).getRole().equals(Actions.ADMIN_ROLE))
			return true;
		else{
			return false;
		}
	}
	
	public static boolean isValidCookieForAnUser(User user) throws Exception{
		if(CommonDaos.getStatusOfCookie(user).equals(Actions.COOKIE_ACTIVE_STATUS))
			return true;
		else
			return false;
	}
	
	public static boolean isRoleCreatedBy(User adminUser, String oldRole) throws Exception{
		if(CommonDaos.getRoleCreatedBy(oldRole).getUsername().equals(adminUser.getUsername()))
			return true;
		else
			return false;
	}

	
	public static boolean createRole(User adminUser, String role) throws Exception{
		if(isValidAdminUser(adminUser) && isValidCookieForAnUser(adminUser))
			if(CommonDaos.createRole(adminUser, role) && insertActionLog(adminUser, Actions.CREATE_ROLE))
			return true;
		else
			return false;
		else 
			return false;
	}
	
	public static boolean updateRole(User adminUser, String oldRole, String newRole) throws Exception{
		if(isValidAdminUser(adminUser) && isValidCookieForAnUser(adminUser) && isRoleCreatedBy(adminUser, oldRole))
			if(CommonDaos.updateRole(oldRole, newRole) && insertActionLog(adminUser, Actions.UPDATE_ROLE))
			return true;
		else
			return false;
		else
			return false;
	}
	
	public static boolean deleteRole(User adminUser,String deleteRole) throws Exception{
		if(isValidAdminUser(adminUser) && isValidCookieForAnUser(adminUser) && isRoleCreatedBy(adminUser, deleteRole))
			if(CommonDaos.deleteRole(deleteRole) && insertActionLog(adminUser, Actions.DELETE_ROLE))
			return true;
		else
			return false;
		else
			return false;
	}
	
	public static boolean insertNewCookie(User user) throws Exception{
		return CommonDaos.insertNewCookie(user);
	}

	public static boolean changeStatusOfCookieToExpired(User user) throws Exception{
		return CommonDaos.changeStatusOfCookieToExpired(user);
	}
	
	private static boolean insertActionLog(User adminUser, String action) throws Exception{
		String timeStamp = Commons.getTime();
		User actionUser = new User();
		actionUser.setUsername(adminUser.getUsername());
		actionUser.setTimeStamp(timeStamp);
		actionUser.setAction(action);
		return insertAction(actionUser);
	}
}
