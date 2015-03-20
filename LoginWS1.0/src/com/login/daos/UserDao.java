package com.login.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.login.beans.User;
import com.login.constants.Queries;
import com.login.utils.ConnMgr;

public class UserDao {
	public boolean create(User newUser,User adminUser, String timeStamp) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.CREATE_NEW_USER);
		statement.setObject(1, newUser.getUsername());
		statement.setObject(2, newUser.getPassword());
		statement.setObject(3, newUser.getRole());
		statement.setObject(4, timeStamp);
		statement.setObject(5, adminUser.getUsername());
		int result = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(result==1)
			return true;
		else
			return false;
	}

	public boolean updateUser(User OldUser, User newUser) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = null;
		int result = 0;
		if(newUser.getPassword()!=null){
			statement = connection.prepareStatement(Queries.UPDATE_USER_PASSWORD);
			statement.setObject(1, newUser.getPassword());
			statement.setObject(2, OldUser.getUsername());
			result = statement.executeUpdate();
		}
		if(newUser.getRole()!=null){
			statement = connection.prepareStatement(Queries.UPDATE_USER_ROLE);
			statement.setObject(1, newUser.getRole());
			statement.setObject(2, OldUser.getUsername());
			result = statement.executeUpdate();
		}
		if(newUser.getUsername()!=null){
			statement = connection.prepareStatement(Queries.UPDATE_USER_USERNAME);
			statement.setObject(1, newUser.getUsername());
			statement.setObject(2, OldUser.getUsername());
			result = statement.executeUpdate();
		}
		ConnMgr.closeConnections(connection, statement);
		if(result==1)
			return true;
		else
			return false;
	}

	public User getUserCreatedBy(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.SELECT_USER_WHO_CREATED_AN_USER);
		statement.setObject(1, user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		String createdBy = "";
		while(resultSet.next())
			createdBy = resultSet.getString(1);
		ConnMgr.closeConnections(connection, statement, resultSet);
		User ret = new User();
		ret.setUsername(createdBy);
		return ret;
	}
	
	public boolean deleteUser(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.DELETE_USER);
		statement.setObject(1, user.getUsername());
		int result = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(result==1)
			return true;
		else
			return false;
	}
	
	public User getValidUserWithPassword(User user) throws Exception{
		User validUser = new User();
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.GET_USER_WITH_PASSWORD);
		statement.setObject(1, user.getUsername());
		statement.setObject(2, user.getPassword());
		ResultSet resultSet = statement.executeQuery();
		if(CommonDaos.getCountOfUser(user) != 0 && CommonDaos.getCountOfUser(user) == 1){
			while(resultSet.next()){
				validUser.setUsername(resultSet.getString(1));
				validUser.setPassword(resultSet.getString(2));
			}
			ConnMgr.closeConnections(connection, statement, resultSet);
		}
		
		if(validUser.getUsername() == null && validUser.getPassword() == null){
			validUser.setPassword("INVALID PASSWORD");
			return validUser;
		}
		else
			return validUser;
	}

	public User getValidUser(User user) throws Exception{
		User validUser = new User();
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.GET_USER);
		statement.setObject(1, user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		if(CommonDaos.getCountOfUser(user) != 0 && CommonDaos.getCountOfUser(user) == 1){
			while(resultSet.next()){
				validUser.setUsername(resultSet.getString(1));
				ConnMgr.closeConnections(connection, statement, resultSet);
				return validUser;
			}
		}
		else{
			validUser.setUsername("USER NAME NOT FOUND");
			ConnMgr.closeConnections(connection, statement, resultSet);
			return validUser;
		}
		return null;
	}
	
	public ArrayList<User> fetchAllUsers() throws Exception{
		ArrayList<User> usersList = new ArrayList<User>();
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.GET_ALL_USERS);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			User user = new User();
			user.setUsername(resultSet.getString(1));
			user.setRole(resultSet.getString(3));
			user.setTimeStamp(resultSet.getString(4));
			usersList.add(user);
		}
		ConnMgr.closeConnections(connection, statement, resultSet);
		return usersList;
	}
	
	public User fetchUser(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.GET_A_USER);
		statement.setString(1, user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		User userRet = new User();
		while (resultSet.next()) {
			userRet.setUsername(resultSet.getString(1));
			userRet.setRole(resultSet.getString(3));
			userRet.setTimeStamp(resultSet.getString(4));
		}
		ConnMgr.closeConnections(connection, statement, resultSet);
		return userRet;
	}
}
