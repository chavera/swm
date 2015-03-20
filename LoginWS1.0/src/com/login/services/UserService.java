package com.login.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.login.beans.User;
import com.login.constants.Actions;
import com.login.daos.UserDao;
import com.login.utils.Commons;

public class UserService {
	public Boolean create(User adminUser,User newUser) throws Exception{
		if(CommonServices.isValidUser(adminUser) && CommonServices.isValidAdminUser(adminUser)){
			if(CommonServices.isValidCookieForAnUser(adminUser)){
				UserDao userDao = new UserDao();
				String timeStamp = Commons.getTime();
				User actLogUser = new User();
				actLogUser.setUsername(adminUser.getUsername());
				actLogUser.setTimeStamp(timeStamp);
				actLogUser.setAction(Actions.CREATE_NEW_USER);
				if(userDao.create(newUser, adminUser, timeStamp) && CommonServices.insertAction(actLogUser))
					return true;
				else
					return false;
			}
			return false;
		}
		return false;
	}

	public boolean updateUser(User admin,User oldUser, User newUser) throws Exception{
		UserDao userDao = new UserDao();
		String timeStamp = Commons.getTime();
		User actLogUser = new User();
		actLogUser.setUsername(admin.getUsername());
		actLogUser.setTimeStamp(timeStamp);
		actLogUser.setAction(Actions.UPDATE_USER);
		if(CommonServices.isValidUser(admin) && CommonServices.isValidCookieForAnUser(admin) && isUserCreatedBy(admin,oldUser))
			if(userDao.updateUser(oldUser,newUser) && CommonServices.insertAction(actLogUser))
				return true;
			else
				return false;
		else
			return false;
	}

	private boolean isUserCreatedBy(User admin, User user) throws Exception{
		UserDao userDao = new UserDao();
		if(userDao.getUserCreatedBy(user).getUsername().equals(admin.getUsername()))
			return true;
		else
			return false;
	}
	
	public boolean deleteUser(User admin,User user) throws Exception{
		UserDao userDao = new UserDao();
		String timeStamp = Commons.getTime();
		User actLogUser = new User();
		actLogUser.setUsername(admin.getUsername());
		actLogUser.setTimeStamp(timeStamp);
		actLogUser.setAction(Actions.DELETE_USER);
		if(CommonServices.isValidUser(admin) && CommonServices.isValidCookieForAnUser(admin) && isUserCreatedBy(admin,user))
			if(userDao.deleteUser(user) && CommonServices.insertAction(actLogUser))
				return true;
			else
				return false;
		else
			return false;
	}
	
	public Object getAllUsers(){
		UserDao usd = new UserDao();
		JSONArray users = new JSONArray();
		try{
			ArrayList<User> listUsers = usd.fetchAllUsers();
			if(!listUsers.isEmpty()){
				for(User user : listUsers){
					JSONObject userObj = new JSONObject();
					userObj.put("username", user.getUsername());
					userObj.put("role", user.getRole());
					userObj.put("created on",user.getTimeStamp());
					users.put(userObj);
				}
			}
		} catch (Exception e){
			
		}
		if(users.length() != 0){
			return users;
		} else {
			return new JSONArray().put("NO USERS LISTED");
		}
	}
	
	public Object getUser(User user){
		JSONObject obj = new JSONObject();
		try{
			User userRet = new UserDao().fetchUser(user);
			if(userRet.getUsername() != null){
				obj.put("username", userRet.getUsername());
				obj.put("role", userRet.getRole());
				obj.put("created on", userRet.getTimeStamp());
			} else{
				obj.put("response", "NO USERS ASSOCIATED WITH THIS USERNAME");
			}
			return obj;
		} catch (Exception e){
			e.printStackTrace();
			return "SERVER OCCURED";
		}
	}
}
