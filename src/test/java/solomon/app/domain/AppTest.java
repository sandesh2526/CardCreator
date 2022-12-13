package test.java.solomon.app.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import main.java.solomon.app.domain.Card;
import main.java.solomon.app.domain.Column;
import main.java.solomon.app.domain.User;
import main.java.solomon.repository.CardInMemoryRepository;
import main.java.solomon.repository.CardRepository;
import main.java.solomon.repository.ColumnInMemoryRepository;
import main.java.solomon.repository.ColumnRepository;

public class AppTest {

	@Test
	public void newCardCreationTest()
	{
		System.out.println("Executing Test Cases");
		CardRepository repo = new CardInMemoryRepository();
		Column newColumn = columnCreationTest();
		Card newCard = newCardTest(newColumn);
		repo.save(newCard);
		System.out.println(newCard.getId());
		assertNotNull(newCard.getId());
	}

	private Card newCardTest(Column newColumn)
	{
		String title = "This is a New Card";
		Card newCard = new Card(title,new User("SomeRealPerson@email.com"),newColumn);
		newCard.setAssignee(new User("Assignee@email.com"));
		return newCard;
	}
 

	private Column columnCreationTest() {
		Column newColumn = new Column("SOmeCol");
		ColumnRepository colRepo = new ColumnInMemoryRepository();
		colRepo.save(newColumn);
		return newColumn;
	}
}
