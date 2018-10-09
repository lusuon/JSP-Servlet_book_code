package Gossip_exercises.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import Gossip_exercises.model.UserService;

/**
 * UserService是多个Servlet都会用到的对象，而它本身不具备状态，考虑将其作为整个程序都会使用的服务对象，存放于ServletContext属性中。
 * 在初始化是，创建UserService对象作为上下文的属性，通过contextInitialized实现
 */

@WebListener
public class GossipListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String USERS = sce.getServletContext().getInitParameter("USERS");
        context.setAttribute("userService", new UserService(USERS));
    }

    public void contextDestroyed(ServletContextEvent sce) {}
}
