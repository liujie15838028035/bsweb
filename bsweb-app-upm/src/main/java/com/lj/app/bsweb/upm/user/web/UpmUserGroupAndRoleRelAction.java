package com.lj.app.bsweb.upm.user.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lj.app.bsweb.upm.AbstractBaseUpmAction;
import com.lj.app.bsweb.upm.user.entity.UpmUserGroupAndRoleRel;
import com.lj.app.bsweb.upm.user.service.UpmUserGroupAndRoleRelService;
import com.lj.app.core.common.base.service.BaseService;
import com.lj.app.core.common.pagination.PageTool;
import com.lj.app.core.common.util.AjaxResult;
import com.lj.app.core.common.util.DateUtil;
import com.lj.app.core.common.util.StringUtil;
import com.lj.app.core.common.web.AbstractBaseAction;
import com.lj.app.core.common.web.Struts2Utils;

/**
 * 
 * 用户组合角色关系
 *
 */
@Controller
@Namespace("/jsp/user")
@Results({
    @Result(name = AbstractBaseAction.INPUT, 
        location = "/jsp/user/upmUserGroupAndRoleRel-input.jsp"),
    @Result(name = AbstractBaseAction.SAVE_RESULT, 
        location = "upmUserGroupAndRoleRelAction!edit.action", type = AbstractBaseAction.REDIRECT),
    @Result(name = AbstractBaseAction.LIST_RESULT, 
        location = "/jsp/user/upmUserGroupAndRoleRelList.jsp", type = AbstractBaseAction.REDIRECT) 
    })

@Action("upmUserGroupAndRoleRelAction")
public class UpmUserGroupAndRoleRelAction extends AbstractBaseUpmAction<UpmUserGroupAndRoleRel> {

  private java.lang.Integer id;
  private java.lang.Integer userGroupId;
  private java.lang.Integer roleId;

  @Autowired
  private UpmUserGroupAndRoleRelService upmUserGroupAndRoleRelService;

  private UpmUserGroupAndRoleRel upmUserGroupAndRoleRel;

  public BaseService<UpmUserGroupAndRoleRel> getBaseService() {
    return upmUserGroupAndRoleRelService;
  }

  public UpmUserGroupAndRoleRel getModel() {
    upmUserGroupAndRoleRel = (UpmUserGroupAndRoleRel) upmUserGroupAndRoleRelService.getInfoByKey(id);
    return upmUserGroupAndRoleRel;
  }

  @Override
  protected void prepareModel() throws Exception {
    upmUserGroupAndRoleRel = (UpmUserGroupAndRoleRel) upmUserGroupAndRoleRelService.getInfoByKey(id);
  }

  @Override
  public String list() throws Exception {
    try {
      Map<String, Object> condition = new HashMap<String, Object>();
      condition.put("userGroupId", userGroupId);
      condition.put("roleId", roleId);
      upmUserGroupAndRoleRelService.findPageList(page, condition);
      Struts2Utils.renderText(PageTool.pageToJsonJQGrid(this.page), new String[0]);
      return null;
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }
  }

  @Override
  public String save() throws Exception {

    try {
      if (StringUtil.isEqualsIgnoreCase(operate, AbstractBaseAction.EDIT_RESULT)) {
        upmUserGroupAndRoleRel.setId(id);
        upmUserGroupAndRoleRel.setUserGroupId(userGroupId);
        upmUserGroupAndRoleRel.setRoleId(roleId);
        upmUserGroupAndRoleRel.setUpdateBy(getLoginUserId());
        upmUserGroupAndRoleRel.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
        upmUserGroupAndRoleRelService.updateObject(upmUserGroupAndRoleRel);

        returnMessage = UPDATE_SUCCESS;
      } else {
        upmUserGroupAndRoleRel = new UpmUserGroupAndRoleRel();
        upmUserGroupAndRoleRel.setId(id);
        upmUserGroupAndRoleRel.setUserGroupId(userGroupId);
        upmUserGroupAndRoleRel.setRoleId(roleId);

        upmUserGroupAndRoleRel.setCreateBy(getLoginUserId());
        upmUserGroupAndRoleRel.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
        upmUserGroupAndRoleRelService.insertObject(upmUserGroupAndRoleRel);
        returnMessage = CREATE_SUCCESS;
      }

      return LIST_RESULT;
    } catch (Exception e) {
      returnMessage = CREATE_FAILURE;
      logger.error(e);
      throw e;
    } 

  }

  /**
   * 批量保存
   * @return json
   * @throws Exception 异常
   */
  public String doBatchSaveRel() throws Exception {

    String returnMessage = "";
    String[] multiSelectedTmp;
    if (multiSelected.indexOf(",") > 0) {
      multiSelectedTmp = multiSelected.split(",");
    } else {
      multiSelectedTmp = new String[] { multiSelected };
    }
    for (int i = 0; i < multiSelectedTmp.length; i++) {
      int selectedId = Integer.parseInt(multiSelectedTmp[i].trim());

      try {

        roleId = selectedId;

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userGroupId", userGroupId);
        condition.put("roleId", roleId);

        List<UpmUserGroupAndRoleRel> list = upmUserGroupAndRoleRelService.findBaseModeList("select", condition);
        if (list != null && list.size() > 0) {
          upmUserGroupAndRoleRel = list.get(0);
          upmUserGroupAndRoleRel.setUpdateBy(getLoginUserId());
          upmUserGroupAndRoleRel.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
          upmUserGroupAndRoleRelService.updateObject(upmUserGroupAndRoleRel);
          returnMessage = UPDATE_SUCCESS;

        } else {
          upmUserGroupAndRoleRel = new UpmUserGroupAndRoleRel();
          upmUserGroupAndRoleRel.setUserGroupId(userGroupId);
          upmUserGroupAndRoleRel.setRoleId(roleId);

          upmUserGroupAndRoleRel.setCreateBy(getLoginUserId());
          upmUserGroupAndRoleRel.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
          upmUserGroupAndRoleRelService.insertObject(upmUserGroupAndRoleRel);
          returnMessage = CREATE_SUCCESS;
        }
      } catch (Exception e) {
        returnMessage = "保存失败";
        logger.error(e);
        throw e;
      }
    }
    AjaxResult ar = new AjaxResult();
    ar.setOpResult(returnMessage);

    Struts2Utils.renderJson(ar);
    return null;
  }

  @Override
  public String delete() throws Exception {
    return null;
  }

  public void setId(java.lang.Integer value) {
    this.id = value;
  }

  public java.lang.Integer getId() {
    return this.id;
  }

  public void setUserGroupId(java.lang.Integer value) {
    this.userGroupId = value;
  }

  public java.lang.Integer getUserGroupId() {
    return this.userGroupId;
  }

  public void setRoleId(java.lang.Integer value) {
    this.roleId = value;
  }

  public java.lang.Integer getRoleId() {
    return this.roleId;
  }

}
