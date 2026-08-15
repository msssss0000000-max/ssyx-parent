package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.utils.PermissionHelper;
import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    //查询所有菜单
    @Override
    public List<Permission> queryAllPermission() {
        //查询所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(null);
        //转换要求数据格式
        List<Permission> result = PermissionHelper.buildPermission(allPermissionList);
        return result;
    }
//999999999999999999999999999
    @Override
    public void removeChildById(long id) {

        List<Long> idList = new ArrayList<>();
        //创建idlist集合，封装所有删除的所有菜单的id
        //如果子菜单下还有子菜单，要都获取到
        //重点：递归方式找当前菜单下的子菜单
        this.getAllPermissionId(id,idList);
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }
    //递归找当前菜单下面的子菜单，第一个参数为当前菜单id，list集合包含所有菜单id
    private void getAllPermissionId(long id, List<Long> idList) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid,id);
        List<Permission> childList = baseMapper.selectList(wrapper);

        childList.stream().forEach(item->{
            idList.add(item.getId());

            //此处递归
            this.getAllPermissionId(item.getId(),idList);
        });
    }
}
