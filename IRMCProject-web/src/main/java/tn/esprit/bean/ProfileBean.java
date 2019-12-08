package tn.esprit.bean;


import entities.User;
import iservice.UserServiceLocal;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped

public class ProfileBean {
	

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private User user = new User();
    private String password;
    private String oldPassword;
    @EJB
    private UserServiceLocal service;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        user = (User) context.getExternalContext().getSessionMap().get("user");
        oldPassword=user.getPwd();
        user.setPwd("");
        System.out.println(user);
    }

    public void editUser() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        User u = (User) context.getExternalContext().getSessionMap().get("user");
        if (u != null) {
            if (BCrypt.checkpw(password, oldPassword)) {
            	System.out.println("aaaaaaa");
                String pass = BCrypt.hashpw(user.getPwd(), BCrypt.gensalt());
                user.setPwd(pass);
                service.EditProfile(user);
                context.getExternalContext().getSessionMap().put("user", user);
                System.out.println("success");
                context.getExternalContext().redirect("/IRMCProject-web/template/dashboard.jsf");
            } else {
                System.out.println("password does not match");
            }
        }
    }
    
	

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}