package com.example.thuctap.controller;

import com.example.thuctap.models.Role;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.services.decentralization.RoleServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("isAuthenticated()")
public class RoleController {

    @Autowired
    private RoleServices roleServices;

    @RequestMapping(value = "add_role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponses<Role> addDecentralization(@RequestBody String role) {

        Gson gson = new Gson();
        Role result = gson.fromJson(role, Role.class);
        return roleServices.addRole(result);
    }

    @RequestMapping(value = "edit_role", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponses<Role> editDecentralization(@RequestBody String role) {

        Gson gson = new Gson();

        Role result = gson.fromJson(role, Role.class);
        return roleServices.editRole(result);
    }

    @RequestMapping(value = "delete_role", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponses<Role> deleteDecentralization(@RequestParam int role_id) {
        return roleServices.deleteRole(role_id);
    }

    @RequestMapping(value = "show_roles", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> showDecentralization() {

        return roleServices.showRoles();
    }
}
