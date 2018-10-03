package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    private FilterConfig config;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        HttpSession session = request.getSession();

        String noLoginPaths = config.getInitParameter("noLoginPaths");

        String charset = config.getInitParameter("charset");
        if(charset==null){
            charset = "UTF-8";//如果没有指定charset的话，将charset设置为UTF-8
        }
        request.setCharacterEncoding(charset);

        if(noLoginPaths!=null){
            String[] strArray = noLoginPaths.split(";");
            for (int i = 0; i < strArray.length; i++) {

                if(strArray[i]==null || "".equals(strArray[i]))continue;

                if(request.getRequestURI().indexOf(strArray[i])!=-1 ){
                    arg2.doFilter(arg0, arg1);
                    return;
                }
            }
        }
        if(session.getAttribute("username")!=null){
            arg2.doFilter(arg0, arg1);//已经存在session对象了，允许通过过滤器
        }else{
            response.sendRedirect("login.jsp");//防止任何人通过直接输入相对应的登录成功地网址都直接访问这个页面。除非是本人而且浏览器没关闭过
        }

    }
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        config = arg0;//怎么得到这个初始化参数呢？参数指的是<param-name>noLoginPaths</param-name>
            //<param-value>login.jsp;fail.jsp;LoginServlet</param-value>
    }
    /*1.声明一个FilterConfig对象config
    * 2.在init()中初始化这个对象，把初始化参数arg0赋给这个对象(过滤的生命周期
    * 3.接收这个对象String  noLoginPaths= config.Parameter("noLoginPaths" );
    * 4.对这个对象进行相关处理，是她复合在规则以便接下来的处理*/
}
/*FilterConfig用来获取部署描述符文件（web.xml）中分配的过滤器初始化参数。*/

