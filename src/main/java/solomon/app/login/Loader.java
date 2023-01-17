package main.java.solomon.app.login;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import main.java.solomon.app.domain.Card;
import main.java.solomon.app.domain.Column;
import main.java.solomon.app.domain.User;
import main.java.solomon.repository.mysql.CardMysqlRepository;
import main.java.solomon.repository.mysql.ColumnMysqlRepository;

public class Loader 
{
	static int a[] = {0,1,1,1,1,1,1,1};
	static Gson gson = new Gson(); // Or use new GsonBuilder().create();
	static List<Card> cards = new ArrayList<>();
	static CardMysqlRepository cardMysqlRepository = new CardMysqlRepository();
	static Card target = new Card("Sine",new User("SOmeEmail"),new Column("SomeCOl"));
	static String json = gson.toJson(target); // serializes target to JSON
	static Card target2 = gson.fromJson(json, Card.class); // deserializes json into target2
	
	
	public static void main(String[] args)
	{
		
			Column column = new ColumnMysqlRepository().findByName("TODO");
			System.out.println(column.getName());
			cards = cardMysqlRepository.findByColumn(column);
			String Cards = gson.toJson(cards);
			try { 
				FileWriter fileWriter = new FileWriter("./src/main/webapp/cards.json");
				fileWriter.write(Cards);
				fileWriter.close();
				
			}
			catch (IOException e){
				e.printStackTrace();
			}
			System.out.println(Cards);
	}
}