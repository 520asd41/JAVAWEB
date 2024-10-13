package org.example.Filter001;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    // 排除列表，包含不需要登录就能访问的路径
    private static final List<String> excludedPaths = List.of("/login", "/register", "/public","/LoginServlet");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // 获取请求的路径
        String requestPath = httpRequest.getServletPath();

        // 检查当前请求路径是否在排除列表中
        if (excludedPaths.contains(requestPath)) {
            // 如果是，则允许请求通过
            chain.doFilter(request, response);
        } else {
            // 如果不是，检查用户是否已登录
            if (session != null) {
                // 用户已登录，允许请求继续
                chain.doFilter(request, response);
            } else {
                // 用户未登录，重定向到登录页面
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }
        }
    }

}

