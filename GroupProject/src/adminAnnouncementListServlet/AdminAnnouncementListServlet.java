package adminAnnouncementListServlet;

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

import administration.Announcement;
import database.DatabaseConnection;

/**
 * Servlet implementation class AdminAnnouncementListServlet
 */
@WebServlet("/AdminAnnouncementListServlet")
public class AdminAnnouncementListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAnnouncementListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		
		DatabaseConnection connection = new DatabaseConnection();
		ResultSet resultSet = connection.executeQuery("SELECT * FROM Announcements;");
		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				announcements.add(new Announcement(resultSet.getInt("id"),
						resultSet.getString("author"),
						resultSet.getString("header"),
						resultSet.getString("body"),
						resultSet.getString("createdAt")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection.close();
		
		request.setAttribute("announcements", announcements);
		RequestDispatcher dispatch = request.getRequestDispatcher("admin-announcement-list.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
