package main.java.init;

import main.java.service.RoleService;
import main.java.service.UserService;
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

        roleService.InitRoleInDb();

    }
}
