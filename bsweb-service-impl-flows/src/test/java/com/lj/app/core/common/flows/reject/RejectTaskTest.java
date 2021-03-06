package com.lj.app.core.common.flows.reject;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.lj.app.core.common.flows.FlowBaseTest;
import com.lj.app.core.common.util.FileUtil;

/**
 * 
 * 驳回测试
 *
 */
public class RejectTaskTest extends FlowBaseTest {

  @Before
  public void before() {
    processId = flowEngine.flowProcessService()
        .deploy(FileUtil.getStreamFromClasspath("com/lj/app/core/common/flows/reject/flow1.xml"));
  }

  @Test
  public void taskTest() throws Exception {
    flowEngine.startInstanceById(processId);

    Map<String, Object> args = new HashMap<String, Object>();
    args.put("number", 2);
    flowEngine.executeTask(processId, null, args);
    flowEngine.executeAndJumpTask(processId, null, args, "task1");
  }
}
