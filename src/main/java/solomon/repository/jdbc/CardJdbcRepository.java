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

import main.java.solomon.app.domain.Card;
import main.java.solomon.app.domain.Column;
import main.java.solomon.app.domain.User;
import main.java.solomon.repository.CardRepository;

public class CardJdbcRepository implements CardRepository{
	private static String jdbcURL = "jdbc:h2:mem:test";
	private Connection connection;
	
	/*
	 * @see main.java.solomon.repository.CardRepository#save(main.java.solomon.app.domain.Card)
	 * @param Card
	 * @
	 */
	@Override
	public void save(Card newCard)
	{
		try
		{
			this.connection = DriverManager.getConnection(jdbcURL);
			PreparedStatement statement = connection.prepareStatement("INSERT INTO cards VALUES(?,?,?,?,?,?)");
			newCard.setId(UUID.randomUUID().toString());
			statement.setString(1, newCard.getId());
			statement.setString(2, newCard.getTitle());
			statement.setString(3, newCard.getDescription());
			statement.setString(4, newCard.getColumn().getId());
			statement.setString(5, newCard.getCreator().getId());
			statement.setString(6, newCard.getAssignee().getId());
			statement.executeUpdate();
			
			//Statement statement2 = connection.createStatement();
			//statement.execute();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR AT Establishing the Connection to Database please check the database configuration! ");
			e.printStackTrace();
		}		
	}

