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
		
		for(int i=1;i<10;i++){
			Thread.sleep(5000);
			if(i%2==0){
				meter.mark(i);
			}
		}
		
		for(int i=1;i<10;i++){
			Thread.sleep(5000);
			if(i%2==0){
				meter.mark();
			}
		}
		//
	}
}