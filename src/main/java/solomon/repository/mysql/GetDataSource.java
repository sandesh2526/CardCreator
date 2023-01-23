package main.java.solomon.repository.mysql;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum GetDataSource {
	INSTANCE;
	private DataSource dataSource;
	
	public DataSource dataSource()
	{
		if (dataSource == null) {
     		System.out.println("[GetDataSource] [dataSource()] Creating datasource");
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("root");
			dataSource.setPassword("password");
			dataSource.setUrl("jdbc:mysql://localhost:3306/card");
			this.dataSource = dataSource;
		}
		return dataSource;
	}
}
