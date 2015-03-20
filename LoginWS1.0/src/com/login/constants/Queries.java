package com.login.constants;

public class Queries {
	
	// Actions CRUD
	public static final String INSERT_ACTION_LOG = "INSERT INTO users.action_log VALUES(?,?,?)";
	public static final String CREATE_NEW_ACTION = "INSERT INTO users.actions VALUES (?,?,?)";
	public static final String UPDATE_ACTION = "UPDATE users.actions SET action_item = ? WHERE action_item = ?";
	public static final String SELECT_USER_WHO_CREATED_AN_ACTION = "SELECT \"createdBy\" FROM users.actions WHERE action_item = ?";
	public static final String DELETE_ACTION = "DELETE FROM users.actions WHERE action_item = ?";
	
	// Users CRUD
	public static final String CREATE_NEW_USER = "INSERT INTO users.users VALUES (?,?,?,?,?)";
	public static final String GET_USER_WITH_PASSWORD = "SELECT username,password FROM users.users WHERE username = ? AND password = ?";
	public static final String GET_COUNT_OF_USER = "SELECT count(*) FROM users.users WHERE username = ?";
	public static final String GET_USER = "SELECT username FROM users.users WHERE username = ?";
	public static final String GET_VALID_USER_ROLE = "SELECT role FROM users.users WHERE username = ?";
	public static final String CHANGE_PASSWORD = "UPDATE users.users SET password = ? WHERE username = ?";
	public static final String SELECT_USER_WHO_CREATED_AN_USER = "SELECT \"createdBy\" FROM users.users WHERE username = ?";
	public static final String UPDATE_USER_USERNAME = "UPDATE users.users SET username = ? WHERE username = ?";
	public static final String UPDATE_USER_PASSWORD = "UPDATE users.users SET password = ? WHERE username = ?";
	public static final String UPDATE_USER_ROLE = "UPDATE users.users SET role = ? WHERE username = ?";
	public static final String DELETE_USER = "DELETE FROM users.users WHERE username = ?";
	public static final String GET_ALL_USERS = "SELECT * FROM users.users";
	public static final String GET_A_USER = "SELECT * FROM users.users WHERE username = ?";
	
	// Cookies CRUD
	public static final String INSERT_LOGIN_COOKIE = "INSERT INTO users.login_cookies VALUES(?,?,?,?)";
	public static final String UPDATE_LOGIN_COOKIE = "UPDATE users.login_cookies SET status = ? WHERE username = ? AND login_cookie = ?";
	public static final String GET_COOKIE_STATUS = "SELECT status FROM users.login_cookies WHERE username = ? AND login_cookie = ?";
	
	// Roles CRUD
	public static final String CREATE_NEW_ROLE = "INSERT INTO users.roles VALUES (?,?,?)";
	public static final String UPDATE_ROLE = "UPDATE users.roles SET role_name = ? WHERE role_name = ?";
	public static final String SELECT_USER_WHO_CREATED_A_ROLE = "SELECT \"createdBy\" FROM users.roles WHERE role_name = ?";
	public static final String DELETE_ROLE = "DELETE FROM users.roles WHERE role_name = ?";
	
}
