package solomon.app.domain;
import java.util.Scanner;
import java.util.UUID;
import solomon.repository.CardRepository;
import solomon.repository.ColumnRepository;
import solomon.repository.UserRepository;

public class RuntimeConsole
{
	public Scanner scanner = new Scanner(System.in);
	UserRepository userRepository = new UserRepository();
	ColumnRepository columnRepository = new ColumnRepository();
	CardRepository cardRepository = new CardRepository();
	
	public void run()
	{	
		System.out.println("Let's Get Started With This!!!");

        User currentUser = enterUser();
        
        System.out.println("Welcome, "+currentUser.getFullName()+"\nWhat you want to do?");
        
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
	private void createCard(User currentUser)
	{
		System.out.println("Title: ");
		String title = scanner.nextLine();
		System.out.println();

		System.out.println("Column: ");
		String co = scanner.nextLine();
		System.out.println();
		
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
		
		System.out.println("Assign the Card to Someone: ");
		
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
			User newUser = new User();
			System.out.print("Enter First Name: ");
			String firstName = scanner.next();
			System.out.println();
			System.out.print("Enter Last Name: ");
			String lastName = scanner.next();
			System.out.println();
			
			newUser.setEmail(email);
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
