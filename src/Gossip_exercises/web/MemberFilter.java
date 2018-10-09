package Gossip_exercises.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Filter拦截对以下页面的请求，检查是否已登录，确定是否进行下一步操作
@WebFilter(
    urlPatterns = { "/delete.do", "/logout.do", "/message.do", "/member.view" }, 
    initParams = { @WebInitParam(name = "LOGIN_VIEW", value = "index.html") })
public class MemberFilter implements Filter {
    private String LOGIN_VIEW;
    public void init(FilterConfig config) throws ServletException {
        this.LOGIN_VIEW = config.getInitParameter("LOGIN_VIEW");//设置登录页面
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request; 
        if(req.getSession().getAttribute("login") != null) {
            chain.doFilter(request, response);//只有登录后，才能调用doFilter()
        }
        else {//否则重定向到登录页面
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(LOGIN_VIEW);
        }
    }

    public void destroy() {
    }

}
