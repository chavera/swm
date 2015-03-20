package com.login.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.login.beans.User;
import com.login.constants.Queries;
import com.login.utils.ConnMgr;

public class ChangePasswordDao {
	
	public boolean changePassword(User user) throws Exception{
		Connection connection = ConnMgr.getConnection();
		PreparedStatement statement = connection.prepareStatement(Queries.CHANGE_PASSWORD);
		statement.setObject(1, user.getPassword());
		statement.setObject(2, user.getUsername());
		int status = statement.executeUpdate();
		ConnMgr.closeConnections(connection, statement);
		if(status==1){
			return true;
		}else{
			return false;
		}
	}
	
}
