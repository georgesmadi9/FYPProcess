package com.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ChoiceSaver implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //String grpc1 = (String) delegateExecution.getVariable("grpchoice1");
        //String grpc2 = (String) delegateExecution.getVariable("grpchoice2");
        //System.out.println("grp1 : "+ grpc1 +"\n grp2 : "+grpc2);
        delegateExecution.setVariable("test","test");
    }
}
