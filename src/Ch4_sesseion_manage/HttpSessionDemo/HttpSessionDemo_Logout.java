package Ch4_sesseion_manage.HttpSessionDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//线程不安全，注意属性设定共享存取问题
@WebServlet(name = "HttpSessionDemo_Logout",urlPatterns = "/HttpSessionDemo_logout.view")
public class HttpSessionDemo_Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("login");
        session.invalidate();//使Session失效

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01" +
                " Transitional//EN'>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>登出</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>使用者 " + user + " 已登出</h1>");
        out.println("</body>");
        out.println("</html>");
        out.close();

    }
}
