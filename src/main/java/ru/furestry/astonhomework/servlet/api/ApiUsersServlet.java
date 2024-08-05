package ru.furestry.astonhomework.servlet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.furestry.astonhomework.entity.User;
import ru.furestry.astonhomework.service.DepartmentService;
import ru.furestry.astonhomework.service.RoleService;
import ru.furestry.astonhomework.service.UserService;

import java.io.IOException;

import static ru.furestry.astonhomework.servlet.api.ApiServlet.writeToResponse;

public class ApiUsersServlet {
    @WebServlet("/api/read/users")
    public static class ApiReadUsersServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String id = request.getParameter("id");

            if (id == null) {
                writeToResponse(
                        mapper.writeValueAsString(UserService.getInstance().findAll()),
                        response
                );
            } else {
                User user = UserService.getInstance()
                        .findById(Long.parseLong(id))
                        .orElse(null);

                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                    return;
                }

                writeToResponse(mapper.writeValueAsString(user), response);
            }
        }
    }

    @WebServlet("/api/create/users")
    public static class ApiCreateUsersServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String username = request.getParameter("username");
            String[] roles = request.getParameterValues("role_ids");
            String departmentId = request.getParameter("department_id");

            if (username == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                return;
            }

            User user = new User();
            user.setUsername(username);

            if (departmentId != null) {
                user.setDepartment(DepartmentService.getInstance()
                        .findById(Long.parseLong(departmentId))
                        .orElse(null)
                );
            }

            for (String roleId : roles) {
                user.getRoles().add(
                        RoleService.getInstance().findById(Long.parseLong(roleId)).get()
                );
            }

            UserService.getInstance().save(user);

            writeToResponse(mapper.writeValueAsString(user), response);
        }
    }

    @WebServlet("/api/delete/users")
    public static class ApiDeleteUsersServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) {
            String id = request.getParameter("id");

            if (id == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                if (UserService.getInstance().delete(Long.parseLong(id))) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

    @WebServlet("/api/update/users")
    public static class ApiUpdateUsersServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String[] roles = request.getParameterValues("role_ids");
            String departmentId = request.getParameter("department_id");

            if (id == null || username == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                return;
            }

            User user = new User(Long.parseLong(id), username);

            if (departmentId != null) {
                user.setDepartment(DepartmentService.getInstance()
                        .findById(Long.parseLong(departmentId))
                        .orElse(null)
                );
            }

            for (String roleId : roles) {
                user.getRoles().add(
                        RoleService.getInstance().findById(Long.parseLong(roleId)).get()
                );
            }

            boolean isUpdated = UserService.getInstance().update(user);

            if (isUpdated) {
                writeToResponse(mapper.writeValueAsString(user), response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
