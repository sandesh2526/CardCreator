package main.java.solomon.app.domain;
//import org.graalvm.compiler.hotspot.replacements.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Card
{
	@SuppressWarnings("unused")
	private String title,description,id;
	@SuppressWarnings("unused")
	private User assignee,creator;
	private Column column;
	private static final Logger LOG = LoggerFactory.getLogger(Card.class);
	
	
	public Card(String title,User Creator,Column col)
	{
		
		this.title = title;
		this.creator = Creator;
		this.column = col;
		
		 //System.out.println("\n"+LocalTime.now()+" User ["+Creator+"] created a card ["+title+"] in Column ["+col+"] \n");
		LOG.info("User {} Created the card in the {} column with title: {}",creator,col,title);
	}
	
	public void assignTo(User assignee)
	{
		this.assignee = assignee;

		LOG.info("Card [{}] was assigned to [{}]", title, assignee.getEmail());

		//System.out.println("\n"+LocalTime.now()+" Card ["+this.title+"] was assigned to ["+assignee+"] \n");
	}
	
	public void moveTo(Column col)
	{
		this.column = col;
		
		LOG.info("Card {} moved to {} column"+this.title,col);
		//System.out.println(LocalTime.now()+" Card ["+this.title+"] was moved to ["+col+"] \n");
	}
	
	public void setDescription(String description)
	{
		this.description = description;
		//System.out.println(LocalTime.now()+" Card ["+this.title+"] was given description ["+description+"] \n");
		
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
