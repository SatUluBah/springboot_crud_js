package ru.ulush.springboot_crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.ulush.springboot_crud.dao.RoleDao;
import ru.ulush.springboot_crud.model.RoleFormatter;

@SpringBootApplication
public class SpringbootCrudApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootCrudApplication.class, args);
    }

    static class MyConfig extends WebMvcConfigurerAdapter {

        @Autowired
        RoleDao roleDao;

        public void addFormatters(FormatterRegistry registry) {

            registry.addFormatter(new RoleFormatter(roleDao));
        }
    }

}
