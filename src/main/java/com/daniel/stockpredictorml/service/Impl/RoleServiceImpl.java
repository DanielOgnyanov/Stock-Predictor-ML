package com.daniel.stockpredictorml.service.Impl;



import com.daniel.stockpredictorml.models.entities.RoleEntity;
import com.daniel.stockpredictorml.models.enums.Role;
import com.daniel.stockpredictorml.repository.RoleRepository;
import com.daniel.stockpredictorml.service.RoleService;
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
