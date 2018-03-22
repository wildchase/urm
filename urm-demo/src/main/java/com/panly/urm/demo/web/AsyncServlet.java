package com.panly.urm.demo.web;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/async"}, asyncSupported = true)
public class AsyncServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;

    private final static Logger logger = LoggerFactory.getLogger(AsyncServlet.class);
    
    @Override
    public void doGet( HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
    	logger.info("doget");
        // 开启Tomcat异步Servlet支持
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

        final AsyncContext ctx = req.startAsync();  // 启动异步处理的上下文
        // ctx.setTimeout(30000);
        ctx.start(new Runnable() {
            @Override
            public void run() {
                // 在此处添加异步处理的代码
            	HttpServletResponse respx =  (HttpServletResponse) ctx.getResponse();
            	try {
					respx.getWriter().write("123");
				} catch (IOException e) {
					e.printStackTrace();
				}
            	logger.info("处理");
                ctx.complete();
            }
        });
    }
}