package com.lj.app.core.common.flows.service;

import java.io.InputStream;

import com.lj.app.core.common.base.service.BaseService;

/**
 * 
 * 流程定义表
 *
 * @param <FlowProcess> 流程定义表
 */
public interface FlowProcessService<FlowProcess> extends BaseService {

  /**
   * 检查流程定义对象
   * 
   * @param process
   *          流程定义对象
   * @param idOrName
   *          流程定义id/name
   */
  public void check(com.lj.app.core.common.flows.entity.FlowProcess process, String idOrName) throws Exception;

  /**
   * 保存流程定义
   * 
   * @param process
   *          流程定义对象
   */
  public void saveFlowProcess(com.lj.app.core.common.flows.entity.FlowProcess process) throws Exception;

  /**
   * 更新流程定义的类别
   * 
   * @param id
   *          流程定义id
   * @param type
   *          类别
   * @since 1.5
   */
  public void updateType(String id, String type) throws Exception;

  /**
   * 根据主键ID获取流程定义对象
   * 
   * @param id
   *          流程定义id
   * @return Process 流程定义对象
   */
  public com.lj.app.core.common.flows.entity.FlowProcess getProcessById(String id) throws Exception;

  /**
   * 根据流程name获取流程定义对象
   * 
   * @param name
   *          流程定义名称
   * @return Process 流程定义对象
   */
  public com.lj.app.core.common.flows.entity.FlowProcess getProcessByName(String name) throws Exception;

  /**
   * 根据流程name、version获取流程定义对象
   * 
   * @param name
   *          流程定义名称
   * @param version
   *          版本号
   * @return Process 流程定义对象
   */
  public com.lj.app.core.common.flows.entity.FlowProcess getProcessByVersion(String name, Integer version)
      throws Exception;

  /**
   * 根據InputStream輸入流，部署流程定义
   * 
   * @param input
   *          流程定义输入流
   * @return String 流程定义id
   */
  public String deploy(InputStream input);

  /**
   * 根據InputStream輸入流，部署流程定义
   * 
   * @param input
   *          流程定义输入流
   * @param creator
   *          创建人
   * @return String 流程定义id
   */
  public String deploy(InputStream input, String creator);

  /**
   * 根據input輸入流，部署流程定义
   * 
   * @param inputStr
   *          流程定义输入流
   * @param creator
   *          创建人
   * @return String 流程定义id
   */
  public String deploy(String inputStr, String creator);

  /**
   * 根據InputStream輸入流，部署流程定义
   * 
   * @param id
   *          流程定义id
   * @param input
   *          流程定义输入流
   */
  public void redeploy(String id, InputStream input);

  /**
   * 根據InputStream輸入流，部署流程定义
   * 
   * @param id
   *          流程定义id
   * @param inputStr
   *          流程定义xml内容
   */
  public void redeploy(String id, String inputStr);

  /**
   * 卸载指定的流程定义，只更新状态
   * 
   * @param id
   *          流程定义id
   */
  public void undeploy(String id) throws Exception;

  /**
   * 获得流程最新版本
   * 
   * @param flowName 流程名称
   * @return 获得流程最新版本
   */
  public int getLatestProcessVersion(String flowName) throws Exception;
}
