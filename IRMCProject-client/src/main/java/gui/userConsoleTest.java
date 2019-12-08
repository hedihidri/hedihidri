package gui;



import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.remoting.NetworkUtil;


import entities.User;

import iservice.UserServiceLocal;
import iservice.UserServiceRemote;
import services.UserService;

public class userConsoleTest {

	public static void main(String[] args) throws NamingException {
		InitialContext ctx = new InitialContext();
		UserServiceRemote proxyuser = (UserServiceRemote) ctx.lookup("IRMCProject-ear/IRMCProject-ejb/UserService!iservice.UserServiceRemote");
		User user=new User();
		user.setUserName("aa");
		user.setPwd("aa");
//		proxyuser.signup(user);
//		user=proxyuser.findUserById(1);
	    
	//us.login(user);
	System.out.println(proxyuser.login(user));
	
	}
}
