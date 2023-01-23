package main.java.solomon.app.login;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.solomon.app.domain.Card;
import main.java.solomon.app.domain.Column;
import main.java.solomon.app.domain.User;
import main.java.solomon.repository.CardRepository;
import main.java.solomon.repository.ColumnRepository;
import main.java.solomon.repository.UserRepository;
import main.java.solomon.repository.mysql.CardMysqlRepository;
import main.java.solomon.repository.mysql.ColumnMysqlRepository;
import main.java.solomon.repository.mysql.UserMysqlRepository;

@WebServlet("/AddCard")
public class AddCard extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		UserRepository userRepository = new UserMysqlRepository();
		ColumnRepository columnRepository = new ColumnMysqlRepository();
		CardRepository cardRepository = new CardMysqlRepository();
		HttpSession session=req.getSession(false);
		System.out.println("[AddCard] [doPost] [checkingColumnRepository] Email is:"+(String)session.getAttribute("email"));
		String creatorEmail = (String)session.getAttribute("email");
		//super.doPost(req, resp);
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String assignTo = req.getParameter("assignTo");
		String column = req.getParameter("column");
		
		if(columnRepository.findByName(column) == null)
		{
			System.out.println("[AddCard] [doPost] [checkingColumnRepository] Column is Empty");
			Column newColumn = new Column(column);
			System.out.println("[AddCard] [doPost] [checkingColumnRepository] ColumnName Is: "+newColumn.getName());
			columnRepository.save(newColumn);
		}
		if(userRepository.findByEmail(assignTo) == null)
		{
			System.out.println("The user in 'Assign To' field does not exixt!");
			resp.sendRedirect("homepage.html");
		}
		
		Column newColumn = columnRepository.findByName(column);
		User Creator = userRepository.findByEmail(creatorEmail);
		User assigningTo = userRepository.findByEmail(assignTo);
		
		Card newCard = new Card(title, Creator, newColumn);
		newCard.setDescription(description);
		newCard.setAssignee(assigningTo);
		cardRepository.save(newCard);
        File file = new File( "C:\\Users\\sande\\Downloads\\CardCreator\\src\\main\\webapp\\cards.json");
        if(file.delete())
        {
        	System.out.println("We have Deleted the File");
        }		
		resp.sendRedirect("Loader");
		System.out.println("[AddCard] [doPost] Title: "+title+"\n Description: "+description+"\n Assign To: "+assignTo+"\n Column: "+newColumn.getName());
	}

}
