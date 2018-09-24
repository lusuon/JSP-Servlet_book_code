package Ch4_sesseion_manage.CookieDemo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CookieDemo_Index",urlPatterns = "/CookieDemo_index.do")
public class CookieDemo_Index extends HttpServlet {
    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 实作步骤1、2
         * */
        Cookie[] cookies = request.getCookies();//获取Cookie
        if(cookies!=null){
            for(Cookie cookie:cookies){
                String name = cookie.getName();
                String value = cookie.getValue();

                //若有符合Cookie名称、数值，允许自动登录
                if("user".equals(name) && "caterpillar".equals(value)){
                    request.setAttribute(name,value);
                    request.getRequestDispatcher("CookieDemo_user.view").forward(request,response);
                    return;
                }
            }
        }
        response.sendRedirect("CookieDemo_login.html");//无对应Cookie名称，数值，尚未允许自动登录，重定向到登录页
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        processRequest(request, response);

    }
}
