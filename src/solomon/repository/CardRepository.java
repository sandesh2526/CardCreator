package solomon.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import solomon.app.domain.Card;
import solomon.app.domain.Column;
import solomon.app.domain.User;

public class CardRepository
{
	private final Map<String, Card> cardByID = new LinkedHashMap<>();
	private final Map<String, List<Card>> cardByAssignee = new HashMap<>();
	private final Map<String, List<Card>> cardByColumn = new HashMap<>();
	
	public void save(Card newCard)
	{
		if(newCard.getId() != null)
		{
			throw new IllegalArgumentException("The card already exists: "+newCard.getId());
		}
		
		newCard.setId(UUID.randomUUID().toString());
		cardByID.put(newCard.getId(), newCard);
		
		List<Card> cardsByColumn = cardByColumn.computeIfAbsent(newCard.getColumn().getId(), (c) -> new ArrayList<>());
		cardsByColumn.add(newCard);
		
		List<Card> cardsByAssignee = cardByAssignee.computeIfAbsent(newCard.getAssignee().getId(), (c) -> new ArrayList<>());
		cardsByAssignee.add(newCard);
		
	}
	
	public List<Card> findByColumn(Column column)
	{
		return cardByColumn.get(column.getId());
	}
	
	public List<Card> findByAssignee(User assignee)
	{
		return cardByAssignee.get(assignee.getId());
	}
	
}
