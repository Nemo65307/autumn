package com.nemo.autumn.service;

import com.nemo.autumn.domain.Role;

import java.util.List;

public interface RoleService {

    void createRole(Role role);

    void deleteRole(Role role);

    void updateRole(Role role);

    Role retrieveRoleById(String id);

    Role retrieveRoleByName(String name);

    List<Role> findAllRoles();

}
