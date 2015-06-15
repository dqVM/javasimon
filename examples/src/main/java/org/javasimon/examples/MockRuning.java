package org.javasimon.examples;

import org.javasimon.Meter;
import org.javasimon.SimonManager;
import org.javasimon.utils.SimonUtils;

public class MockRuning {
	
	public MockRuning() {
		// TODO Auto-generated constructor stub
	}
	
	public void running() throws InterruptedException{
		Meter meter=SimonManager.getMeter(SimonUtils.generateName()+"Meter");
		
		for(int i=1;i<100;i++){
			Thread.sleep(1000);
			meter.mark();
		}
		//
	}
}
