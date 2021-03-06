package com.lj.app.bsweb.upm.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lj.app.core.common.base.service.BaseServiceImpl;

/**
 * 
 * 用户组服务
 *
 * @param <UpmUserGroup>
 *          用户组对象
 */
@Service("upmUserGroupService")
public class UpmUserGroupServiceImpl<UpmUserGroup> extends BaseServiceImpl<UpmUserGroup>
    implements UpmUserGroupService<UpmUserGroup> {
  /**
   * 查询用户组数据
   * 
   * @return 用户组列表
   */
  public List<UpmUserGroup> findUpmUserGroupByParentId(Long treeNodeId) {
    Map<String, Object> condition = new HashMap<String, Object>();
    condition.put("parentId", treeNodeId);

    List<UpmUserGroup> upmUserGroupList = queryForList("findUpmUserGroupByParentId", condition);

    return upmUserGroupList;
  }

  /**
   * 查询用户组数据
   * 
   * @return 用户组列表
   */
  public List<UpmUserGroup> findUpmUserGroupByGroupName(String userGroupName) {
    Map<String, Object> condition = new HashMap<String, Object>();
    condition.put("userGroupName", userGroupName);
    List<UpmUserGroup> upmUserGroupList = queryForList("findUpmUserGroupByGroupName", condition);

    return upmUserGroupList;
  }
}
