package main.java.solomon.repository.memnory;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.solomon.app.domain.User;
import main.java.solomon.repository.UserRepository;

public class UserInMemoryRepository implements UserRepository
{
	private final Map<String,User> userDatabase = new HashMap<>();
	private final Map<String, User> userByEmail = new HashMap<>();
	private static final Logger LOG = LoggerFactory.getLogger(UserInMemoryRepository.class);
	
	public void saveOrUpdate(User newUser)
	{
		userDatabase.put(newUser.getId(),newUser);
		userByEmail.put(newUser.getEmail(), newUser);
		LOG.info("Created the user {}",newUser);
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