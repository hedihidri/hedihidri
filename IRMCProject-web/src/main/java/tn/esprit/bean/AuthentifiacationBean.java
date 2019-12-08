package tn.esprit.bean;


import entities.User;
import iservice.UserServiceLocal;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class AuthentifiacationBean {

    @EJB
    private UserServiceLocal userServiceLocal;
    private boolean loggedIn = false;
    public static User user = new User();
    private String role;
    private List<User> listUsers = new ArrayList<User>();
    
    public UserServiceLocal getUserServiceLocal() {
		return userServiceLocal;
	}

	public void setUserServiceLocal(UserServiceLocal userServiceLocal) {
		this.userServiceLocal = userServiceLocal;
	}

	@PostConstruct
	public void init() {
		listUsers = userServiceLocal.getUser();
		
	}
	
	
	
    public List<User> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<User> listUsers) {
		this.listUsers = listUsers;
	}
	
	public void userremove(User u) {
		userServiceLocal.removeUser(u);
		init();
		FacesMessage msg = new FacesMessage("user Removed!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public AuthentifiacationBean() {
    	
        // this.user.setUserName("ffafaf");
    }

//	@PostConstruct
//	public void test() {
//		User u = new User();
//		u.setUserName("hedi");
//		user = u;
//	}

    public String doLogin() {
        String navTo = "";
        User found = userServiceLocal.login(user);
        if (found != null) {
            System.out.println(found.getPwd());
            System.out.println(user.getPwd());
            if (BCrypt.checkpw(user.getPwd(), found.getPwd())) {
                System.out.println("It matches");
                if (found.getRoles().equals("admin")) {
                    user = found;
                  //  this.setRole("Admin");
                    loggedIn = true;
                    navTo = "pages/client/profileEdit?faces-redirect=true";
                }
                
                else if  (found.getRoles().equals("medecin")) {
                    user = found;
                  //  this.setRole("Admin");
                    loggedIn = true;
                    navTo = "pages/client/profileEdit1?faces-redirect=true";
                }
                else if  (found.getRoles().equals("patient")) {
                    user = found;
                  //  this.setRole("Admin");
                    loggedIn = true;
                    navTo = "pages/client/profileEdit1?faces-redirect=true";
                }
                
            } else {
                System.out.println("It does not match");
                //navTo = "/template/exempleContent?faces-redirect=true";
            }
            user = found;
            loggedIn = true;
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user);
            User u = (User) context.getExternalContext().getSessionMap().get("user");
            System.out.println(u);
        }
        FacesMessage msg = new FacesMessage("Mot de passe incorrecte");
        FacesContext.getCurrentInstance().addMessage("form_login:form_submit", msg);
        return navTo;
    }

    public String doLogout() {
        user = null;
        loggedIn = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/IRMCProject-web/login.jsf";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
