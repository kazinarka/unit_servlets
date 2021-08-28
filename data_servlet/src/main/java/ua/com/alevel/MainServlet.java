package ua.com.alevel;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "mainServlet", urlPatterns = {"/mainServlet"})
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = -8948379822734246956L;

    private static final Map<String, String> userInfo = new ConcurrentHashMap<>();

    @Override
    public void init() {
        System.out.println("Servlet " + getServletName() + " is initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String remoteHost = req.getRemoteHost();
        String header = req.getHeader("User-Agent");
        userInfo.put(remoteHost, header);

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        writer.println("<h1 align=center>List of ip addresses and user-agent");

        for (Map.Entry<String, String> entry : userInfo.entrySet()) {
            if (entry.getKey().equals(remoteHost)) {
                writer.println("<p align=center> <b>" + entry.getKey() + " " + entry.getValue() + "</b> </p>");
            } else {
                writer.println("<p align=center>" + entry.getKey() + " " + entry.getValue() + "</p>");
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("Servlet " + getServletName() + " is destroyed");
    }
}