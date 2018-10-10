package Gossip_exercises.controller;

import Gossip_exercises.model.UserService;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Gossip_exercises.controller.Delete",urlPatterns = "/delete.do")
public class Delete extends HttpServlet {
    private final String USERS = "C:\\Users\\54234\\Documents\\GitHub\\NEU_JavaWebProjet\\users";
    private final String LOGIN_VIEW = "index.html";
    private final String SUCCESS_VIEW = "member.view";
    
	protected void doGet(HttpServletRequest request, 
	                     HttpServletResponse response) 
	                         throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
	    //会话对象无login属性，重定向到登录页
        if(request.getSession().getAttribute("login") == null) {
            response.sendRedirect(LOGIN_VIEW);
            return;
        }

        //删除用户文件夹对应文件
        String username = (String) request.getSession().getAttribute("login");
        String message = request.getParameter("message");

        /*ch5：重构
        File file = new File(USERS + "/" + username + "/" + message + ".txt");
        if(file.exists()) {
            file.delete();
        }
        */
        //重构后使用UserService提供方法
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        userService.deleteMessage(username, message);

        response.sendRedirect(SUCCESS_VIEW);//成功后重定向到会员页
	}
}
