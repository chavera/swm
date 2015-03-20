package com.login.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.login.beans.User;
import com.login.constants.Actions;
import com.login.constants.Queries;
import com.login.utils.Commons;
import com.login.utils.ConnMgr;

public class CommonDaos {
	public static String getStatusOfCookie(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.GET_COOKIE_STATUS);
		statement.setObject(1, user.getUsername());
		statement.setObject(2, user.getCookie());
		ResultSet resultSet = statement.executeQuery();
		String status = "";
		while(resultSet.next()){
			status = resultSet.getString(1);
		}
		ConnMgr.closeConnections(connection, statement,resultSet);
		return status;
	}
	
	public static boolean insertNewCookie(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.INSERT_LOGIN_COOKIE);
		statement.setObject(1, user.getUsername());
		statement.setObject(2, user.getTimeStamp());
		statement.setObject(3, user.getCookie());
		statement.setObject(4, Actions.COOKIE_ACTIVE_STATUS);
		int result = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(result == 1)
			return true;
		else
			return false;
	}
	
	public static boolean changeStatusOfCookieToExpired(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_LOGIN_COOKIE);
		statement.setObject(1, Actions.COOKIE_EXPIRED_STATUS);
		statement.setObject(2, user.getUsername());
		statement.setObject(3, user.getCookie());
		int result = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(result==1){
			return true;
		}else{
			return false;
		}
	}
	
	public static int getCountOfUser(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.GET_COUNT_OF_USER);
		statement.setObject(1, user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		int count = 0;
		while(resultSet.next())
			count = resultSet.getInt(1);
		ConnMgr.closeConnections(connection, statement, resultSet);
		return count;
	}
	
	public static User getRole(User user) throws Exception{
		User validUser = new User();
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.GET_VALID_USER_ROLE);
		statement.setObject(1, user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()){
			validUser.setRole(resultSet.getString(1));
		}
		ConnMgr.closeConnections(connection, statement, resultSet);
		return validUser;
	}
	
	public static User getRoleCreatedBy(String role) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.SELECT_USER_WHO_CREATED_A_ROLE);
		statement.setObject(1, role);
		ResultSet resultSet = statement.executeQuery();
		String createdBy = "";
		while(resultSet.next())
			createdBy = resultSet.getString(1);
		ConnMgr.closeConnections(connection, statement, resultSet);
		User user = new User();
		user.setUsername(createdBy);
		return user;
	}
	
	public static User getActionCreatedBy(String action) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.SELECT_USER_WHO_CREATED_AN_ACTION);
		statement.setObject(1, action);
		ResultSet resultSet = statement.executeQuery();
		String createdBy = "";
		while(resultSet.next())
			createdBy = resultSet.getString(1);
		ConnMgr.closeConnections(connection, statement, resultSet);
		User user = new User();
		user.setUsername(createdBy);
		return user;
	}
	
	public static boolean createRole(User adminUser, String role) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.CREATE_NEW_ROLE);
		statement.setObject(1, role);
		statement.setObject(2, Commons.getTime());
		statement.setObject(3, adminUser.getUsername());
		int result = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(result==1)
			return true;
		else
			return false;
	}
	
	public static boolean updateRole(String oldRole, String newRole) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.UPDATE_ROLE);
		statement.setObject(1, newRole);
		statement.setObject(2, oldRole);
		int result = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(result==1)
			return true;
		else
			return false;
	}
	
	public static boolean deleteRole(String deleteRole) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.DELETE_ROLE);
		statement.setObject(1, deleteRole);
		int result = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(result==1)
			return true;
		else
			return false;
	}
	
	public static boolean insertAction(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.INSERT_ACTION_LOG);
		statement.setObject(1, user.getUsername());
		statement.setObject(2, user.getTimeStamp());
		statement.setObject(3, user.getAction());
		int result = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(result==1)
			return true;
		else
			return false;
	}
}
