package Gossip_exercises.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//ch5：重构前
//@WebServlet(name = "Gossip_exercises.controller.Login",urlPatterns = "/login.do")
//重构后：使用初始参数
@WebServlet(
        urlPatterns={"/login.do"},
        initParams={
                @WebInitParam(name = "SUCCESS_VIEW", value = "member.view"),
                @WebInitParam(name = "ERROR_VIEW", value = "index.html")
        }
)

public class Login extends HttpServlet {
    private final String USERS = "C:\\Users\\54234\\Documents\\GitHub\\NEU_JavaWebProjet\\users";//未填入对应配置;
    private final String SUCCESS_VIEW = "member.view";
    private final String ERROR_VIEW = "index.html";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //-----------------ch4:会话管理-----------------
        String page = ERROR_VIEW;
        if(checkLogin(username, password)) {
            request.getSession().setAttribute("login", username);//登录信息正确时，设定login属性，值为用户名;
            //setAttribute()设定会话期间保留的信息
            page = SUCCESS_VIEW;
        }
        response.sendRedirect(page);
        /*
        -----------------ch3旧代码-----------------
        if(checkLogin(username,password)){
            //正确则转发到成功页面
            request.getRequestDispatcher(SUCCESS_VIEW).forward(request,response);
        }else{
            //错误则重定向到首页
            response.sendRedirect(ERROR_VIEW);
        }
        */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

    }
    /* ---------------------ch5：Servlet进阶API 重构，该功能将由UserService提供----------------------
    private boolean checkLogin(String username, String password)
            throws IOException {
        if(username != null && password != null) {
            //读取用户文件夹中的profile
            for (String file : new File(USERS).list()) {
                if (file.equals(username)) {
                    BufferedReader reader = new BufferedReader(
                            new FileReader(USERS + "/" + file + "/profile"));
                    String passwd = reader.readLine().split("\t")[1];
                    if(passwd.equals(password)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    */
}
