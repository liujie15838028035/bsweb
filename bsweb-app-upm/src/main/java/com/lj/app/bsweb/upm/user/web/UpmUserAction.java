package com.lj.app.bsweb.upm.user.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lj.app.bsweb.upm.AbstractBaseUpmAction;
import com.lj.app.core.common.base.entity.BaseEntity;
import com.lj.app.core.common.base.entity.UpmUser;
import com.lj.app.core.common.base.service.BaseService;
import com.lj.app.core.common.base.service.UpmUserService;
import com.lj.app.core.common.exception.BusinessException;
import com.lj.app.core.common.pagination.PageTool;
import com.lj.app.core.common.security.DesUtil;
import com.lj.app.core.common.util.AjaxResult;
import com.lj.app.core.common.util.DateUtil;
import com.lj.app.core.common.util.SessionCode;
import com.lj.app.core.common.util.StringUtil;
import com.lj.app.core.common.web.AbstractBaseAction;
import com.lj.app.core.common.web.Struts2Utils;

/**
 * 
 * 用户管理
 *
 */
@Controller
@Namespace("/jsp/user")
@Results({ 
    @Result(name = AbstractBaseAction.INPUT, 
        location = "upmUser-input.jsp"),
    @Result(name = AbstractBaseAction.SAVE_RESULT, 
        location = "upmUserAction!edit.action", type = AbstractBaseAction.REDIRECT),
    @Result(name = AbstractBaseAction.LIST_RESULT, 
        location = "upmUserList.jsp", type = AbstractBaseAction.REDIRECT) 
        })

@Action("upmUserAction")
public class UpmUserAction extends AbstractBaseUpmAction<UpmUser> {

  private java.lang.Integer id;
  private String loginNo;
  private String pwd;
  private String userName;
  private String address;
  private String mobile;

  private String email;

  /**
   * 组织机构描述
   */
  private String orgDesc;

  private Long treeNodeId;

  private String oldPwd;
  private String newPwd;

  @Autowired
  private UpmUserService upmUserService;

  private UpmUser upmUser;

  public BaseService<UpmUser> getBaseService() {
    return upmUserService;
  }

  public UpmUser getModel() {
    return upmUser;
  }

  @Override
  protected void prepareModel() throws Exception {
    if (id != null) {
      upmUser = (UpmUser) upmUserService.getInfoByKey(id);
      String password = upmUser.getPwd();
      upmUser.setPwd(DesUtil.decrypt(password));

    } else {
      upmUser = new UpmUser();
    }
  }

  /**
   * 查询用户
   * @return json
   * @throws Exception 异常
   */
  public String listUserByCondition() throws Exception {
    try {
      Map<String, Object> condition = new HashMap<String, Object>();
      condition.put("loginNo", loginNo);
      condition.put("userName", userName);
      condition.put("address", address);
      condition.put("mobile", mobile);
      condition.put("orgDesc", orgDesc);
      condition.put("treeNodeId", treeNodeId);
      condition.put("email", email);
      upmUserService.findPageList(page, condition, "listUserByCondition");
      Struts2Utils.renderText(PageTool.pageToJsonBootStrap(this.page), new String[0]);
      return null;
    } catch (Exception e) {
      logger.error(e);
      throw e;
    }
  }

