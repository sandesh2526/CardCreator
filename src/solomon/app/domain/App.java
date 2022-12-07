package solomon.app.domain;
import java.util.Scanner;

public class App
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		
		Column inProgress = new Column("In Progress");
		User creator;
		
		System.out.println("Let's Get Started With This!!!");

		creator = new User();
		//User assignee = new User("assignee@email.com");
		boolean s = false;
		do
		{
			System.out.println("Title: ");
			String title = scanner.nextLine();
			
			System.out.println("Column: ");
			String co = scanner.nextLine();
			
			Card c1 = new Card(title, creator, new Column(co));
			
			System.out.println("You just created a card. Assign it to your friend.\n");
			
			User assignee = new User();
			c1.assignTo(assignee);
			c1.moveTo(inProgress);
			System.out.println("Do you want to create another card(Y/N)?");
			char choice = scanner.next().charAt(0);
			scanner.nextLine();
			if(choice == "Y".charAt(0))
			{
				s = true;
			}
			else
			{	
				s = false;
			}
		}while(s);
		scanner.close();

		/*Card c2 = new Card("Task #2", creator, todo);
		c2.assignTo(assignee);
		c2.setDescription("Won't fix it, created by mistake");
		c2.moveTo(done);
		c2.assignTo(creator);
		 */
		
	}
}
