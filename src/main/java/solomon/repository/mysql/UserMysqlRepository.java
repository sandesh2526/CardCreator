package main.java.solomon.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.solomon.app.domain.User;
import main.java.solomon.repository.UserRepository;

public class UserMysqlRepository implements UserRepository{
	private static Logger LOG = LoggerFactory.getLogger(UserMysqlRepository.class);
	private static Connection connection;
	@Override
	public void saveOrUpdate(User newUser)
	{
		try 
		{
			connection = InitiateMysqlDatabases.getConnection();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?)");
			statement.setString(1, newUser.getId());
			statement.setString(2, newUser.getEmail());
			statement.setString(3, newUser.getFirstName());
			statement.setString(4, newUser.getLastName());
			int affectedRows = statement.executeUpdate();			
			LOG.info("[saveOrUpdate] Added "+affectedRows+" new user");
		} 
		catch (SQLException e)
		{
			LOG.error("[saveOrUpdate] Unble to connect to the database, please check database configurations");
			e.printStackTrace();
		}
	}

	@Override
	public User findByEmail(String email)
	{
		User newUser = null;
		try
		{
			connection = InitiateMysqlDatabases.getConnection();
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
			LOG.debug("[findByEmail] Please check the database configuration");
			e.printStackTrace();
		}
		return newUser;
	}
}