	@Override
	public List<Card> findByColumn(Column column)
	{
		ArrayList<Card> cards = new ArrayList<>();
		try 
		{
			Statement statement = connection.createStatement();
			String sqlString = "SELECT M.ID AS CARD_ID,M.Title AS CARD_TITLE,M.Description AS CARD_DESCRIPTION,M.COL_ID,M.COL_NAME,M.Creator,M.CREATOR_FIRST_NAME,M.CREATOR_LAST_NAME,M.CREATOR_EMAIL,M.Assignee,F.First_Name AS ASSIGNEE_FIRST_NAME,F.last_name AS ASSIGNEE_LAST_NAME,F.email AS ASSIGNEE_EMAIL FROM "
					+ "(SELECT S.ID,S.Title,S.Description,S.COL_ID,S.COL_NAME,S.Creator,U.First_Name AS CREATOR_FIRST_NAME,U.Last_NAME AS CREATOR_LAST_NAME,U.EMAIL AS CREATOR_EMAIL,S.Assignee FROM "
					+ "(SELECT CA.ID,CA.Title,CA.Description,CA.CREATOR_ID AS CREATOR,CA.Assignee_ID AS ASSIGNEE,CL.ID AS COL_ID,CL.NAME AS COL_NAME FROM cards CA CROSS JOIN columns CL where CA.column_id = CL.ID AND CL.Name='"+column.getName()+"')"
					+ "AS S CROSS JOIN USERS U WHERE S.CREATOR=U.ID) "
					+ "AS M CROSS JOIN USERS F WHERE M.Assignee=F.ID";
			
			System.out.println("EXECUTING THE FOLLOWING QUERY:\n"+sqlString);
			
			ResultSet rSet = statement.executeQuery(sqlString);
			if(!(rSet == null))
			{
				while(rSet.next())
				{

						User assignee = new User(rSet.getString(rSet.findColumn("Assignee_EMAIL")));
						assignee.setFirstName(rSet.getString(rSet.findColumn("Assignee_FIRST_NAME")));
						assignee.setLastName(rSet.getString(rSet.findColumn("Assignee_LAST_NAME")));
						assignee.setId(rSet.getString(rSet.findColumn("Assignee")));
						
						User creator = new User(rSet.getString(rSet.findColumn("Creator_Email")));
						creator.setFirstName(rSet.getString(rSet.findColumn("Creator_First_Name")));
						creator.setLastName(rSet.getString(rSet.findColumn("Creator_Last_Name")));
						creator.setId(rSet.getString(rSet.findColumn("Creator")));
						
						Column newColumn = new Column(rSet.getString(rSet.findColumn("COL_NAME")));
						newColumn.setId(rSet.getString(rSet.findColumn("COL_ID")));
						
						Card newCard = new Card(rSet.getString(rSet.findColumn("CARD_TITLE")), creator, newColumn);
						newCard.setAssignee(assignee);
						newCard.setDescription(rSet.getString(rSet.findColumn("CARD_DESCRIPTION")));
						newCard.setId(rSet.getString(rSet.findColumn("CARD_ID")));
						
						cards.add(newCard);
						System.out.println("Assignee ID is: "+assignee.getEmail());
						System.out.println("Creator Name is: "+creator.getFullName());
						System.out.println("COLUMN NAME IS: "+newColumn.getName());						
					
					/*for(int i=1;i<=rSet.getMetaData().getColumnCount();i++)
					{
						System.out.print(rSet.getString(i)+"\t");
					}*/
					System.out.println();
					//Column newColumn = new Column(rSet.getString(8));
					//newColumn.setId(rSet.getString(7));
					//System.out.println("Column ID is: "+rSet.findColumn("Name"));
					//Card newCard = new Card(rSet.getString(2), rSet.getString(3),newColumn); 
				}
			}
			else 
			{
				System.out.println("No results YET");
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return cards;
	}

	@Override
	public List<Card> findByAssignee(User Assignee)
	{
		ArrayList<Card> cards = new ArrayList<>();
		try
		{
			Statement statement = connection.createStatement();
			String sqlString = "SELECT M.ID AS CARD_ID,M.Title AS CARD_TITLE,M.Description AS CARD_DESCRIPTION,M.COL_ID,M.COL_NAME,M.Creator,M.CREATOR_FIRST_NAME,M.CREATOR_LAST_NAME,M.CREATOR_EMAIL,M.Assignee,F.First_Name AS ASSIGNEE_FIRST_NAME,F.last_name AS ASSIGNEE_LAST_NAME,F.email AS ASSIGNEE_EMAIL FROM "
					+ "(SELECT S.ID,S.Title,S.Description,S.COL_ID,S.COL_NAME,S.Creator,U.First_Name AS CREATOR_FIRST_NAME,U.Last_NAME AS CREATOR_LAST_NAME,U.EMAIL AS CREATOR_EMAIL,S.Assignee FROM "
					+ "(SELECT CA.ID,CA.Title,CA.Description,CA.CREATOR_ID AS CREATOR,CA.Assignee_ID AS ASSIGNEE,CL.ID AS COL_ID,CL.NAME AS COL_NAME FROM cards CA CROSS JOIN columns CL where CA.column_id = CL.ID)"
					+ "AS S CROSS JOIN USERS U WHERE S.CREATOR=U.ID) "
					+ "AS M CROSS JOIN USERS F WHERE M.Assignee=F.ID AND F.ID='"+Assignee.getId()+"'";
			
			System.out.println("EXECUTING THE FOLLOWING QUERY:\n"+sqlString);
			
			ResultSet rSet = statement.executeQuery(sqlString);
			if(!(rSet == null))
			{
				while(rSet.next())
				{

						User assignee = new User(rSet.getString(rSet.findColumn("Assignee_EMAIL")));
						assignee.setFirstName(rSet.getString(rSet.findColumn("Assignee_FIRST_NAME")));
						assignee.setLastName(rSet.getString(rSet.findColumn("Assignee_LAST_NAME")));
						assignee.setId(rSet.getString(rSet.findColumn("Assignee")));
						
						User creator = new User(rSet.getString(rSet.findColumn("Creator_Email")));
						creator.setFirstName(rSet.getString(rSet.findColumn("Creator_First_Name")));
						creator.setLastName(rSet.getString(rSet.findColumn("Creator_Last_Name")));
						creator.setId(rSet.getString(rSet.findColumn("Creator")));
						
						Column newColumn = new Column(rSet.getString(rSet.findColumn("COL_NAME")));
						newColumn.setId(rSet.getString(rSet.findColumn("COL_ID")));
						
						Card newCard = new Card(rSet.getString(rSet.findColumn("CARD_TITLE")), creator, newColumn);
						newCard.setAssignee(assignee);
						newCard.setDescription(rSet.getString(rSet.findColumn("CARD_DESCRIPTION")));
						newCard.setId(rSet.getString(rSet.findColumn("CARD_ID")));
						
						cards.add(newCard);
						System.out.println("Assignee ID is: "+assignee.getEmail());
						System.out.println("Creator Name is: "+creator.getFullName());
						System.out.println("COLUMN NAME IS: "+newColumn.getName());						
					
					/*for(int i=1;i<=rSet.getMetaData().getColumnCount();i++)
					{
						System.out.print(rSet.getString(i)+"\t");
					}*/
					System.out.println();
					//Column newColumn = new Column(rSet.getString(8));
					//newColumn.setId(rSet.getString(7));
					//System.out.println("Column ID is: "+rSet.findColumn("Name"));
					//Card newCard = new Card(rSet.getString(2), rSet.getString(3),newColumn); 
				}
			}
			else 
			{
				System.out.println("No results YET");
			}
		}
		catch(Exception e)
		{
			System.out.println("Aborting Function Card By Assignee");
			e.printStackTrace();
		}
		return cards;
	}

}
