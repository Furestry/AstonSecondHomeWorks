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
import ru.furestry.astonhomework.entity.Department;
import ru.furestry.astonhomework.entity.Role;
import ru.furestry.astonhomework.entity.User;
import ru.furestry.astonhomework.service.DepartmentService;
import ru.furestry.astonhomework.service.RoleService;
import ru.furestry.astonhomework.service.UserService;
import ru.furestry.astonhomework.util.Utils;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ServerConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);

        context.setVariable("usersList", UserService.getInstance().findAll());
        context.setVariable("rolesList", RoleService.getInstance().findAll());
        context.setVariable("departmentsList", DepartmentService.getInstance().findAll());

        templateEngine.process("users", context, response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        String username = request.getParameter("username");
        String departmentId = Utils.getParameterValue("department", request.getParameterMap());
        List<Role> roles = Utils.getParameterValues("role", request.getParameterMap())
                .parallelStream()
                .map(r -> RoleService.getInstance()
                        .findById(Long.parseLong(r))
                        .orElse(null)
                )
                .toList();
        Department department = DepartmentService.getInstance()
                .findById(Long.parseLong(departmentId))
                .orElse(null);

        user.setUsername(username);
        user.setRoles(roles);
        user.setDepartment(department);

        UserService.getInstance().save(user);

        response.sendRedirect("/users");
    }

    @WebServlet("/users/delete")
    public static class UsersDeleteServlet extends HttpServlet {
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            UserService.getInstance().delete(Long.parseLong(
                    request.getParameter("userId")
            ));

            response.sendRedirect("/users");
        }
    }

    @WebServlet("/users/edit")
    public static class UsersEditServlet extends HttpServlet {
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            User user = new User();
            String username = request.getParameter("username");
            String userId = request.getParameter("userId");
            List<Role> roles = Utils.getParameterValues("role", request.getParameterMap())
                    .parallelStream()
                    .map(r -> RoleService.getInstance()
                            .findById(Long.parseLong(r)).orElse(null)
                    )
                    .toList();
            String departmentId = Utils.getParameterValue("department", request.getParameterMap());

            user.setId(Long.parseLong(userId));
            user.setUsername(username);
            user.setRoles(roles);
            user.setDepartment(DepartmentService.getInstance()
                    .findById(Long.parseLong(departmentId))
                    .orElse(null)
            );

            UserService.getInstance().update(user);

            response.sendRedirect("/users");
        }
    }
}
