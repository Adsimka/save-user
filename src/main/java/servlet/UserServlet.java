package servlet;

import dao.UserDao;
import model.Gender;
import model.User;
import service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@WebServlet(name = "user", urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    private UserService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        service = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(String.format("Method: doGet"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String requestURI = req.getRequestURI();

        service.saveUserDB(req);

        String params = formatParams(req);
        resp.getWriter().write(String.format("Method doPost\nURI: %s \nParams: %s", requestURI, params));
    }

    private static String formatParams(HttpServletRequest req) {
        return req.getParameterMap()
                .entrySet()
                .stream()
                .map(entry -> {
                    String param = String.join(" and ", entry.getValue());
                    return entry.getKey() + " - " + param;
                }).collect(Collectors.joining("\n"));
    }
}
