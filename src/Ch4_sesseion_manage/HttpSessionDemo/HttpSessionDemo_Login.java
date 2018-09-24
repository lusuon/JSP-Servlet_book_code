package Ch4_sesseion_manage.HttpSessionDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HttpSessionDemo_Login",urlPatterns ="/HttpSession_login.do" )
public class HttpSessionDemo_Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String user = request.getParameter("user");
        String passwd = request.getParameter("passwd");
        if ("caterpillar".equals(user) && "123456".equals(passwd)) {
            request.getSession().setAttribute("login", user);//设定登录字符——识别用户是否登录，即为登陆令牌
            request.getRequestDispatcher("/HttpSession_user.view").forward(request,
                    response);
        } else {
            response.sendRedirect("HttpSessionDemo_login.html");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

    }
}
