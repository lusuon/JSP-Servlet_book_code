package Gossip_exercises.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Gossip_exercises.controller.Logout",urlPatterns ="/logout.do")
public class Logout extends HttpServlet {
    private final String LOGIN_VIEW = "index.html";
	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) 
	                      throws ServletException, IOException {
        if(request.getSession().getAttribute("login") != null) {
            request.getSession().invalidate(); //令session对象失效实现注销
        }
        response.sendRedirect(LOGIN_VIEW);
	}
}
