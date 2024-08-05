package ru.furestry.astonhomework.servlet.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.furestry.astonhomework.entity.Department;
import ru.furestry.astonhomework.service.DepartmentService;
import ru.furestry.astonhomework.service.UserService;

import java.io.IOException;

import static ru.furestry.astonhomework.servlet.api.ApiServlet.writeToResponse;

public class ApiDepartmentsServlet {
    @WebServlet("/api/read/departments")
    public static class ApiReadDepartmentsServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String id = request.getParameter("id");

            if (id == null) {
                writeToResponse(
                        mapper.writeValueAsString(DepartmentService.getInstance().findAll()),
                        response
                );
            } else {
                Department department = DepartmentService.getInstance()
                        .findById(Long.parseLong(id))
                        .orElse(null);

                if (department == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                    return;
                }

                writeToResponse(mapper.writeValueAsString(department), response);
            }
        }
    }

    @WebServlet("/api/create/departments")
    public static class ApiCreateDepartmentsServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String name = request.getParameter("username");
            String[] users = request.getParameterValues("user_ids");

            if (name == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                return;
            }

            Department department = new Department();
            department.setName(name);

            for (String userId : users) {
                department.getUsers().add(
                        UserService.getInstance().findById(Long.parseLong(userId)).get()
                );
            }

            DepartmentService.getInstance().save(department);

            writeToResponse(mapper.writeValueAsString(department), response);
        }
    }

    @WebServlet("/api/delete/departments")
    public static class ApiDeleteDepartmentsServlet extends HttpServlet {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) {
            String id = request.getParameter("id");

            if (id == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                if (DepartmentService.getInstance().delete(Long.parseLong(id))) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

    @WebServlet("/api/update/departments")
    public static class ApiUpdateDepartmentsServlet extends HttpServlet {
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

            Department department = new Department(Long.parseLong(id), name);

            for (String userId : users) {
                department.getUsers().add(
                        UserService.getInstance().findById(Long.parseLong(userId)).get()
                );
            }

            boolean isUpdated = DepartmentService.getInstance().update(department);

            if (isUpdated) {
                writeToResponse(mapper.writeValueAsString(department), response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
