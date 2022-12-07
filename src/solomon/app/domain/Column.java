package solomon.app.domain;

public class Column
{
	private String name;

	
	public Column(String name)
	{
		this.name = name;
	}
	
	public String getName() 
	{
		return this.name;
	}
	@Override
	public String toString()
	{
		return this.name;
	}
}
