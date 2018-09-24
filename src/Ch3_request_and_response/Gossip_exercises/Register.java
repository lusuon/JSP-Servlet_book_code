package Ch3_request_and_response.Gossip_exercises;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Ch3_request_and_response.Gossip_exercises.Register",urlPatterns = "/register.do")
public class Register extends HttpServlet {

    private final String USERS = "C:\\Users\\54234\\Documents\\GitHub\\NEU_JavaWebProjet\\users";//未填入对应配置;
    private final String SUCCESS_VIEW = "success.view";
    private final String ERROR_VIEW = "error.view";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // 获取请求参数
        response.setContentType("text/html;charset=utf-8");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPasswd = request.getParameter("confirmedPasswd");

        //验证请求参数
        ArrayList<String> errors =new ArrayList<String>();

        if (isInvalidEmail(email)){
            errors.add("邮件地址未填写/格式不正确");
        }
        if (isInvalidUsername(username)){
            errors.add("用户名未填写/已存在");
        }
        if (isInvalidPassword(password,confirmPasswd)){
            errors.add("密码格式不正确/两次输入不一致");
            errors.add("pw:"+password);
            errors.add("confirm:"+confirmPasswd);
        }

        String resultPage = ERROR_VIEW;
        if (!errors.isEmpty()){
            request.setAttribute("errors",errors);
            //窗体验证出错，设置收集错误的List为请求属性
        }else{
            resultPage =SUCCESS_VIEW;
            createUserData(email,username,password);
            //创建用户资料
        }
        request.getRequestDispatcher(resultPage).forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

    }

    private boolean isInvalidEmail(String email){
        return email ==null||!email.matches("^[a-z0-9-]+([.]"+"[_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$");
    }

    private boolean isInvalidUsername(String username){
        //检查用户文件夹是否创建确认用户注册与否
        for(String file : new File(USERS).list()){
            if (file.equals(username)){
                return true;
            }
        }
        return false;
    }

    private boolean isInvalidPassword(String password,String confirmedPasswd){
        return password ==null||
                password.length()<6||
                password.length()>16||
                !password.equals(confirmedPasswd);
    }

    private void createUserData(String email,String username,String password) throws IOException{
        //创建用户文件夹，在profile中储存邮件密码
        File userhome = new File(USERS +"/"+username);
        userhome.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userhome+"/profile"));
        writer.write(email+"\t"+password);
        writer.close();
    }
}
