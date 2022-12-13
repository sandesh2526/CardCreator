package main.java.solomon.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.jar.Attributes.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.solomon.app.domain.Column;
import main.java.solomon.app.domain.RuntimeConsole;

@SuppressWarnings("unused")
public class ColumnInMemoryRepository implements ColumnRepository
{
	private final Map<String,Column> columnByName = new HashMap<>();
	private final Map<String, Column> columnByID = new HashMap<>();
	private static final Logger LOG = LoggerFactory.getLogger(ColumnInMemoryRepository.class);
	
	public void save(Column column)
	{
		if (column.getId() != null)
		{
			throw new IllegalArgumentException("This column already exist "+column.getId());
		}
		column.setId(UUID.randomUUID().toString());
		LOG.info("Added the Column {} to database",column);
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
