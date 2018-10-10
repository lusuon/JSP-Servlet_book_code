package Gossip_exercises.view;

import Gossip_exercises.model.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.*;
import java.text.DateFormat;
import java.util.*;

/**
 * （1）通过URL重写实现注销
 * （2）新增信息失败转发至会员网页，将请求参数填回到输入字段
 * （3）（4）显示信息的内容、时间信息
 */

@WebServlet(name = "Gossip_exercises.view.Member",urlPatterns = "/member.view")
public class Member extends HttpServlet {
    private final String USERS = "C:\\Users\\54234\\Documents\\GitHub\\NEU_JavaWebProjet\\users";
    private final String LOGIN_VIEW = "index.html";

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("login") == null) {
            response.sendRedirect(LOGIN_VIEW);
            return;
        }

        String username = (String) request.getSession().getAttribute("login");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>");
        out.println("<html>");
        out.println("<head>");
        out.println("  <meta content='text/html; charset=UTF-8' http-equiv='content-type'>");
        out.println("<title>Gossip 微网志</title>");
        out.println("<link rel='stylesheet' href='css/member.css' type='text/css'>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='leftPanel'>");
        out.println("<img src='images/caterpillar.jpg' alt='Gossip 微网志' /><br><br>");

        /*  实作步骤1：用户注销链接 */
        out.println("<a href='logout.do?username="+username+"'>注销"+username+"</a>");



        out.println("</div>");
        out.println("<form method='post' action='message.do'>");
        out.println("分享新鲜事...<br>");


        /*  实作步骤2:判断是否回填信息 */
        String blabla = request.getParameter("blabla");
        if (blabla == null){
            blabla = "";
        }else{
            out.println("信息要小于140字<br>");
        }
        out.println("<textarea cols='60' rows='4' name='blabla'>"+blabla+"</textarea>");

        out.println("<br>");
        out.println("<button type='submit'>送出</button>");
        out.println("</form>");
        out.println("<table style='text-align: left; width: 510px; height: 88px;' border='0' cellpadding='2' cellspacing='2'>");
        out.println("<thead>");
        out.println("<tr><th><hr></th></tr>");
        out.println("</thead>");
        out.println("<tbody>");


        /*  实作步骤3：读取文件并显示信息 */
        //ch5:使用UserService提供方法
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        Map<Date,String> message = userService.readMessage(username);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL, Locale.CHINA);
        for (Date date:message.keySet()){
            out.println("<tr><td style=vertical-align:top;'>");
            out.println(username+"<br>");
            out.println(message.get(date)+"<br>");
            out.println(dateFormat.format(date));
            out.println("<a href='delete.do?message=" + date.getTime()+"'>删除</a>");
            out.println("<hr></td></tr>");


        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("<hr style='width: 100%; height: 1px;'>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    /* ---------------------ch5：Servlet进阶API 重构，该功能将由UserService提供----------------------

    //用于过滤.txt文件名
    private class TxtFilenameFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".txt");
        }
    }

    private TxtFilenameFilter filenameFilter = new TxtFilenameFilter();

    private class DateComparator implements Comparator<Date> {
        @Override
        public int compare(Date d1, Date d2) {
            return -d1.compareTo(d2);
        }
    }

    private DateComparator comparator = new DateComparator();

    //TreeMap排序，日期近者在上方
    private Map<Date, String> readMessage(String username) throws IOException {
        File border = new File(USERS + "/" + username);
        String[] txts = border.list( filenameFilter);

        Map<Date, String> messages = new TreeMap<Date, String>(comparator);
        for(String txt : txts) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(USERS + "/" + username + "/" + txt), "UTF-8"));
            String text = null;
            StringBuilder builder = new StringBuilder();
            while((text = reader.readLine()) != null) {
                builder.append(text);
            }
            Date date = new Date(Long.parseLong(txt.substring(0, txt.indexOf(".txt"))));
            messages.put(date, builder.toString());
            reader.close();
        }

        return messages;
    }
    */


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        processRequest(request, response);
        /*
        -----------------ch3-----------------
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>");
        out.println("<html>");
        out.println("<head>");
        out.println("  <meta content='text/html; charset=UTF-8' http-equiv='content-type'>");
        out.println("  <title>会员登入成功</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>会员 " + request.getParameter("username") + " 你好</h1>");
        out.println("</body>");
        out.println("</html>");

        out.close();
        */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        processRequest(request, response);
    }
}
