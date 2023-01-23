package main.java.solomon.app.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.java.solomon.app.domain.User;
import main.java.solomon.repository.mysql.GetSetPassword;
import main.java.solomon.repository.mysql.UserMysqlRepository;

@WebServlet("/SignUpProcess")
public class SignUpProcess extends HttpServlet
{
	private static final long serialVersionUID = 4645243168201768970L;
	private UserMysqlRepository userMysqlRepository = new UserMysqlRepository();
	private GetSetPassword gsp = new GetSetPassword();

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		RequestDispatcher rd = request.getRequestDispatcher("./Loader");
		System.out.println("Coming inside the DoPost");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String passkey = request.getParameter("passkey");
		String uid = UUID.randomUUID().toString();
		
		
		if((userMysqlRepository.findByEmail(email)) != null)
		{
			out.print("<h3>User already exist</h3>");
			System.out.println("Coming here!!");
		}
		else
		{
			
			HttpSession session=request.getSession();  
			User newUser = new User(email);
			System.out.println("Email: "+newUser.getEmail());
			newUser.setId(uid);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			userMysqlRepository.saveOrUpdate(newUser);
			System.out.println("okay! Have came  here! Agter");
			try{
				gsp.SetPassword(uid, passkey);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			session.setAttribute("email", email);
			System.out.println("COming here successfully");
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
			
}		
