package solomon.app.domain;

import java.time.LocalTime;

public class Card
{
	private String title;
	@SuppressWarnings("unused")
	private String description;
	@SuppressWarnings("unused")
	private User assignee,creator;
	Column column;
	LocalTime creationTime = LocalTime.now();
	
	public Card(String title,User Creator,Column col)
	{
		this.title = title;
		this.creator = Creator;
		this.column = col;
		System.out.println("\n"+LocalTime.now()+" User ["+Creator+"] created a card ["+title+"] in Column ["+col+"] \n");
		
	}
	
	public void assignTo(User assignee)
	{
		this.assignee = assignee;
		System.out.println("\n"+LocalTime.now()+" Card ["+this.title+"] was assigned to ["+assignee+"] \n");
	}
	
	public void moveTo(Column col)
	{
		this.column = col;
		System.out.println(LocalTime.now()+" Card ["+this.title+"] was moved to ["+col+"] \n");
	}
	
	public void setDescription(String description)
	{
		this.description = description;
		System.out.println(LocalTime.now()+" Card ["+this.title+"] was given description ["+description+"] \n");		
	}
	
}
