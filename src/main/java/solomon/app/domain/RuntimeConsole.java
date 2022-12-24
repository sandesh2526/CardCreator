package main.java.solomon.app.domain;
import java.util.Scanner;
import java.util.UUID;

import main.java.solomon.repository.CardRepository;
import main.java.solomon.repository.ColumnRepository;
import main.java.solomon.repository.UserRepository;
import main.java.solomon.repository.jdbc.CardJdbcRepository;
import main.java.solomon.repository.jdbc.ColumnJdbcRepository;
import main.java.solomon.repository.jdbc.InitiateDatabases;
import main.java.solomon.repository.jdbc.UserJdbcRepository;

public class RuntimeConsole
{
	public Scanner scanner = new Scanner(System.in);
	private final UserRepository userRepository = new UserJdbcRepository();
	private final ColumnRepository columnRepository = new ColumnJdbcRepository();
	private final CardRepository cardRepository = new CardJdbcRepository();
	private static User currentUser = null;
	public void run()
	{	 
		scanner = new Scanner(System.in);
		System.out.println("Let's Get Started With This!!!");
		if(currentUser == null)
		{
			try
			{
				@SuppressWarnings("unused")
				InitiateDatabases initiateDatabases = new InitiateDatabases();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

	        User newUser = enterUser();
	        currentUser = newUser;
		}
        
        System.out.println("Welcome, "+currentUser.getFullName()+"\nWhat you want to do?");
        try 
        {
			do
    		{
            	System.out.println("\n1. Create a card \t 2.Show Cards \t 0.Exit");
            	int choice = scanner.nextInt();
            	scanner.nextLine();
            	if (choice == 1) 
            	{
            		createCard(currentUser);
    			}
            	else if(choice == 2)
            	{
    				showCards();
    			}
            	else if(choice == 0)
            	{
    				return;
    			}
    		}while(true);
		} 
        catch (Exception e)
        {
        	System.out.println("Please Enter one of the above options");
        	run();
		}
	}
	private void createCard(User currentUser)
	{
		System.out.println("Title: ");
		String title = scanner.nextLine();
		System.out.println();

		System.out.println("Column: ");
		String co = scanner.nextLine();
		System.out.println();
		
		System.out.println("Enter Card Description: ");
		String description = scanner.nextLine();
		
		
		Column newColumn = new Column(co);
		
		if(columnRepository.findByName(co) != null)
		{
			newColumn = columnRepository.findByName(co);
		}
		else
		{
			columnRepository.save(newColumn);
		}
		System.out.println();
		
		Card newCard = new Card(title,currentUser,newColumn);
		
		newCard.setDescription(description);
		System.out.println("Assign the Card to Someone: ");
		
		newCard.setCreator(currentUser);
		
		User assignee = enterUser();
		
		newCard.assignTo(assignee);
		
		cardRepository.save(newCard);
		
	}
	private User enterUser()
	{
		System.out.print("Enter email: ");
		String email = scanner.nextLine();
		
		System.out.println(email);
		if(userRepository.findByEmail(email) != null)
		{
			return userRepository.findByEmail(email);
		}
		
		else
		{
			User newUser = new User(email);
			System.out.print("Enter First Name: ");
			String firstName = scanner.next();
			System.out.println();
			System.out.print("Enter Last Name: ");
			String lastName = scanner.next();
			System.out.println();
			
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setId(UUID.randomUUID().toString());
			
			userRepository.saveOrUpdate(newUser);
			
			return newUser;
		}
	}

	private void showCards()
	{
        for (Column column : columnRepository.findAllOrderedByPosition()) 
        {
            String line = column.getName() + ":\t";
            for (Card card : cardRepository.findByColumn(column))
                line += card.getTitle() + "(" + card.getAssignee().getFullName() + "), ";
            line = line.substring(0, line.length() - 2);
            System.out.println(line);
        }
	}
}
