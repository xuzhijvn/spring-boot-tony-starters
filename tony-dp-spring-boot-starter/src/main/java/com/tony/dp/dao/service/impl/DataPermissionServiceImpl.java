/*
 *       Copyright© (2020).
 */
package com.tony.dp.dao.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tony.dp.ApplicationContextHolder;
import com.tony.dp.dao.entity.SysDpResource;
import com.tony.dp.dao.entity.SysDpRole;
import com.tony.dp.dao.entity.SysDpRoleResource;
import com.tony.dp.dao.entity.SysDpUserRole;
import com.tony.dp.dao.mapper.SysDpResourceMapper;
import com.tony.dp.dao.mapper.SysDpRoleMapper;
import com.tony.dp.dao.mapper.SysDpRoleResourceMapper;
import com.tony.dp.dao.mapper.SysDpUserRoleMapper;
import com.tony.dp.dao.service.DataPermissionService;
import com.tony.dp.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tony
 * @create 2021-09-07
 * @description:
 */
public class DataPermissionServiceImpl implements DataPermissionService {

    @Override
    public IPage<SysDpRole> rolePages(RolePagesDTO dto) {
        SysDpRoleMapper sysDpRoleMapper =  ApplicationContextHolder.getBean(SysDpRoleMapper.class);
        Page<SysDpRole> page = new Page<>(dto.getCurrent(), dto.getSize());
        return sysDpRoleMapper.rolePages(page, new QueryWrapper<>());
    }

    @Override
    public List<SysDpResource> getResources() {
        SysDpResourceMapper sysDpResourceMapper =  ApplicationContextHolder.getBean(SysDpResourceMapper.class);

        return sysDpResourceMapper.selectList(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(RoleAddDTO dto) {

        SysDpRoleMapper sysDpRoleMapper =  ApplicationContextHolder.getBean(SysDpRoleMapper.class);
        SysDpRoleResourceMapper sysDpRoleResourceMapper =  ApplicationContextHolder.getBean(SysDpRoleResourceMapper.class);


        SysDpRole role = new SysDpRole();
        role.setName(dto.getRoleName());
        role.setOpUser(dto.getOpUser());
        sysDpRoleMapper.insert(role);

        dto.getResourceIds().forEach(resourceId -> {
            SysDpRoleResource roleResource = new SysDpRoleResource();
            roleResource.setRoleId(role.getId());
            roleResource.setOpUser(dto.getOpUser());
            roleResource.setResourceId(resourceId);
            sysDpRoleResourceMapper.insert(roleResource);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleUpdateDTO dto) {

        SysDpRoleMapper sysDpRoleMapper =  ApplicationContextHolder.getBean(SysDpRoleMapper.class);
        SysDpRoleResourceMapper sysDpRoleResourceMapper =  ApplicationContextHolder.getBean(SysDpRoleResourceMapper.class);

        //更新角色名
        LambdaUpdateWrapper<SysDpRole> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysDpRole::getId, dto.getRoleId())
                .set(SysDpRole::getOpUser, dto.getOpUser())
                .set(SysDpRole::getName, dto.getRoleName());
        sysDpRoleMapper.update(null, wrapper);

        //查询 角色->资源 的关系
        LambdaQueryWrapper<SysDpRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDpRoleResource::getRoleId, dto.getRoleId());
        List<SysDpRoleResource> roleResourceList = sysDpRoleResourceMapper.selectList(queryWrapper);

        List<Long> oldIds = roleResourceList.stream().map(SysDpRoleResource::getResourceId).collect(Collectors.toList());
        List<Long> newIds = dto.getResourceIds();

        //差集
        List<Long> needAddIds = CollUtil.subtractToList(newIds, oldIds);
        List<Long> needDeleteIds = CollUtil.subtractToList(oldIds, newIds);

        //新增
        needAddIds.forEach(e -> {
            SysDpRoleResource entity = new SysDpRoleResource();
            entity.setRoleId(dto.getRoleId());
            entity.setOpUser(dto.getOpUser());
            entity.setResourceId(e);
            sysDpRoleResourceMapper.insert(entity);
        });

        //删除
        needDeleteIds.forEach(e -> {
            queryWrapper.clear();
            queryWrapper.eq(SysDpRoleResource::getRoleId, dto.getRoleId())
                    .eq(SysDpRoleResource::getResourceId, e);
            sysDpRoleResourceMapper.delete(queryWrapper);
        });


        return true;
    }

    @Override
    public List<SysDpUserRole> getRoleUser(RoleUserGetDTO dto) {

        SysDpUserRoleMapper sysDpUserRoleMapper =  ApplicationContextHolder.getBean(SysDpUserRoleMapper.class);

        LambdaQueryWrapper<SysDpUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDpUserRole::getRoleId, dto.getRoleId());
        return sysDpUserRoleMapper.selectList(wrapper);
    }

    @Override
    public boolean deleteRoleUser(RoleUserDeleteDTO dto) {
        SysDpUserRoleMapper sysDpUserRoleMapper =  ApplicationContextHolder.getBean(SysDpUserRoleMapper.class);
        LambdaQueryWrapper<SysDpUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDpUserRole::getRoleId, dto.getRoleId());
        wrapper.eq(SysDpUserRole::getUserId, dto.getUserId());
        return sysDpUserRoleMapper.delete(wrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoleUser(RoleUserAddDTO dto) {
        SysDpUserRoleMapper sysDpUserRoleMapper =  ApplicationContextHolder.getBean(SysDpUserRoleMapper.class);
        dto.getUsers().forEach(user -> {
            SysDpUserRole entity = new SysDpUserRole();
            entity.setRoleId(dto.getRoleId());
            entity.setUserId(user.getUserId());
            entity.setOpUser(dto.getOpUser());
            sysDpUserRoleMapper.insert(entity);
        });
        return true;
    }
}
