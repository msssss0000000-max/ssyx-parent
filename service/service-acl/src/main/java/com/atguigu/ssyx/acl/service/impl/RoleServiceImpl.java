package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.RoleMapper;
import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    //用户角色关系
    @Autowired
    private AdminRoleService adminRoleService;


    @Override
    public IPage<Role> selectRolePage(Page<Role> pageParam, RoleQueryVo roleQueryVo) {

        //创建条件对象
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();

        //1.获取条件值
        String roleName = roleQueryVo.getRoleName();
        //2.判断条件值是否为空，不为空封装查询条件
        if(!StringUtils.isEmpty(roleName)){
            wrapper.like(Role::getRoleName,roleName);
        }
        //3.调用方法，实现条件分页查询
        IPage<Role> rolePage = baseMapper.selectPage(pageParam, wrapper);
        //4.返回分页对象

        return rolePage;
    }
    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
        //1.查询所有角色
        List<Role> allRoleList = baseMapper.selectList(null);
        //2.根据用户id查询用户分配角色列表
        //2.1根据用户id查询，用户角色关系表admin_role,查询用户分配的角色id列表
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();

        //设置查询条件，根据用户id adminId
        wrapper.eq(AdminRole::getAdminId,adminId);
        List<AdminRole> adminRoleList = adminRoleService.list(wrapper);
        //2.2通过第一步返回的集合，获取所有角色id的列表List<Long>
        List<Long> roleIdList = adminRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        //2.3创建新的list集合，用于存储用户配置角色
        List<Role> assignRoleList = new ArrayList<>();
        //2.4遍历所有角色列表，得到所有allRoleList，得到所有角色
        //判断所有角色里是否包含已经分配的角色id
        for (Role role:allRoleList){
            if(roleIdList.contains(role.getId())){
                assignRoleList.add(role);
            }
        }

        //封装到map，返回
        Map<String, Object> result = new HashMap<>();
        //所有角色列表
        result.put("allRoleList", allRoleList);
        //用户分配角色列表
        result.put("assignRoles", assignRoleList);

        return result;
    }

    //为用户进行分配
    @Override
    public void saveAdminRole(Long adminId, Long[] roleIds) {
        //1.删除用户已经分配过的角色数据
        //根据用户id删除admin_role表里面对应的数据
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId,adminId);
        adminRoleService.remove(wrapper);
        //2.重新分配
        //遍历多个角色id，得到每个角色的id，拿着每个角色id+用户id添加用户角色关系表
        List<AdminRole> list = new ArrayList<>();
        for (Long roleId:roleIds){
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            list.add(adminRole);
        }
        adminRoleService.saveBatch(list);
    }
}
