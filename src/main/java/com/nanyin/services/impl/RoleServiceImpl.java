package com.nanyin.services.impl;

import com.nanyin.config.enums.TranferDirection;
import com.nanyin.entity.DTO.TranferDto;
import com.nanyin.entity.QRole;
import com.nanyin.entity.Role;
import com.nanyin.entity.User;
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
        if(offset == null || limit == null){
            offset = 1;
            limit = Integer.MAX_VALUE;
        }
        PageRequest pageRequest = PageRequest.of(offset-1,limit);
        QRole role = QRole.role;
        Predicate predicate = role.isNotNull().or(role.isNull());
        predicate = search == null ? predicate : ExpressionUtils.and(predicate,role.name.like("%"+search+"%"));
        return roleRepository.findAll(predicate,pageRequest);
    }


    @Override
    public void movePerson(TranferDto tranferDto) throws Exception{
        Integer roleId = Integer.parseInt(tranferDto.getStandardKey());
        Role role = roleRepository.getOne(roleId);
        String direction = tranferDto.getDirection();
        Integer[] keys = tranferDto.getMovedKeys();
        List<User> users = new ArrayList<>();
        if(TranferDirection.RIGHT.equals(direction)){
            for (Integer key : keys) {
                Optional<User> one = userRepository.findById(key);
                if(one.isPresent()){
                    User temp = one.get();
                    // 多对多关系需要双向关联保存才起作用
                    temp.getRoles().add(role);
                    role.getUsers().add(temp);
                    users.add(temp);
                }
            }

        }else if(TranferDirection.LEFT.equals(direction)){
            for (Integer key : keys) {
                Optional<User> one = userRepository.findById(key);
                if(one.isPresent()){
                    User temp = one.get();
                    temp.getRoles().remove(role);
                    role.getUsers().remove(temp);
                    users.add(temp);
                }
            }
        }
        userRepository.saveAll(users);
    }

}