  /**
   * 公共保存或者更新方法
   * 
   * @return 列表
   */
  @Override
  public String commonSaveOrUpdate() throws Exception {

    try {
      if (StringUtil.isEqualsIgnoreCase(operate, AbstractBaseAction.EDIT_RESULT)) {
        BaseEntity entity = (BaseEntity) getModel();
        entity.setUpdateBy(this.getLoginUserId());
        entity.setUpdateByUname(this.getUserName());
        entity.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

        String password = upmUser.getPwd();
        upmUser.setPwd(DesUtil.encrypt(password));

        getBaseService().updateObject(entity);
        returnMessage = UPDATE_SUCCESS;
      } else {
        upmUser.setCreateBy(this.getLoginUserId());
        upmUser.setCreateByUname(this.getUserName());
        upmUser.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

        String password = upmUser.getPwd();
        upmUser.setPwd(DesUtil.encrypt(password));

        getBaseService().insertObject(upmUser);
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
   * 修改密码
   * @return json
   * @throws Exception 异常
   */
  public String updateAcctPwd() throws Exception {
    AjaxResult ar = new AjaxResult();

    try {

      UpmUser loginUser = (UpmUser) Struts2Utils.getSession().getAttribute(SessionCode.MAIN_ACCT);
      if (!loginUser.getPwd().equals(DesUtil.encrypt(oldPwd))) {
        ar.setOpResult("对不起!旧密码不正确,请重新输入");
        Struts2Utils.renderJson(ar);
        return null;
      }

      if (!pwd.equals(newPwd)) {
        ar.setOpResult("对不起!输入新密码两次不一致，请重新输入");
        Struts2Utils.renderJson(ar);
        return null;
      }

      loginUser.setId(getLoginUserId());
      loginUser.setPwd(DesUtil.encrypt(pwd));
      loginUser.setUpdateBy(getLoginUserId());
      loginUser.setUpdateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
      upmUserService.updateObject(loginUser);

      Struts2Utils.getSession().setAttribute(SessionCode.MAIN_ACCT, loginUser);

      ar.setOpResult(UPDATE_SUCCESS);
      Struts2Utils.renderJson(ar);
    } catch (Exception e) {
      ar.setOpResult(UPDATE_FAILURE);
      Struts2Utils.renderJson(ar);
      throw e;
    }

    return null;

  }

  /**
   * 删除校验
   * 
   * @param deleteId 用户Id
   * @throws Exception 异常
   */
  public void multideleteValidate(Integer deleteId) throws BusinessException {
    if (deleteId == null)  {
      throw new BusinessException("删除Id不能为空");
    }
    
    if (deleteId != null && deleteId.intValue() <= 1) {
      throw new BusinessException("超级管理员无法删除");
    }
    if (deleteId == this.getLoginUserId()) {
      throw new BusinessException("当前操作用户不能删除自己的账号");
    }
  }

  public void setId(java.lang.Integer value) {
    this.id = value;
  }

  public java.lang.Integer getId() {
    return this.id;
  }

  public void setLoginNo(String value) {
    this.loginNo = value;
  }

  public String getLoginNo() {
    return this.loginNo;
  }

  public void setPwd(String value) {
    this.pwd = value;
  }

  public String getPwd() {
    return this.pwd;
  }

  public void setUserName(String value) {
    this.userName = value;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setAddress(String value) {
    this.address = value;
  }

  public String getAddress() {
    return this.address;
  }

  public void setMobile(String value) {
    this.mobile = value;
  }

  public String getMobile() {
    return this.mobile;
  }

  public UpmUserService getUpmUserService() {
    return upmUserService;
  }

  public void setUpmUserService(UpmUserService upmUserService) {
    this.upmUserService = upmUserService;
  }

  public UpmUser getUpmUser() {
    return upmUser;
  }

  public void setUpmUser(UpmUser upmUser) {
    this.upmUser = upmUser;
  }

  public Long getTreeNodeId() {
    return treeNodeId;
  }

  public void setTreeNodeId(Long treeNodeId) {
    this.treeNodeId = treeNodeId;
  }

  public String getOrgDesc() {
    return orgDesc;
  }

  public void setOrgDesc(String orgDesc) {
    this.orgDesc = orgDesc;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getOldPwd() {
    return oldPwd;
  }

  public void setOldPwd(String oldPwd) {
    this.oldPwd = oldPwd;
  }

  public String getNewPwd() {
    return newPwd;
  }

  public void setNewPwd(String newPwd) {
    this.newPwd = newPwd;
  }

}
