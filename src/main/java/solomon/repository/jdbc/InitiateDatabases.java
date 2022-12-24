package main.java.solomon.repository.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InitiateDatabases 
{
	private String jdbcURL = "jdbc:h2:mem:test";
	Connection connection;
	public InitiateDatabases() throws SQLException
	{
		connection = DriverManager.getConnection(jdbcURL);		
		Statement stmt = connection.createStatement();
		String sql = "CREATE TABLE users(ID varchar(36) primary key,EMAIL nvarchar(120) not null,"
				+ "FIRST_NAME varchar(50),LAST_NAME varchar(50));"
				+ "CREATE TABLE columns(ID varchar (36) primary key,NAME NVARCHAR(50));"
	/*Cards	*/	+ "CREATE TABLE cards(ID varchar(36) primary key,"
				+ "TITLE nvarchar(255),DESCRIPTION clob,COLUMN_ID varchar(36),"
				+ "CREATOR_ID varchar(36) not null,ASSIGNEE_ID varchar(36));";
		stmt.execute(sql);
		stmt.executeUpdate("INSERT INTO cards VALUES('HASjfkasjkfn5dfcdfhu5c','SomeTitle','DESDESDESDESDESDEDSDESEDSESSDESDSESDS','jjfsdfkjsdfnsjk5d5f','Jdsjfnkjsnsdfnsd5dsdsd45d','dsjksajcnajasd')");
		ResultSet rSet = stmt.executeQuery("Select * from cards");
		if(rSet.next())
		{
			System.out.println("Not empty"+rSet.getString(1));
		}
		else
		{
			System.out.println("EMPTY!!");
		}
	}
	public Connection getConnection()
	{
		return this.connection;
	}
		
}