package solomon.app.domain;

import java.time.LocalTime;

public class Card
{
	@SuppressWarnings("unused")
	private String title,description,id;
	@SuppressWarnings("unused")
	private User assignee,creator;
	private Column column;
	
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

	public User getAssignee() 
	{
		return assignee;
	}

	public void setAssignee(User assignee) 
	{
		this.assignee = assignee;
	}

	
	public Column getColumn()
	{
		return column;
	}

	public void setColumn(Column column)
	{
		this.column = column;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return this.title;
	}

}
