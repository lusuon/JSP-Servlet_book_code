package Ch3_request_and_response.Gossip_exercises;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@WebServlet(name = "Ch3_request_and_response.Gossip_exercises.Login",urlPatterns = "/login.do")
public class Login extends HttpServlet {
    private final String USERS = "C:\\Users\\54234\\Documents\\GitHub\\NEU_JavaWebProjet\\users";//未填入对应配置;
    private final String SUCCESS_VIEW = "member.view";
    private final String ERROR_VIEW = "index.view";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(checkLogin(username,password)){
            //正确则转发到成功页面
            request.getRequestDispatcher(SUCCESS_VIEW).forward(request,response);
        }else{
            //错误则返回首页
            response.sendRedirect(ERROR_VIEW);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

    }

    private boolean checkLogin(String username, String password)
            throws IOException {
        if(username != null && password != null) {
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
}
