package com.example.thuctap.services.decentralization;

import com.example.thuctap.models.ERole;
import com.example.thuctap.models.Role;
import com.example.thuctap.models.responseobject.ProductResponses;

import java.util.List;
import java.util.Optional;

public interface IRoleServices {
    Optional<Role> findByRoleName(ERole roleName);
    ProductResponses<Role> addRole(Role role);
    ProductResponses<Role> editRole(Role role);
    ProductResponses<Role> deleteRole(int role_id);
    List<Role> showRoles();
}
