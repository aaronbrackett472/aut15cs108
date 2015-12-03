package adminUserListServlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import administration.User;
import database.DatabaseConnection;

/**
 * Servlet implementation class AdminUserListServlet
 */
@WebServlet("/AdminUserListServlet")
public class AdminUserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<User> users = new ArrayList<User>();
		
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM Accounts;");
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				users.add(new User(resultSet.getString("username"),
						resultSet.getBoolean("isAdmin"),
						resultSet.getBoolean("suspended"),
						resultSet.getString("suspensionEnd")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection.close();
		
		request.setAttribute("users", users);
		RequestDispatcher dispatch = request.getRequestDispatcher("admin-user-list.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("delete") != null) {
			User.deleteUser(request.getParameter("username"));
		} else if (request.getParameter("promote") != null) {
			User.promoteUser(request.getParameter("username"));
		} else if (request.getParameter("demote") != null) {
			User.demoteUser(request.getParameter("username"));
		} else if (request.getParameter("unsuspend") != null) {
			User.unsuspendUser(request.getParameter("username"));
		} else {
			int days = Integer.parseInt(request.getParameter("suspension"));
			User.suspendUser(request.getParameter("username"), days);
		}
		
		doGet(request, response);
	}

}
