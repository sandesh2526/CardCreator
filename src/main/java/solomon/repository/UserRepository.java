package main.java.solomon.repository;

import main.java.solomon.app.domain.User;

public interface UserRepository
{
	public void saveOrUpdate(User newUser);
	public User findByEmail(String email);
}
