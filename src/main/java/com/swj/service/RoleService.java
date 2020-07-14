package com.swj.service;

import com.swj.entity.TbRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swj.vo.ConditionalVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swj
 * @since 2020-06-09
 */
public interface RoleService extends IService<TbRole> {

    int addRole(TbRole role);

    int updateRole(TbRole role);

    int deleteRole(int id);

    List<TbRole> getRoleList(Integer page, Integer limit, ConditionalVO vo);

    long getTotal();

    TbRole getRoleById(int id);

    void setPerms(int id, String perms);
}
