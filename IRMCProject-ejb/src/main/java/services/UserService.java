package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import entities.User;
import iservice.UserServiceLocal;
import iservice.UserServiceRemote;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
public class UserService implements UserServiceRemote, UserServiceLocal {

    @PersistenceContext
    private EntityManager em;


    @Override
    public User login(User u) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username or u.email = :email");
        query.setParameter("username", u.getUserName());
        query.setParameter("email", u.getUserName());
        int resultCount = query.getResultList().size();
        if (resultCount != 0) {
            return (User) query.getSingleResult();
        }
        return null;

    }

    @Override
    public void signup(User us) {
        em.persist(us);

    }

    @Override
    public void EditProfile(User us) {
        em.merge(us);

    }

    @Override
    public User ShowProfile(int id) {
        return em.find(User.class, id);

    }

    @Override
    public User findUser(User user) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email OR u.userName = :username");
        query.setParameter("email", user.getEmail());
        query.setParameter("username", user.getUserName());
        User userFromDB = (User) query.getSingleResult();
        return userFromDB;
    }

    @Override
    public User findUserByMail(String mail) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", mail);
        User userFromDB = (User) query.getSingleResult();
        return userFromDB;
    }

    @Override
    public User findUserById(int id) {
        User user = em.find(User.class, id);
        return user;
    }
    @Override
    public List<User> getUser() {
		// TODO Auto-generated method stub
		return em.createQuery("select d from User d").getResultList();
	}
   
    @Override
	public void removeUser(User user) {
		em.remove(em.merge(user));
		
	}

    
}
