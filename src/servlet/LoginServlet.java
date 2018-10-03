package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    public LoginServlet() {
        super();
    }

    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(username);

        if("admin".equals(username) && "admin".equals(password)){
            //检验通过
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect(request.getContextPath()+"/sucess.jsp");
        }else{
            //校验失败
            response.sendRedirect(request.getContextPath()+"/fail.jsp");
        }
    }

    public void init() throws ServletException {
        // Put your code here
    }
}
/*request.getContextPath()拿到的是你的web项目的根路径*/

/*Servlet是在服务器上运行的小程序,一个Servlet就是一个JAVA类,并且可以通过"请求-响应"编程模型来访问的这个
驻留在服务器内存里的Servlet程序.Servlet存在于服务器的内存中.先有Servlet再有JSP,JSP前身就是Servlet。*/
/*Tomocat容器等级
   Tomocat容器分为四个等级，Servlet的容器管理Context容器，一个Context对应一个Web工程。
   tomcat容器等级：tomcat->Container容器->Engine容器(引擎容器)->Host(主机)容器->Servlet->Context*/
