package test.java.solomon.app.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import main.java.solomon.app.domain.Card;
import main.java.solomon.app.domain.Column;
import main.java.solomon.app.domain.User;
import main.java.solomon.repository.CardRepository;
import main.java.solomon.repository.ColumnRepository;
import main.java.solomon.repository.jdbc.CardJdbcRepository;
import main.java.solomon.repository.jdbc.ColumnJdbcRepository;
import main.java.solomon.repository.jdbc.InitiateDatabases;
import main.java.solomon.repository.jdbc.UserJdbcRepository;
import main.java.solomon.repository.memnory.CardInMemoryRepository;
import main.java.solomon.repository.memnory.ColumnInMemoryRepository;

public class AppTest {
	
	public Column creationOfColumnInDatabase(String columnName)
	{
		Column newColumn = new Column(columnName);
		ColumnJdbcRepository columnJdbcRepository = new ColumnJdbcRepository();
		columnJdbcRepository.save(newColumn);
		Column col = columnJdbcRepository.findByName(columnName);
		assertNotNull(col);
		assertEquals(columnName, col.getName());
		List<Column> columns = columnJdbcRepository.findAllOrderedByPosition();
		assertNotNull(columns);
		return newColumn;
	}
	
	public User userAdditionToDatabaseTest()
	{
		User newUser = new User("SomeEmail@mail.com");
		newUser.setFirstName("Falcon");
		newUser.setLastName("Grey");
		newUser.setId(UUID.randomUUID().toString());
		UserJdbcRepository repo = new UserJdbcRepository();
		//System.out.println("UUID from user:"+newUser.getId());
		repo.saveOrUpdate(newUser);
		//System.out.println("LETS START THAT FIND BY EMAIL THING");
		newUser = repo.findByEmail("SomeEmail@mail.com");
		//System.out.println(newUser.getEmail()+newUser.getFirstName()+newUser.getLastName()+newUser.getId());
		assertNotNull(newUser.getFirstName());
		assertNotNull(newUser.getLastName());
		assertNotNull(newUser.getId());
		return newUser;
	}

	public User userAdditionToDatabaseTest(String mailID)
	{
		User newUser = new User(mailID);
		newUser.setFirstName("Falcon");
		newUser.setLastName("Grey");
		newUser.setId(UUID.randomUUID().toString());
		UserJdbcRepository repo = new UserJdbcRepository();
		//System.out.println("UUID from user:"+newUser.getId());
		repo.saveOrUpdate(newUser);
		//System.out.println("LETS START THAT FIND BY EMAIL THING");
		newUser = repo.findByEmail(mailID);
		//System.out.println(newUser.getEmail()+newUser.getFirstName()+newUser.getLastName()+newUser.getId());
		assertNotNull(newUser.getFirstName());
		assertNotNull(newUser.getLastName());
		assertNotNull(newUser.getId());
		return newUser;
	}

	@Test
	public void databaseCardCreationTest()
	{
		try
		{
			@SuppressWarnings("unused")
			InitiateDatabases  initiateDatabases = new InitiateDatabases();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		Card newCard = new Card("The Stories Of Grim Reapper", userAdditionToDatabaseTest("FalconGreyOriginal@email.com"), creationOfColumnInDatabase("TODO"));
		newCard.setDescription("HEY ADDED SOME DESCRIPTIO ");
		User assignee = userAdditionToDatabaseTest("FlaconGreyFake@email.com");
		newCard.assignTo(assignee);
		CardJdbcRepository repository = new CardJdbcRepository();
		repository.save(newCard);
		Column s = new ColumnJdbcRepository().findByName("TODO");
		
		
		User creator = userAdditionToDatabaseTest("Yotsuya@email.com");
		Card newCard1 = new Card("I am standing on 100 Million Lives", creator, creationOfColumnInDatabase("DONE"));
		newCard1.setDescription("Was Okay, need to check for season 3");
		User assignee1 = userAdditionToDatabaseTest("salvatorCommandor@email.com");
		newCard1.assignTo(assignee1);
		repository.save(newCard1);
		Column someColumn = new ColumnJdbcRepository().findByName("DONE");
		
		assertEquals(newCard1.getAssignee().getEmail(), repository.findByColumn(someColumn).get(0).getAssignee().getEmail());
		//System.out.println("Column is Created: "+someColumn);
		//System.out.println("Printing the ID from databaseCardCrationTest: "+s.getId()+"Name:"+s.getName());
		List<Card> cards = repository.findByColumn(s);		
		assertEquals(cards.get(0).getTitle(),"The Stories Of Grim Reapper");
		/*for (Card card : cards)
		{
			System.out.println("The Card "+card.getTitle()+" is in column: "+card.getColumn().getName());
		}*/
		assertEquals(cards.get(0).getAssignee().getEmail(),"FlaconGreyFake@email.com");
		
		List<Card> cardsByAssignee = new ArrayList<>();
		cardsByAssignee = repository.findByAssignee(assignee);
		assertNotNull(cardsByAssignee.get(0));
		/*for (Card card : cardsByAssignee)
		{
			System.out.println("The Card "+card.getTitle()+" is assigned to:"+card.getAssignee().getEmail());
		}*/
	}
	
	@Test
	public void newCardCreationTest()
	{
		//System.out.println("Executing Test Cases");
		CardRepository repo = new CardInMemoryRepository();
		Column newColumn = columnCreationTest();
		Card newCard = newCardTest(newColumn);
		repo.save(newCard);
		//System.out.println(newCard.getId());
		assertNotNull(newCard.getId());
	}

	private Card newCardTest(Column newColumn)
	{
		String title = "This is a New Card";
		Card newCard = new Card(title,new User("SomeRealPerson@email.com"),newColumn);
		newCard.setAssignee(new User("Assignee@email.com"));
		return newCard;
	}

	private Column columnCreationTest()
	{
		Column newColumn = new Column("SOmeCol");
		ColumnRepository colRepo = new ColumnInMemoryRepository();
		colRepo.save(newColumn);
		return newColumn;
	}
}
