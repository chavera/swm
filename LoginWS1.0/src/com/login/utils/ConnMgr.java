package com.login.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.login.beans.DbBean;

public class ConnMgr {
	private static DbBean db;
	public static String dburl;
	private static Properties props;
	private static void setDbDetails() throws Exception{
		props = new Properties();
		db = new DbBean();
		InputStream input = ConnMgr.class.getClassLoader().getResourceAsStream("database.properties");
		props.load(input);
		db.setHost(props.getProperty("host"));
		db.setPort(Integer.parseInt(props.getProperty("port")));
		db.setDbName(props.getProperty("dbname"));
		db.setUsername(props.getProperty("user"));
		db.setPassword(props.getProperty("password"));
		dburl = "jdbc:postgresql://" + db.getHost() + ":" + db.getPort() + "/" + db.getDbName();
	}
	
	public static Connection getConnection() throws Exception{  
	      Connection con = null;
	      setDbDetails();
	      Class.forName(props.getProperty("driver"));     
	      con = DriverManager.getConnection(dburl,db.getUsername(),db.getPassword());
	      //System.out.println("Connection to "+ "'" + dburl + "'" +" made succsessfully ...");
	      return con;
	   }
	
	   public static void closeConnection(Connection connection) throws SQLException{
		   connection.close();
	   }
	   
	   public static void closeConnections(Connection connection,Statement statement, ResultSet resultSet) throws SQLException{
			connection.close();
			statement.close();
			resultSet.close();
		}
	   public static void closeConnections(Connection connection,PreparedStatement statement, ResultSet resultSet) throws SQLException{
			connection.close();
			statement.close();
			resultSet.close();
		}
	   public static void closeConnections (Connection connection,Statement statement ) throws SQLException{
		   	connection.close();
			statement.close();
	   }
	   public static void closeConnections (Connection connection,PreparedStatement statement ) throws SQLException{
		   	connection.close();
			statement.close();
	  }
	   public static void closeConnections(Connection connection,PreparedStatement statement1, Statement statement2, ResultSet resultSet) throws SQLException{
			connection.close();
			statement1.close();
			statement2.close();
			resultSet.close();
		}
}
