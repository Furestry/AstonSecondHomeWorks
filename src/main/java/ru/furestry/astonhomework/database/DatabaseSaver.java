package ru.furestry.astonhomework.database;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.furestry.astonhomework.service.DepartmentService;
import ru.furestry.astonhomework.service.RoleService;
import ru.furestry.astonhomework.service.UserService;

@WebListener
public class DatabaseSaver implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        UserService.getInstance().saveAll();
        DepartmentService.getInstance().saveAll();
        RoleService.getInstance().saveAll();
    }
}
