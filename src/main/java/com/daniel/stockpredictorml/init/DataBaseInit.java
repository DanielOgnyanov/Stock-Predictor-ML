package com.daniel.stockpredictorml.init;


import com.daniel.stockpredictorml.service.RoleService;
import com.daniel.stockpredictorml.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataBaseInit implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    public DataBaseInit(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        userService.createInitialUser();
        roleService.InitRoleInDb();

    }
}
