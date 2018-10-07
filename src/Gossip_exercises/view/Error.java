package Gossip_exercises.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Gossip_exercises.view.Error",urlPatterns = "/error.view")
public class Error extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta content ='text/html ;charset =UTF-8'"+"http-equiv='content-type'>");
        out.println("<title>注册失败</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>注册失败</h1>");
        out.println("<ul style = 'color:rgb(255,0,0);'>");


        //取得请求属性errors
        List<String> errors = (List<String>)request.getAttribute("errors");
        //显示错误信息
        for(String error: errors){
            out.println("<li>"+error+"</li");
        }

        out.println("</ul>");
        out.println("<a href = 'register.html'>返回注册页面</a>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

    }
}
