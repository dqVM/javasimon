package org.javasimon.examples;

import java.util.concurrent.TimeUnit;

import org.javasimon.ConsoleReporter;
import org.javasimon.Meter;
import org.javasimon.MeterSample;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.javasimon.StopwatchSample;
import org.javasimon.callback.CallbackSkeleton;
import org.javasimon.utils.SimonUtils;



/**
 * CallbackExample shows how to implement callback that prints out some information on the specific events.
 *
 * @author <a href="mailto:virgo47@gmail.com">Richard "Virgo" Richter</a>
 */
public final class CallbackExample {
	/**
	 * Entry point to the Callback Example.
	 *
	 * @param args unused
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
	
		
		
		
		
		SimonManager.callback().addCallback(new CallbackSkeleton() {
		
			@Override
			public void onMeterIncrease(Meter meter, long inc, MeterSample sample) {
//				if(inc%10==0){
//					System.out.println("\n Meter "+meter.getName()+" current report:"+meter);
//				}
			}
			
			@Override
			public void onStopwatchStart(Split split) {
				System.out.println("\nStopwatch " + split.getStopwatch().getName() + " has just been started.");
			}

			@Override
			public void onStopwatchStop(Split split, StopwatchSample sample) {
				System.out.println("Stopwatch " + split.getStopwatch().getName()
					+ " has just been stopped (" + SimonUtils.presentNanoTime(split.runningFor()) + ").");
			}
		});

		startReport();
		
		MockRuning mRuning=new MockRuning();
		
		mRuning.running();
//		Stopwatch sw = SimonManager.getStopwatch(SimonUtils.generateName());
//		sw.start().stop();
//
//		Split split = sw.start();
//		//no-inspection StatementWithEmptyBody
//		for (int i = 0; i < 1000000; i++) {
//			// what does JVM do with empty loop? :-)))
//		}
//		split.stop();
//
//		sw.start().stop();
//
//		//System.out.println("\nAdditional stop() does nothing, Split state is preserved.");
//		split.stop();
//		//System.out.println("split = " + split);
	}

	private static void startReport() {
		ConsoleReporter consoleReporter=ConsoleReporter.forManager(SimonManager.manager())
				.convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
		
		consoleReporter.start(5, TimeUnit.SECONDS);
		
	}
}
