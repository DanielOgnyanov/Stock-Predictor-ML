package service.Impl;

import models.entities.RoleEntity;
import models.enums.Role;
import org.springframework.stereotype.Service;
import repository.RoleRepository;
import service.RoleService;

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
