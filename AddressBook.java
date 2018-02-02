package softeng;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

/**
 * Servlet implementation class AddressBook
 */
@WebServlet("/AddressBook")
public class AddressBook extends HttpServlet {
	private static final long 	serialVersionUID 	= 1L;
    static String				url					= "jdbc:mysql://mh4830.ddns.net:3306/myDB";
    static String				user				= "remote";
    static String				password			= "password";
    static Connection			connection			= null;       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddressBook() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
		connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		
        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>Address Input</title>");
        out.println("</head><body>");
        
        out.println("<form method=\"get\" action=\"echo\">"
        		+ "<fieldset>"
        		+ "<legend>New Address</legend>"
        		+ "Name: <input type=\"text\" name=\"username\" /><br />"
        		+ "Email: <input type=\"text\" name=\"email\" /><br />"
        		+ "Street: <input type=\"text\" name=\"street\" /><br>"
        		+ "City: <input type=\"text\" name =\"city\" /><br>"
        		+ "State(2 characters max): <input type=\"text\" name=\"state\" /><br>"
        		+ "Zip Code(numbers only): <input type=\"text\" name=\"zip\" /><br>"
        		+ "Phone: <input type=\"text\" name=\"phone\" /><br />"
        		+ "</fieldset>"
        		+ "<input type = \"submit\" value=\"SEND\" />"
        		+ "<input type = \"reset\" value=\"CLEAR\" />"
        		+ "</form>");
        
        out.println("<form method=\"get\" action=\"delete\">"
        		+ "<fieldset>"
        		+ "<legend>Delete Address</legend>"
        		+ "Name: <input type=\"text\" name=\"username\" /><br />"
        		+ "</fieldset>"
        		+ "<input type = \"submit\" value = \"DELETE\" />"
        		+ "<input type = \"reset\" value = \"CLEAR\" />"
        		+ "</form>");
        
        try {
        	String selectSQL = "SELECT * FROM myTable";
        	//String theUserName = "user%";
        	PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        	//preparedStatement.setString(1,  theUserName);
        	ResultSet rs = preparedStatement.executeQuery();
        	while(rs.next()) {
            	out.println("-------------------------------------------------------------------------<br>");
        		String id = rs.getString("ID");
        		String username = rs.getString("MYUSER");
        		String email = rs.getString("EMAIL");
        		String street = rs.getString("STREET");
        		String city = rs.getString("CITY");
        		String state = rs.getString("STATE");
        		String zipcode = Integer.toString(rs.getInt("ZIPCODE"));
        		String phone = rs.getString("PHONE");
        		out.println("<div>");
        		out.println("NAME: " + username + "<br>");
        		out.println("EMAIL: " + email + "<br>");
        		out.println("STREET: " + street + "<br>");
        		out.println("CITY: " + city + "<br>");
        		out.println("State: " + state + "<br>");
        		out.println("ZIP: " + zipcode + "<br>");
        		out.println("PHONE: " + phone + "<br>");
        		out.println("</div>");
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        
        out.println("</body>"
        		+ 	"</html>");
		out.close();
		/*
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().println("--------------------- MySQL JDBC Connection Testing ------------------<br>");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
		response.getWriter().println("MySQL JDBC Driver Registered!<br>");
		connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			response.getWriter().println("You made it, take control of your database now!<br>");;
		} else {
			System.out.println("Failed to make connection!");
		}
		try {
			String selectSQL = "SELECT * FROM myTable WHERE MYUSER LIKE ?";
			String theUserName = "user%";
			response.getWriter().println(selectSQL + "<br>");
			response.getWriter().println("------------------------------------------<br>");
			PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1,  theUserName);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String id = rs.getString("ID");
				String username = rs.getString("MYUSER");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				response.getWriter().append("USER ID: " + id + ", ");
				response.getWriter().append("USER NAME: " + username + ", ");
				response.getWriter().append("USER EMAIL: " + email + ", ");
				response.getWriter().append("USER PHONE: " + phone + "<br>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} */
	} //end doGet

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
