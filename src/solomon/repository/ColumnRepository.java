package solomon.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.jar.Attributes.Name;

import solomon.app.domain.Column;

@SuppressWarnings("unused")
public class ColumnRepository
{
	private final Map<String,Column> columnByName = new HashMap<>();
	private final Map<String, Column> columnByID = new HashMap<>();
	
	public void save(Column column)
	{
		if (column.getId() != null)
		{
			throw new IllegalArgumentException("This column already exist "+column.getId());
		}
		column.setId(UUID.randomUUID().toString());
		columnByID.put(column.getId(),column);
		columnByName.put(column.getName(), column);
	}
	
	public Column findByName(String name)
	{
		if(columnByName.get(name) != null)
		{
			return columnByName.get(name);
		}
		else
		{
			return null;
		}
	}
		 
	public Collection<Column> findAllOrderedByPosition()
	{
		return  columnByName.values();
	}
}
