package iservice;

import entities.User;
import java.util.List;

import javax.ejb.Local;


@Local
public interface UserServiceLocal {
	public User login(User us);
	public void signup(User us);
	public void EditProfile(User us);
	public User ShowProfile(int id);
	User findUser(User user);
	public User findUserByMail(String mail);
	public User findUserById(int id);
	List<User> getUser();
	public void removeUser(User user);
	
}
