package Gossip_exercises.controller;

import Gossip_exercises.model.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//ch5：重构前
//@WebServlet(name = "Gossip_exercises.controller.Register",urlPatterns = "/register.do")
//重构后：使用初始参数
@WebServlet(
        name = "Gossip_exercises.controller.Register",
        urlPatterns = "/register.do",
        initParams = {
                @WebInitParam(name = "SUCCESS_VIEW",value = "success.view"),
                @WebInitParam(name = "SUCCESS_VIEW",value = "success.view")
        }
        )

public class Register extends HttpServlet {
    /*ch5：重构前
    private final String USERS = "C:\\Users\\54234\\Documents\\GitHub\\NEU_JavaWebProjet\\users";//未填入对应配置;
    private final String SUCCESS_VIEW = "success.view";
    private final String ERROR_VIEW = "error.view";
    */

    //重构后:已在标注中声明，使用init()加载，从config获取
    private String SUCCESS_VIEW ;
    private String ERROR_VIEW ;
    @Override
    public void init() throws ServletException {
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //获取userService对象
        UserService userService = (UserService) getServletContext().getAttribute("userService");

        // 获取请求参数
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPasswd = request.getParameter("confirmedPasswd");

        //验证请求参数
        ArrayList<String> errors =new ArrayList<String>();

        if (isInvalidEmail(email)){
            errors.add("邮件地址未填写/格式不正确");
        }
        if (userService.isInvalidUsername(username)){
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
            resultPage =SUCCESS_VIEW;//可见，默认转发的页面为Error页面，完全符合格式才转发到正确页
            userService.createUserData(email,username,password);//ch5:重构，使用userService提供的方法
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

    private boolean isInvalidPassword(String password,String confirmedPasswd){
        return password ==null||
                password.length()<6||
                password.length()>16||
                !password.equals(confirmedPasswd);
    }

    /* ---------------------ch5：Servlet进阶API 重构，该功能将由UserService提供----------------------

    private boolean isInvalidUsername(String username){
        //检查用户文件夹是否创建确认用户注册与否
        for(String file : new File(USERS).list()){
            if (file.equals(username)){
                return true;
            }
        }
        return false;
    }

    private void createUserData(String email,String username,String password) throws IOException{
        //创建用户文件夹，在profile中储存邮件密码
        File userhome = new File(USERS +"/"+username);
        userhome.mkdir();
        BufferedWriter writer = new BufferedWriter(new FileWriter(userhome+"/profile"));
        writer.write(email+"\t"+password);
        writer.close();
    }
    */
}
