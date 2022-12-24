package main.java.solomon.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.solomon.app.domain.User;
import main.java.solomon.repository.UserRepository;

public class UserJdbcRepository implements UserRepository{
	private static String jdbcURL = "jdbc:h2:mem:test";
	private static Logger LOG = LoggerFactory.getLogger(UserJdbcRepository.class);
	private static Connection connection;
	@Override
	public void saveOrUpdate(User newUser)
	{
		try 
		{
			connection = DriverManager.getConnection(jdbcURL);
			//Statement stmt = connection.createStatement();
			//String sql = "CREATE TABLE users(ID varchar(36) primary key,EMAIL varchar(50),FIRST_NAME varchar(50),LAST_NAME varchar(50))";
			//stmt.execute(sql);
			/*sql = "SELECT * from users";
			int affectedRows = stmt.executeUpdate("INSERT INTO users VALUES('54s5dsd5sd65cd1s56das','SomeMailId@maik.vim','SOMEE','SKKKS')");
			ResultSet rSet = stmt.executeQuery(sql);
			rSet.next();
			System.out.println(rSet.getString(1));*/
			/*sql = "INSERT INTO users VALUES('"+newUser.getId()+"','"+newUser.getEmail()+"','"+newUser.getFirstName()+"','"+newUser.getLastName()+"')";
			int affectedRows = stmt.executeUpdate(sql);
			System.out.println("Printing Something to know th difference ");*/
			PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?)");
			statement.setString(1, newUser.getId());
			statement.setString(2, newUser.getEmail());
			statement.setString(3, newUser.getFirstName());
			statement.setString(4, newUser.getLastName());
			//System.out.println(statement.getParameterMetaData().getParameterCount());
			int affectedRows = statement.executeUpdate();
			
			/*sql = "Select * from users";
			ResultSet rSet = stmt.executeQuery(sql);
			while(rSet.next())
			{
				System.out.println("UUID in from database: "+rSet.getString(1));
			}*/
			LOG.info("Added "+affectedRows+" new user");
		} catch (SQLException e)
		{
			LOG.error("Unble to connect to the database, please check database configurations");
			e.printStackTrace();
		}
	}

	@Override
	public User findByEmail(String email)
	{
		User newUser = null;
		try
		{
			connection = DriverManager.getConnection(jdbcURL);
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM users where EMAIL='"+email+"'";
			ResultSet rSet = statement.executeQuery(sql);

			while(rSet.next())
			{
				newUser = new User(rSet.getString(2));
				newUser.setId(rSet.getString(1));
				newUser.setFirstName(rSet.getString(3));
				newUser.setLastName(rSet.getString(4));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return newUser;
	}
}