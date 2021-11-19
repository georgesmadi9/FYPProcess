package com.camunda;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.scenario.ProcessScenario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
public class ProcessScenarioTest {

    private static final String PROCESS_DEFINITION_KEY = "FYPProcess";

    static {
        LogFactory.useSlf4jLogging(); // MyBatis
    }

    @Autowired
    private ProcessEngine processEngine;
    @Mock
    private ProcessScenario myProcess;

    @Before
    public void setup() {
        init(processEngine);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHappyPath() {
        // Define scenarios by using camunda-bpm-assert-scenario:

        //ExecutableRunner starter = Scenario.run(myProcess) //
        //    .startByKey(PROCESS_DEFINITION_KEY);

        // when(myProcess.waitsAtReceiveTask(anyString())).thenReturn((messageSubscription) -> {
        //  messageSubscription.receive();
        // });
        // when(myProcess.waitsAtUserTask(anyString())).thenReturn((task) -> {
        //  task.complete();
        // });

        // OK - everything prepared - let's go and execute the scenario
        //Scenario scenario = starter.execute();

        // now you can do some assertions
        //verify(myProcess).hasFinished("EndEvent");
    }

}
