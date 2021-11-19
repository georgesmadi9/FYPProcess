package com.camunda;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.ProcessEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.init;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ProcessTest {

    private static final String PROCESS_DEFINITION_KEY = "FYPProcess";

    static {
        LogFactory.useSlf4jLogging(); // MyBatis
    }

    @Autowired
    private ProcessEngine processEngine;

    @Before
    public void setup() {
        init(processEngine);
    }

    @Test
    public void testHappyPath() {
        // Either: Drive the process by API and assert correct behavior by camunda-bpm-assert, e.g.:
        //ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

        // Now: Drive the process by API and assert correct behavior by camunda-bpm-assert
    }

}
