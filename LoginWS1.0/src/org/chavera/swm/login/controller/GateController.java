package org.chavera.swm.login.controller;

import org.chavera.swm.login.beans.User;
import org.chavera.swm.login.constants.ErrMsgs;
import org.chavera.swm.login.services.ChangePasswordService;
import org.chavera.swm.login.services.CommonServices;
import org.chavera.swm.login.services.LoginService;
import org.chavera.swm.login.services.LogoutService;
import org.chavera.swm.login.services.UserService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class GateController {
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String req){
		LoginService loginService = new LoginService();
		JSONObject userObject = null;
		try{
			userObject = new JSONObject(req.toString());
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User user = new User();
		if(userObject != null){
			try{
				user.setUsername(userObject.getString("username"));
				user.setPassword(userObject.getString("password"));
			} catch(JSONException e){
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_102);
					err.put("ERROR MESSAGE", ErrMsgs.ED_102);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		}
		try{
			if(CommonServices.isValid(user)){
				Object result = loginService.postLoginAction(user);
				if(result instanceof User){
					User userReturn = (User)result;
					JSONObject returnObject = new JSONObject();
					returnObject.put("username", userReturn.getUsername());
					returnObject.put("cookie", userReturn.getCookie());
					returnObject.put("timeStamp", userReturn.getTimeStamp());
					returnObject.put("role", userReturn.getRole());
					return Response.status(200).entity(returnObject.toString()).build();
				} else{
					try{
						JSONObject err = new JSONObject();
						err.put("ERROR CODE", ErrMsgs.EC_201);
						err.put("ERROR MESSAGE", ErrMsgs.ED_201);
						return Response.status(500).entity(err.toString()).build();
					} catch(Exception e1){
					}
				}	
			} else {
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_201);
					err.put("ERROR MESSAGE", ErrMsgs.ED_201);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		} catch(Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_203);
				err.put("ERROR MESSAGE", ErrMsgs.ED_203);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@POST
	@Path("/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(String req){
		LogoutService logoutService = new LogoutService();
		JSONObject userObject = null;
		try{
			userObject = new JSONObject(req);
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User user = new User();
		if(userObject != null){
			try{
				user.setUsername(userObject.getString("username"));
				user.setCookie(userObject.getString("cookie"));
			} catch(JSONException e){
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_102);
					err.put("ERROR MESSAGE", ErrMsgs.ED_102);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		}
		try{
			if(logoutService.postLogoutAction(user)){
				return Response.status(200).entity(new JSONObject().put("message", "SUCCESSFULLY LOGGED OUT").toString()).build();
			}
		} catch(Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_301);
				err.put("ERROR MESSAGE", ErrMsgs.ED_301);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@POST
	@Path("/changePassword")
	@Consumes("application/json")
	@Produces("application/json")
	public Response changePassword(String req){
		ChangePasswordService changePasswordService = new ChangePasswordService();
		JSONObject userObject = null;
		try{
			userObject = new JSONObject(req);
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User user = new User();
		if(userObject != null){
			try{
				user.setUsername(userObject.getString("username"));
				user.setPassword(userObject.getString("password"));
				user.setCookie(userObject.getString("cookie"));
			} catch(JSONException e){
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_102);
					err.put("ERROR MESSAGE", ErrMsgs.ED_102);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		}
		
		try{
			Object result = changePasswordService.changePassword(user);
			if(result instanceof User){
				User reUser = (User)result;
				JSONObject ret = new JSONObject();
				ret.put("username", reUser.getUsername());
				ret.put("role", reUser.getRole());
				ret.put("cookie", reUser.getCookie());
				ret.put("timeStamp", reUser.getTimeStamp());
				return Response.status(200).entity(ret.toString()).build();
			}
		} catch(Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_401);
				err.put("ERROR MESSAGE", ErrMsgs.ED_401);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@POST
	@Path("/createUser")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createUser(String req){
		JSONObject userObject = null;
		try{
			userObject = new JSONObject(req);
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User adminUser = new User();
		User newUser = new User();
		if(userObject != null){
			try{
				adminUser.setUsername(userObject.getString("adminUser"));
				adminUser.setCookie(userObject.getString("adminCookie"));
				newUser.setUsername(userObject.getString("newUser"));
				newUser.setPassword(userObject.getString("newUserpassword"));
				newUser.setRole(userObject.getString("role"));
			} catch(JSONException e){
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_102);
					err.put("ERROR MESSAGE", ErrMsgs.ED_102);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		}
		UserService newUserService = new UserService();
		try{
			if(newUserService.create(adminUser,newUser)){
				return Response.status(200).entity(new JSONObject().put("message", "NEW USER CREATED SUCCESSFULLY").toString()).build();
			}
		} catch(Exception e){
			JSONObject err = new JSONObject();
			try{
				err.put("ERROR CODE", ErrMsgs.EC_501);
				err.put("ERROR MESSAGE", ErrMsgs.ED_501);
			} catch(Exception e1){
			}
		}
		
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@POST
	@Path("/updateUser")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateUser(String req){
		JSONObject obj = null;
		try{
			obj = new JSONObject(req);
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User admin = new User();
		try{
			admin.setUsername(obj.getString("adminUser"));
			admin.setCookie(obj.getString("adminCookie"));
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_102);
				err.put("ERROR MESSAGE", ErrMsgs.ED_102);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User oldUser = new User();
		try{
			oldUser.setUsername(obj.getString("userUsername"));
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_102);
				err.put("ERROR MESSAGE", ErrMsgs.ED_102);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		String newUserRole = "";
		String newUsername = "";
		String newPassword = "";
		try{
			try{
				newUserRole = obj.getString("newUserRole");
			}catch(Exception ex){
				newUserRole = null;
			}
			
			try{
				newUsername = obj.getString("newUsername");
			}catch(Exception ex){
				newUsername = null;
			}
			
			try{
				newPassword = obj.getString("newPassword");
			}catch(Exception ex){
				newPassword = null;
			}
		} catch (Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_102);
				err.put("ERROR MESSAGE", ErrMsgs.ED_102);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User newUser = new User();
		newUser.setPassword(newPassword);
		newUser.setRole(newUserRole);
		newUser.setUsername(newUsername);
		UserService newUserService = new UserService();
		try{
			if(newUserService.updateUser(admin,oldUser,newUser)){
				return Response.status(200).entity(new JSONObject().put("message", "USER UPDATED").toString()).build();
			}
		} catch(Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_601);
				err.put("ERROR MESSAGE", ErrMsgs.ED_601);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@POST
	@Path("/deleteUser")
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteUser(String req){
		JSONObject obj = null;
		try{
			obj = new JSONObject(req);
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User admin = new User();
		User user = new User();
		UserService newUserService = new UserService();
		if(obj != null){
			try{
				admin.setUsername(obj.getString("adminUser"));
				admin.setCookie(obj.getString("adminCookie"));
				user.setUsername(obj.getString("userUsername"));
			} catch(JSONException e){
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_102);
					err.put("ERROR MESSAGE", ErrMsgs.ED_102);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		}
		try{
			if(newUserService.deleteUser(admin, user)){
				return Response.status(200).entity(new JSONObject().put("message", "USER DELETED").toString()).build();
			}
		} catch(Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_701);
				err.put("ERROR MESSAGE", ErrMsgs.ED_701);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@POST
	@Path("/createRole")
	@Consumes("application/json")
	@Produces("application/json")
	public Response newRole(String req){
		JSONObject userObject = null;
		try{
			userObject = new JSONObject(req);
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User adminUser = new User();
		String newRole = "";
		if(userObject != null){
			try{
				adminUser.setUsername(userObject.getString("username"));
				adminUser.setCookie(userObject.getString("cookie"));
				adminUser.setRole(userObject.getString("role"));
				newRole = userObject.getString("newRole");
			} catch(JSONException e){
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_102);
					err.put("ERROR MESSAGE", ErrMsgs.ED_102);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		}
		try{
			if(CommonServices.createRole(adminUser, newRole)){
				return Response.status(200).entity(new JSONObject().put("message", "NEW ROLE CREATED").toString()).build();
			}
		} catch(Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_801);
				err.put("ERROR MESSAGE", ErrMsgs.ED_801);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@POST
	@Path("/updateRole")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateRole(String req) {
		JSONObject userObject = null;
		try{
			userObject = new JSONObject(req);
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User adminUser = new User();
		String oldRole = "";
		String newRole = "";
		if(userObject != null){
			try{
				adminUser.setUsername(userObject.getString("username"));
				adminUser.setCookie(userObject.getString("cookie"));
				adminUser.setRole(userObject.getString("role"));
				oldRole = userObject.getString("oldRole");
				newRole = userObject.getString("newRole");
			} catch(JSONException e){
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_102);
					err.put("ERROR MESSAGE", ErrMsgs.ED_102);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		}
		
		try{
			if(CommonServices.updateRole(adminUser, oldRole, newRole)){
				return Response.status(500).entity(new JSONObject().put("message", "ROLE UPDATED").toString()).build();
			}
		}catch(Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_901);
				err.put("ERROR MESSAGE", ErrMsgs.ED_901);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@POST
	@Path("/deleteRole")
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteRole(String req){
		JSONObject userObject = null;
		try{
			userObject = new JSONObject(req);
		} catch(JSONException e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_101);
				err.put("ERROR MESSAGE", ErrMsgs.ED_101);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		User adminUser = new User();
		
		String deleteRole = "";
		if(userObject != null){
			try{
				adminUser.setUsername(userObject.getString("username"));
				adminUser.setCookie(userObject.getString("cookie"));
				adminUser.setRole(userObject.getString("role"));
				deleteRole = userObject.getString("deleteRole");
			} catch(JSONException e){
				try{
					JSONObject err = new JSONObject();
					err.put("ERROR CODE", ErrMsgs.EC_102);
					err.put("ERROR MESSAGE", ErrMsgs.ED_102);
					return Response.status(500).entity(err.toString()).build();
				} catch(Exception e1){
				}
			}
		}
		
		try{
			if(CommonServices.deleteRole(adminUser, deleteRole)){
				return Response.status(500).entity(new JSONObject().put("message", "ROLE DELETED").toString()).build();
			}
		} catch(Exception e){
			try{
				JSONObject err = new JSONObject();
				err.put("ERROR CODE", ErrMsgs.EC_1001);
				err.put("ERROR MESSAGE", ErrMsgs.ED_1001);
				return Response.status(500).entity(err.toString()).build();
			} catch(Exception e1){
			}
		}
		
		JSONObject err = new JSONObject();
		try{
			err.put("ERROR CODE", ErrMsgs.EC_103);
			err.put("ERROR MESSAGE", ErrMsgs.ED_103);
		} catch(Exception e1){
		}
		return Response.status(500).entity(err.toString()).build();
	}
	
	@GET
	@Path("/fetchAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(){
		return Response.status(200).entity(new UserService().getAllUsers().toString()).build();
	}
	
	@GET
	@Path("/fetch")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAUser(@DefaultValue("0") @QueryParam("username") String username){
		User user = new User();
		user.setUsername(username);
		Object obj;
		try{
			obj = new UserService().getUser(user);
			if(obj instanceof JSONObject){
				return Response.status(200).entity(obj.toString()).build();
			} else {
				return Response.status(500).entity(obj).build();
			}
		} catch (Exception e){
			return Response.status(500).entity("SERVER ERROR").build();
		}
	}
}
