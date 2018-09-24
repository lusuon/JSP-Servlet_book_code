package Ch4_sesseion_manage.HttpSessionDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HttpSessionDemo_User",urlPatterns ="/HttpSession_user.view")
public class HttpSessionDemo_User extends HttpServlet {
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        /*
         * 实作步骤1
         * */
        if(session.getAttribute("login")==null){
            response.sendRedirect("HttpSessionDemo_login.html");
        }else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01" +
                    " Transitional//EN'>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>欢迎 "
                    + session.getAttribute("login") + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>使用者 " + session.getAttribute("login")+ " 已登入</h1><br><br>");//获取用户名
            out.println("<a href='HttpSessionDemo_logout.view'>登出</a>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        processRequest(request,response);

    }
}
