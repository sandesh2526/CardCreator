package main.java.solomon.repository.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class GetSetPassword
{
	private static String jdbcURL = "jdbc:mysql://localhost:3306/card"; //database name at end, here 'card'
	//private static Logger LOG = LoggerFactory.getLogger(GetSetPassword.class);
	
	public void SetPassword(String uid,String Password) throws ClassNotFoundException
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL,"root","password");
			//InitiateMysqlDatabases initiateMysqlDatabases = new InitiateMysqlDatabases();
			//LOG.info("Datainitiate was successful!	");
			//connection = InitiateMysqlDatabases.getConnection();
			String sqString = "INSERT INTO passkey VALUES('"+uid+"','"+Password+"');"; 
			//LOG.info("[SetPassword] Executing query: "+sqString);
			Statement statement = connection.createStatement();
			int something = statement.executeUpdate(sqString);
			System.out.println(something);
		}
		catch (SQLException e)
		{
			//LOG.error("[SetPassword] while Establishing the Connection with Database, check the database configuration! ");
			e.printStackTrace();
		}
	}
	public String GetPassword(String email)
	{
		String string = "";

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL,"root","password");
			Statement statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery("SELECT pass FROM passkey WHERE id=(SELECT id FROM users WHERE email='"+email+"')");
			rSet.next();
			string = rSet.getString(1);
			System.out.println("[GetSetPassword] [GetPassword] Passkey is: "+string);
		}
		catch (Exception e)
		{
			//LOG.error("[save] ERROR while Establishing the Connection with Database, check the database configuration! ");
			e.printStackTrace();
		}		
		return string;
	}
}