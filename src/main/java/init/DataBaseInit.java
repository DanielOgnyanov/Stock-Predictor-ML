package init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import service.RoleService;

@Component
public class DataBaseInit implements CommandLineRunner {

    private final RoleService roleService;

    public DataBaseInit(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {

        roleService.InitRoleInDb();
    }
}
