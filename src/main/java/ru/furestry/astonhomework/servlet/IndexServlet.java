package ru.furestry.astonhomework.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.furestry.astonhomework.config.ServerConfiguration;

import java.io.IOException;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ServerConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);

        templateEngine.process("index", context, response.getWriter());
    }
}
