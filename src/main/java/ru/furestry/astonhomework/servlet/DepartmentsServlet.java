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
import ru.furestry.astonhomework.service.DepartmentService;

import java.io.IOException;

@WebServlet("/departments")
public class DepartmentsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ServerConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);

        context.setVariable("departmentsList", DepartmentService.getInstance().findAll());

        templateEngine.process("departments", context, response.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Department department = new Department();
        String name = request.getParameter("name");

        department.setName(name);

        DepartmentService.getInstance().save(department);

        response.sendRedirect("/departments");
    }

    @WebServlet("/departments/delete")
    public static class DepartmentsDeleteServlet extends HttpServlet {
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            DepartmentService.getInstance().delete(Long.parseLong(
                    request.getParameter("departmentId")
            ));

            response.sendRedirect("/departments");
        }
    }

    @WebServlet("/departments/edit")
    public static class DepartmentsEditServlet extends HttpServlet {
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            Department department = new Department();
            String name = request.getParameter("name");

            department.setName(name);

            DepartmentService.getInstance().update(department);

            response.sendRedirect("/departments");
        }
    }
}
