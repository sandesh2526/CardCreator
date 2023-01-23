package main.java.solomon.repository.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.solomon.app.domain.Column;
import main.java.solomon.repository.ColumnRepository;

public class ColumnMysqlRepository implements ColumnRepository {

	private static Connection connection;
	private static final Logger LOG = LoggerFactory.getLogger(ColumnMysqlRepository.class); 
	private static String jdbcURL = "jdbc:mysql://localhost:3306/card"; //database name at end, here 'card'

	public ColumnMysqlRepository()
	{
		System.out.println("ENTERING THE CONSTRUCTOR");
		Logger LOG = LoggerFactory.getLogger(ColumnMysqlRepository.class);
		
		connection = InitiateMysqlDatabases.getConnection();
		LOG.info("[(Constructor)] CONNECTION IS SET");
	}
	//private Statement statement;
	@Override
	public void save(Column column)
	{		
		try
		{
			connection = DriverManager.getConnection(jdbcURL,"root","password");
			PreparedStatement statement = connection.prepareStatement("INSERT INTO columns VALUES(?,?)");
			column.setId(UUID.randomUUID().toString());
			
			statement.setString(1, column.getId());
			statement.setString(2, column.getName());
			
			LOG.info("EXECUTING query: "+statement.toString());
			int affected = statement.executeUpdate();
			LOG.info("[save] Added "+affected+" new column");
		}
		catch (SQLException e)
		{
			System.out.println("Some new Behaviour");
			e.printStackTrace();
		}
	}

	@Override
	public Column findByName(String name)
	{
		LOG.info("[findByName] ENTERED THE findByName("+name+")");
		Column newColumn = null;
		try
		{
			//Statement stmt = InitiateMysqlDatabases.getConnection().createStatement();
			connection = DriverManager.getConnection(jdbcURL,"root","password");
			Statement stmt = connection.createStatement();
			String sqlQuery = "Select * from columns where NAME='"+name+"'";
			
			ResultSet rSet = stmt.executeQuery(sqlQuery);
			
			if(rSet.next())
			{
				 newColumn = new Column(rSet.getString(2));
				 newColumn.setId(rSet.getString(1));
				 LOG.info("[findByName] returning a value");
			}
			
			else
			{
				LOG.info("[findByName] returning a null object!");
				newColumn = null;
			}
		}
		catch (SQLException e)
		{
			LOG.error("[findByName] SOME ERROR WHILE CONNECTING DATABASE");
			e.printStackTrace();
		}
		return newColumn;
	}

	@Override
	public List<Column> findAllOrderedByPosition() {
		ArrayList<Column> storedColumns = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(jdbcURL,"root","password");		
			Statement statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery("SELECT * from columns");
			while(rSet.next())
			{
				Column column = new Column(rSet.getString(2));
				column.setId(rSet.getString(1));
				storedColumns.add(column);
			}
		}
		catch (SQLException e)
		{
			LOG.debug("[findAllOrderedByPosition] Some Exception While Fetching the values");
			e.printStackTrace();
		}
		return storedColumns;
	}

}
