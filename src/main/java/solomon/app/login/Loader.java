package main.java.solomon.app.login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.solomon.app.domain.Card;
import main.java.solomon.repository.mysql.CardMysqlRepository;

@WebServlet("/Loader")
public class Loader extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	static Gson gson = new Gson(); // Or use new GsonBuilder().create();
	static List<Card> cards = new ArrayList<>();
	static CardMysqlRepository cardMysqlRepository = new CardMysqlRepository();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		//Deleting the Old JSON
        File file = new File( "C:\\Users\\sande\\Downloads\\CardCreator\\src\\main\\webapp\\cards.json");
        if(file.delete())
        {
        	System.out.println("We have Deleted the cards.json File");
        }
        
		HttpSession session=req.getSession(false);
		String email = (String)session.getAttribute("email");
		System.out.println(email);
		cards = cardMysqlRepository.findByEmail(email);
		
		if(cards != null) {
			String Cards = gson.toJson(cards);
			try { 
				System.out.println("Ok");
				// Specify the Directory file location where you will be storing your location
				FileWriter fileWriter = new FileWriter("C:\\Users\\sande\\Downloads\\CardCreator\\src\\main\\webapp\\cards.json");
				fileWriter.write(Cards);
				fileWriter.close();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resp.sendRedirect("homepage.html");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session=req.getSession(false);
		String email = (String)session.getAttribute("email");
		cards = cardMysqlRepository.findByEmail(email);
		if (!cards.equals(null)) {
			//System.out.println("Cards Exist"+cards.get(1));
			String Cards = gson.toJson(cards);
			try { 
				System.out.println("Okay1");
				FileWriter fileWriter = new FileWriter("C:\\Users\\sande\\Downloads\\CardCreator\\src\\main\\webapp\\cards.json");
				fileWriter.write(Cards);
				fileWriter.close();	
			}
			catch (IOException e){
				e.printStackTrace();
			}
			resp.sendRedirect("homepage.html");
			System.out.println(Cards);
		}
		System.out.println("[Loder] [doPost] The Email Stored is: ["+email+"]");
	}

	/*public static void main(String[] args)
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
	}*/
}