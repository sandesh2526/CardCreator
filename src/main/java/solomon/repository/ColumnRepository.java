package main.java.solomon.repository;

import java.util.Collection;

import main.java.solomon.app.domain.Column;

public interface ColumnRepository
{
	public void save(Column column);
	public Column findByName(String name);
	public Collection<Column> findAllOrderedByPosition();
}
