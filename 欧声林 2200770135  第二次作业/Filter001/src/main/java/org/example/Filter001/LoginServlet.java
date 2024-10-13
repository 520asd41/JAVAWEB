package org.example.Filter001;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private String account = "123456";
    private String password = "123456";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从请求参数中获取用户名和密码
        String account1 = req.getParameter("username");
        String password1 = req.getParameter("password");

        // 开始校验是否登录成功
        boolean isLogin = false;
        if (account1 != null && account1.equals(account) && password1 != null && password1.equals(password)) {
            isLogin = true;
            req.getSession().setAttribute("userLoggedIn", true); // 设置登录标记
            req.getSession().setAttribute("account", account1); // 设置用户账号属性
        }

        // 响应结果
        resp.setContentType("text/html;charset=UTF-8");
        if (isLogin) {
            // 登录成功，可以重定向到主页或者返回成功消息
            resp.sendRedirect(req.getContextPath() + "/main.html"); // 重定向到主页
        } else {
            // 登录失败，返回失败消息
            resp.getWriter().println("登录失败，账号或密码错误");
        }
    }
}
