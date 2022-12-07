package solomon.app.domain;

import java.util.Scanner;

public class User
{
	Scanner scanner = new Scanner(System.in);
	
	private String email,firstName,lastName;
	
	public User()
	{
		System.out.print("Email: ");
		System.out.println();
		this.email = scanner.nextLine();
		
		System.out.print("First Name: ");
		System.out.println();
		this.firstName = scanner.nextLine();
		
		System.out.print("Last Name: ");
		System.out.println();
		this.lastName = scanner.nextLine();		
	}
	@Override
	public String toString() {
		return this.email;
	}
	
	public String getFirstName() {
		return firstName;
		
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		
	}
	public String getLastName() {
		return lastName;
		
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
		
	}	

}
