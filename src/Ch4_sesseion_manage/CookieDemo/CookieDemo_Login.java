package Ch4_sesseion_manage.CookieDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CookieDemo_Login",urlPatterns = "/CookieDemo_login.do")
public class CookieDemo_Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        String passwd = request.getParameter("passwd");
        if("caterpillar".equals(user) && "123456".equals(passwd)) {
            String login = request.getParameter("login");
            /*
             * 实作步骤1、2
             * */
            if("auto".equals(login)){//login值为auto表示自动登录
                //创建一周有效cookie，添加入响应
                Cookie cookie = new Cookie("user","caterpillar");
                cookie.setMaxAge(7*24*60*60);
                response.addCookie(cookie);
            }
            request.setAttribute("user", user);
            request.getRequestDispatcher("CookieDemo_user.view")
                    .forward(request, response);
        }
        else {
            response.sendRedirect("CookieDemo_login.html");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

    }
}
