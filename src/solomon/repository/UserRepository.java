package solomon.repository;

import java.util.HashMap;
import java.util.Map;

import solomon.app.domain.User;

public class UserRepository
{
	private final Map<String,User> userDatabase = new HashMap<>();
	private final Map<String, User> userByEmail = new HashMap<>();
	
	public void saveOrUpdate(User newUser)
	{
		System.out.println("The Email Id matches: "+newUser.getEmail());
		userDatabase.put(newUser.getId(),newUser);
		userByEmail.put(newUser.getEmail(), newUser);
	}
	
	public User findByEmail(String email) 
	{
		if(userByEmail.containsKey(email))
		{
			return userByEmail.get(email);
		}
		else
		{
			return null;
		}
		
	}
}