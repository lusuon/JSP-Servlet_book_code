package Gossip_exercises.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//ch5：重构前
//@WebServlet(name = "Gossip_exercises.controller.Logout",urlPatterns ="/logout.do")

//重构后：使用初始参数
@WebServlet(
        urlPatterns={"/logout.do"},
        initParams={
                @WebInitParam(name = "LOGIN_VIEW", value = "index.html")
        }
)
public class Logout extends HttpServlet {
    /*ch5：重构前
    private final String LOGIN_VIEW = "index.html";
    */

    //重构后:已在标注中声明，使用init()加载，从config获取
    private String LOGIN_VIEW;
    @Override
    public void init() throws ServletException {
        LOGIN_VIEW = getServletConfig().getInitParameter("LOGIN_VIEW");
    }
	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) 
	                      throws ServletException, IOException {
        if(request.getSession().getAttribute("login") != null) {
            request.getSession().invalidate(); //令session对象失效实现注销
        }
        response.sendRedirect(LOGIN_VIEW);
	}
}
