package org.example.servletrequestlistener001;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebListener
public class RequestLoggerListener implements ServletRequestListener {

    // 内部类 RequestLog，用于存储和计算请求的详细信息。
    public static class RequestLog {
        private long start;
        private long duration;
        private String IPAddress;
        private String requestURL;
        private String requestMethod;
        private String requestParams;
        private String UserAgent;

        // 初始化请求的开始时间和其他相关信息。
        public RequestLog(HttpServletRequest req) {
            start = System.currentTimeMillis();
            IPAddress = req.getRemoteAddr();
            requestURL = req.getRequestURI();
            requestMethod = req.getMethod();
            requestParams = req.getQueryString();
            UserAgent = req.getHeader("User-Agent");
        }

        //方法计算请求的持续时间。
        public void getDuration() {
            long end = System.currentTimeMillis();
            duration = end - start;
        }

        //方法返回一个格式化的字符串，包含请求的所有详细信息。
        @Override
        public String toString() {
            getDuration();
            Date now = new Date(start);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = sdf.format(now);
            return "[" + startTime + "]:" +
                    "  IPAddress='" + IPAddress + '\'' +
                    ", requestURL='" + requestURL + '\'' +
                    ", requestMethod='" + requestMethod + '\'' +
                    ", requestParams='" + requestParams + '\'' +
                    ", UserAgent='" + UserAgent + '\'' +
                    ", duration=" + duration + "ms";
        }
    }

    //当请求结束时调用，打印请求时间，客户端 IP 地址，请求方法，请求 URI，查询字符串，User-Agent，请求处理时间
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
        RequestLog log = (RequestLog) req.getAttribute("log");
        System.out.println(log);
    }

    //当请求开始时调用。它创建一个新的 RequestLog 对象并将其设置为请求的一个属性，以便在请求结束时使用。
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
        RequestLog log = new RequestLog(req);
        req.setAttribute("log", log);
    }
}
