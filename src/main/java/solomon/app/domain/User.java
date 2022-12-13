package main.java.solomon.app.domain;


public class User
{
		private String email,firstName,lastName,id;
	
	public User(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	@Override
	public String toString()
	{ 
		return this.email;
	}
	public String getFirstName()
	{
		return firstName;	
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;	
	}
	public String getLastName() 
	{
		return lastName;	
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;	
	}
	public String getId()
	{
		return id;	
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getFullName()
	{
		return this.firstName+" "+this.lastName;
	}

}
