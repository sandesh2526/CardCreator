package main.java.solomon.app.login;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.solomon.repository.mysql.GetSetPassword;
import main.java.solomon.repository.mysql.UserMysqlRepository;

@WebServlet("/LoginProcess")
public class LoginProcess extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private UserMysqlRepository userRepo = new UserMysqlRepository();
	private GetSetPassword gsp = new GetSetPassword(); 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		System.out.println("Supporting this method and okay to move");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();  
		String emailString = req.getParameter("email");
		String passkey = req.getParameter("passkey");
		System.out.println("Email: ["+emailString+"] Password provided: ["+passkey+"] Expected: ["+gsp.GetPassword(emailString).equals(passkey)+"]");
		RequestDispatcher rd = req.getRequestDispatcher("./Loader");
		if(userRepo.findByEmail(emailString) != null && gsp.GetPassword(emailString).equals(passkey))
		{
			session.setAttribute("email", emailString);
			resp.sendRedirect("Loader");
		}
		else {
			out.print("<h3>Incorrect Credentials Please try again1!</h3>");			
		}

	}
}
