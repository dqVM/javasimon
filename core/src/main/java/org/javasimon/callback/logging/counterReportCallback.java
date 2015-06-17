package org.javasimon.callback.logging;

import org.javasimon.Counter;
import org.javasimon.CounterSample;
import org.javasimon.callback.CallbackSkeleton;

public class counterReportCallback extends CallbackSkeleton{
	/**
	 * Default counterReporter which configuration with  
	 */
	
	
	@Override
	public void onCounterIncrease(Counter counter, long inc, CounterSample sample){
		
	}
	
}
