package com.nanyin.services.impl;

import com.nanyin.config.enums.TranferDirection;
import com.nanyin.config.util.PageHelper;
import com.nanyin.entity.DTO.TranferDto;
import com.nanyin.entity.Permission;
import com.nanyin.entity.QRole;
import com.nanyin.entity.Role;
import com.nanyin.entity.User;
import com.nanyin.repository.PermissionRepository;
import com.nanyin.repository.RoleRepository;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.RoleService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Role saveRole(Role role)  throws Exception{
        role.setGmtCreate(new Date());
        role.setGmtModify(new Date());
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role getRole(Integer id)  throws Exception {
        return roleRepository.getOne(id);
    }

    @Override
    public Role updateRole(Role role) throws Exception {
        role.setGmtModify(new Date());
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }


    @Override
    public Page<Role> findRoles(String search, Integer offset, Integer limit, String order) throws Exception {
        // 获取分页
        PageRequest pageRequest = PageHelper.generatePageRequest(offset, limit);
        QRole role = QRole.role;
        Predicate predicate = role.isNotNull().or(role.isNull());
        predicate = search == null ? predicate : ExpressionUtils.and(predicate,role.name.like("%"+search+"%"));
        return roleRepository.findAll(predicate,pageRequest);
    }

    @Override
    public void movePersons(TranferDto tranferDto) throws Exception{
        Integer roleId = Integer.parseInt(tranferDto.getStandardKey());
        String direction = tranferDto.getDirection();
        Integer[] keys = tranferDto.getMovedKeys();
        List<User> users = doMovePersons(direction,keys,roleId);
        userRepository.saveAll(users);
    }

    private List<User> doMovePersons(String direction,Integer[] keys,Integer standardKey){
        List<User> users = new ArrayList<>();
        Role role = roleRepository.getOne(standardKey);
        if(TranferDirection.RIGHT.equals(direction)){
            for (Integer key : keys) {
                users.add(decideMovePersonDirection(key,role,true));
            }
        }else if(TranferDirection.LEFT.equals(direction)){
            for (Integer key : keys) {
                users.add(decideMovePersonDirection(key,role,false));
            }
        }
        return users;
    }

    private User decideMovePersonDirection(Integer key,Role role,boolean right){
        Optional<User> one = userRepository.findById(key);
        if(one.isPresent()){
            User temp = one.get();
            if(right){
                temp.getRoles().add(role);
                role.getUsers().add(temp);
            }else{
                temp.getRoles().remove(role);
                role.getUsers().remove(temp);
            }
            return temp;
        }
        return null;
    }


    @Override
    public void movePermission(TranferDto tranferDto) throws Exception {
        Integer roleId = Integer.parseInt(tranferDto.getStandardKey());
        String direction = tranferDto.getDirection();
        Integer[] keys = tranferDto.getMovedKeys();
        List<Permission> permissions = doMovePermission(direction,keys,roleId);
        permissionRepository.saveAll(permissions);

    }

    private List<Permission> doMovePermission(String direction, Integer[] keys, Integer roleId) {
        List<Permission> permissions = new ArrayList<>();
        Role role = roleRepository.getOne(roleId);
        if(TranferDirection.RIGHT.equals(direction)){
            for (Integer key : keys) {
                permissions.add(decideMovePermissionDirection(key,role,true));
            }
        }else if(TranferDirection.LEFT.equals(direction)){
            for (Integer key : keys) {
                permissions.add(decideMovePermissionDirection(key,role,false));
            }
        }
        return permissions;
    }

    private Permission decideMovePermissionDirection(Integer key, Role role, boolean right) {
        Optional<Permission> one = permissionRepository.findById(key);
        if(one.isPresent()){
            Permission temp = one.get();
            if(right){
                temp.getRoles().add(role);
                role.getPermissions().add(temp);
            }else{
                temp.getRoles().remove(role);
                role.getPermissions().remove(temp);
            }
            return temp;
        }
        return null;
    }


}
