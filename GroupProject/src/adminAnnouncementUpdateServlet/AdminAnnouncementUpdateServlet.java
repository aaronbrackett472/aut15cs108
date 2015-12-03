package adminAnnouncementUpdateServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import administration.Announcement;
import database.DatabaseConnection;
import utilities.Utilities;

/**
 * Servlet implementation class AdminAnnouncementUpdateServlet
 */
@WebServlet("/AdminAnnouncementUpdateServlet")
public class AdminAnnouncementUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAnnouncementUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseConnection connection = new DatabaseConnection();
		int id = Integer.parseInt(request.getParameter("id"));
		if (request.getParameter("update") != null) {
			Announcement.updateAnnouncement(id, request.getParameter("header"), request.getParameter("body"));
		} else {
			Announcement.deleteAnnouncement(id);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("AdminAnnouncementListServlet");
		dispatch.forward(request, response);
	}

}
