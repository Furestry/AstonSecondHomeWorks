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
import ru.furestry.astonhomework.entity.Role;
import ru.furestry.astonhomework.service.RoleService;

import java.io.IOException;

@WebServlet("/roles")
public class RolesServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ServerConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);

        context.setVariable("rolesList", RoleService.getInstance().findAll());

        templateEngine.process("roles", context, response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Role role = new Role();
        String name = request.getParameter("name");

        role.setName(name);

        RoleService.getInstance().save(role);

        response.sendRedirect("/roles");
    }

    @WebServlet("/roles/delete")
    public static class RolesDeleteServlet extends HttpServlet {
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            RoleService.getInstance().delete(Long.parseLong(
                    request.getParameter("roleId")
            ));

            response.sendRedirect("/roles");
        }
    }

    @WebServlet("/roles/edit")
    public static class RolesEditServlet extends HttpServlet {
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            Role role = new Role();
            String name = request.getParameter("name");

            role.setName(name);

            RoleService.getInstance().update(role);

            response.sendRedirect("/roles");
        }
    }
}
