package tn.esprit.bean;


import entities.User;
import iservice.UserServiceLocal;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RegisterBean1 {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private User user = new User();

    @EJB
    private UserServiceLocal service;


    public String addUser() {
        user.setRoles("admin");
        String pass = BCrypt.hashpw(user.getPwd(), BCrypt.gensalt());
        user.setPwd(pass);
        service.signup(user);
        user = new User();
        return "/login?faces-redirect=true";
    }

    public String addUser1() {
     
        String pass = BCrypt.hashpw(user.getPwd(), BCrypt.gensalt());
        user.setPwd(pass);
        service.signup(user);
        user = new User();
        return "/login?faces-redirect=true";
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}