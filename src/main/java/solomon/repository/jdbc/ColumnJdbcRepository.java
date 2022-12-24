package main.java.solomon.repository.jdbc;

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

public class ColumnJdbcRepository implements ColumnRepository {

	private Connection connection;
	private static String jdbcURL = "jdbc:h2:mem:test";
	private static final Logger LOG = LoggerFactory.getLogger(ColumnJdbcRepository.class); 
	
	public ColumnJdbcRepository()
	{
		jdbcURL = "jdbc:h2:mem:test";
		System.out.println("ENTERING THE CONSTRUCTOR");
		Logger LOG = LoggerFactory.getLogger(ColumnJdbcRepository.class);
		try
		{
			this.connection = DriverManager.getConnection(jdbcURL);
			System.out.println("CONNECTION IS SET");
		}
		catch (SQLException e)
		{
			LOG.error("UNABLE TO ESTABLISH CONNECTION");
			e.printStackTrace();
		}
	}
	//private Statement statement;
	@SuppressWarnings("unused")
	@Override
	public void save(Column column)
	{
		InitiateDatabases initiateDatabases;
		
		try
		{
			this.connection = DriverManager.getConnection(jdbcURL);
			PreparedStatement statement = connection.prepareStatement("INSERT INTO columns VALUES(?,?)");
			column.setId(UUID.randomUUID().toString());
			System.out.println(column.getId());
			statement.setString(1, column.getId());
			statement.setString(2, column.getName());
			int affected = statement.executeUpdate();
			LOG.info("Added "+affected+" new column");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Column findByName(String name)
	{
		if(connection != null)
		{
			System.out.println("Connection is not NUll");
			
		}
		else {
			System.out.println("COnnection is null");
		}
		LOG.info("ENTERED THE FINDCOLUMNBYNAME");
		Column newColumn = null;
		try
		{
			LOG.info("ENTERED IN THE TRY1");
			Statement state = connection.createStatement();
			LOG.info("ENTERED IN THE TRY BLOCK AFTER THE Statement");
			String sqlQuery = "Select * from columns where NAME='"+name+"'";
			LOG.info("Executing Following Query: \n"+sqlQuery);
			ResultSet rSet = state.executeQuery(sqlQuery);
			if(rSet.next())
			{
				 newColumn = new Column(rSet.getString(2));
				 newColumn.setId(rSet.getString(1));
				 LOG.info("findByName returning a proper value");
			}
			else
			{
				LOG.info("findByName is returning a null object!");
				newColumn = null;
			}
		}
		catch (SQLException e)
		{
			LOG.error("SOME ERROR WHILE CONNECTING DATABASE");
			e.printStackTrace();
		}
		return newColumn;
	}

	@Override
	public List<Column> findAllOrderedByPosition() {
		ArrayList<Column> storedColumns = new ArrayList<>();
		try {
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
			e.printStackTrace();
		}
		return storedColumns;
	}

}
