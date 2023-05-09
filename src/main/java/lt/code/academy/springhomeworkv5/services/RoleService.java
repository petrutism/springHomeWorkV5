package lt.code.academy.springhomeworkv5.services;

import lt.code.academy.springhomeworkv5.dto.Role;
import lt.code.academy.springhomeworkv5.entities.RoleEntity;
import lt.code.academy.springhomeworkv5.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role){
        return Role.convert(roleRepository.save(RoleEntity.convert(role)));
    }

    public Role findByName(String name){
        return Role.convert(roleRepository.findByName(name));
    }
}
