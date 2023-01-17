package main.java.solomon.repository.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitiateMysqlDatabases 
{
	//private BasicDataSource dataSource = new BasicDataSource();	
	private static String jdbcURL = "jdbc:mysql://localhost:3306/card"; //database name at end, here 'card'
	private static Connection connection; 
	private static Logger LOG = LoggerFactory.getLogger(InitiateMysqlDatabases.class);
	
	public InitiateMysqlDatabases() throws SQLException
	{	//Setting up Database
		/*dataSource.setUrl(jdbcURL);
	    dataSource.setUsername("root");				
	    dataSource.setPassword("password");*/
		connection = DriverManager.getConnection(jdbcURL,"root","password");
		//connection = dataSource.getConnection();
	    System.out.println("Able to get the Connection");
		Statement stmt = connection.createStatement();
		
		String query = "SHOW TABLES;";
		LOG.info("[InitiateMysqlDatabase] Executing Query: "+query);
		ResultSet resultSet = stmt.executeQuery(query);
		
		if (!resultSet.next())
		{	
			LOG.info("[InitiateMysqlDatabases(Constructor)] No Tables found in database creating the tables");

			String sql ="CREATE TABLE users(ID varchar(36) primary key,EMAIL nvarchar(120) not null,"
					  + "FIRST_NAME varchar(50),LAST_NAME varchar(50));";
			LOG.info("[InitiateMysqlDatabases(Constructor)] Executing Qury: "+sql);
			stmt.execute(sql);
									  
/*Column*/	sql = "CREATE TABLE columns(ID varchar (36) primary key,NAME NVARCHAR(255));";
			LOG.info("[InitiateMysqlDatabases(Constructor)] Executing Qury: "+sql);
			stmt.execute(sql);
/*Cards*/	sql = "CREATE TABLE cards(ID varchar(36) primary key,"
				+ "TITLE nvarchar(255),DESCRIPTION NVARCHAR(255),COLUMN_ID varchar(36),"
				+ "CREATOR_ID varchar(36) not null,ASSIGNEE_ID varchar(36),creationDate date);";
			LOG.info("[InitiateMysqlDatabases(Constructor)] Executing Qury: "+sql);
			stmt.execute(sql);
			
			sql = "CREATE TABLE passkey(ID varchar(36) PRIMARY KEY NOT NULL,pass VARCHAR(32) NOT NULL)";
		}
	}
	public static Connection getConnection()
	{
		try
		{
			return connection;
		}
		catch (Exception e)
		{
			LOG.debug("[getConnection] Returning null connection object, please check the dataSource configuration");
			e.printStackTrace();
			return null;
		}
	}
}