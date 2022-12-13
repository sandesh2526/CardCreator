package main.java.solomon.repository;

import java.util.List;

import main.java.solomon.app.domain.Card;
import main.java.solomon.app.domain.Column;
import main.java.solomon.app.domain.User;

public interface CardRepository
{
	public void save(Card newCard);
	public List<Card> findByColumn(Column column);
	public List<Card> findByAssignee(User Assignee);
	
}
