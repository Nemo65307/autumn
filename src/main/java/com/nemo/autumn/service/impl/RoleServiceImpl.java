package com.nemo.autumn.service.impl;

import com.nemo.autumn.repository.RoleRepository;
import com.nemo.autumn.domain.Role;
import com.nemo.autumn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void createRole(Role role) {
        repository.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Role role) {
        repository.delete(role);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        repository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role retrieveRoleById(String id) {
        Optional<Role> role = repository.findById(id);
        return role.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Role retrieveRoleByName(String name) {
        Optional<Role> role = repository.findByName(name);
        return role.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllRoles() {
        return repository.findAll();
    }

}