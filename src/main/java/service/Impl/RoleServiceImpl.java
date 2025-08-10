package main.java.service.Impl;



import main.java.models.entities.RoleEntity;
import main.java.models.enums.Role;
import main.java.repository.RoleRepository;
import main.java.service.RoleService;
import org.springframework.stereotype.Service;



import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void InitRoleInDb() {

        if (roleRepository.count() == 0) {
            Arrays.stream(Role.values()).forEach(currentRole -> {
                RoleEntity role = new RoleEntity();
                role.setName(currentRole);
                roleRepository.save(role);
            });
        }
    }
}
