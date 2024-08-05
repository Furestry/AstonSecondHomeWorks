package ru.furestry.astonhomework.servlet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.furestry.astonhomework.entity.Role;
import ru.furestry.astonhomework.service.RoleService;
import ru.furestry.astonhomework.service.UserService;

import java.io.IOException;

import static ru.furestry.astonhomework.servlet.api.ApiServlet.writeToResponse;

public class ApiRolesServlet {
    @WebServlet("/api/read/roles")
    public static class ApiReadRolesServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String id = request.getParameter("id");

            if (id == null) {
                writeToResponse(
                        mapper.writeValueAsString(RoleService.getInstance().findAll()),
                        response
                );
            } else {
                Role role = RoleService.getInstance()
                        .findById(Long.parseLong(id))
                        .orElse(null);

                if (role == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                    return;
                }

                writeToResponse(mapper.writeValueAsString(role), response);
            }
        }
    }

    @WebServlet("/api/create/roles")
    public static class ApiCreateRolesServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String name = request.getParameter("username");
            String[] users = request.getParameterValues("user_ids");

            if (name == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                return;
            }

            Role role = new Role();
            role.setName(name);

            for (String userId : users) {
                role.getUsers().add(
                        UserService.getInstance().findById(Long.parseLong(userId)).get()
                );
            }

            RoleService.getInstance().save(role);

            writeToResponse(mapper.writeValueAsString(role), response);
        }
    }

    @WebServlet("/api/delete/roles")
    public static class ApiDeleteRolesServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) {
            String id = request.getParameter("id");

            if (id == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                if (RoleService.getInstance().delete(Long.parseLong(id))) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

    @WebServlet("/api/update/roles")
    public static class ApiUpdateRolesServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String[] users = request.getParameterValues("user_ids");

            if (id == null || name == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                return;
            }

            Role role = new Role(Long.parseLong(id), name);

            for (String userId : users) {
                role.getUsers().add(
                        UserService.getInstance().findById(Long.parseLong(userId)).get()
                );
            }

            boolean isUpdated = RoleService.getInstance().update(role);

            if (isUpdated) {
                writeToResponse(mapper.writeValueAsString(role), response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
