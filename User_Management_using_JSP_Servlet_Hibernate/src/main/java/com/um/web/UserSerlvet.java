package com.um.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.um.dao.UserDao;
import com.um.model.User;

/**
 * Servlet implementation class UserSerlvet
 */
@WebServlet("/")
public class UserSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;
	@Override
	public void init() throws ServletException {
		userDao=new UserDao();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getServletPath();
		System.out.println(action);
		try {
			switch(action) {
			case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                insertUser(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateUser(request, response);
                break;
            default:
                listUser(request, response);
                break;
        }
    } catch (SQLException ex) {
        throw new ServletException(ex);
    }
}

private void listUser(HttpServletRequest request, HttpServletResponse response)
throws SQLException, IOException, ServletException {
    List<User> listUser = userDao.getAllUser();
    request.setAttribute("listUser", listUser);
    RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
    dispatcher.forward(request, response);
}

private void showNewForm(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
	System.out.println("Hi new!");
    RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
    dispatcher.forward(request, response);
}

private void showEditForm(HttpServletRequest request, HttpServletResponse response)
throws SQLException, ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    User existingUser = userDao.getUser(id);
    RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
    request.setAttribute("user", existingUser);
    dispatcher.forward(request, response);

}

private void insertUser(HttpServletRequest request, HttpServletResponse response)
throws SQLException, IOException {
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String country = request.getParameter("country");
    User newUser = new User(name, email, country);
    userDao.saveUser(newUser);
    response.sendRedirect("list");
}

private void updateUser(HttpServletRequest request, HttpServletResponse response)
throws SQLException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String country = request.getParameter("country");

    User user = new User(id, name, email, country);
    userDao.updateUser(user);
    response.sendRedirect("list");
}

private void deleteUser(HttpServletRequest request, HttpServletResponse response)
throws SQLException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    userDao.deleteUser(id);
    response.sendRedirect("list");
}
}