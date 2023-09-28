package com.example.thuctap.services.decentralization;

import com.example.thuctap.models.ERole;
import com.example.thuctap.models.Role;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServices implements IRoleServices {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findByRoleName(ERole roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public ProductResponses<Role> addRole(Role role) {
        ProductResponses<Role> responses = new ProductResponses<>();

        role.setCreated_at(new Date());
        roleRepository.save(role);
        responses.setStatus(1);
        responses.setError("Them role thanh cong");
        responses.setData(role);
        return responses;
    }

    @Override
    public ProductResponses<Role> editRole(Role role) {

        ProductResponses<Role> responses = new ProductResponses<>();

        Optional<Role> optionalRole = roleRepository.findById(role.getRoleId());

        if (optionalRole.isEmpty()){
            responses.setStatus(5);
            responses.setError("Role khong ton tai");
            return responses;
        }
        Role currentRole = optionalRole.get();
        currentRole.setUpdate_at(new Date());
        currentRole.setRoleName(role.getRoleName());
        roleRepository.save(currentRole);

        responses.setStatus(1);
        responses.setError("Sua role thanh cong");
        responses.setData(currentRole);
        return responses;
    }

    @Override
    public ProductResponses<Role> deleteRole(int role_id) {
        ProductResponses<Role> responses = new ProductResponses<>();
        Optional<Role> optionalRole = roleRepository.findById(role_id);
        roleRepository.delete(optionalRole.get());

        responses.setStatus(1);
        responses.setError("Xoa role thanh cong");
        responses.setData(optionalRole.get());
        return responses;
    }

    @Override
    public List<Role> showRoles() {
        return roleRepository.findAll();
    }
}
