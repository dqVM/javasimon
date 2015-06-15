package org.javasimon.examples;

//package org.javasimon.examples;

import org.javasimon.Counter;
import org.javasimon.CounterSample;
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
public final class CallbackTesting {
	/**
	 * Entry point to the Callback Example.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		SimonManager.callback().addCallback(new CallbackSkeleton() {
			@Override
			public void onMeterIncrease(Meter meter, long inc, MeterSample sample) {
				System.out.println("\nMeter"+meter.getName()+" mark by"+inc);
				
			}
			
			
			@Override
			public void onCounterIncrease(Counter counter, long inc, CounterSample sample) {
				System.out.println("\nCounter"+counter.getName()+" increase  by"+ inc);
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

		Stopwatch sw = SimonManager.getStopwatch(SimonUtils.generateName()+"-watch");
		Counter c=SimonManager.getCounter(SimonUtils.generateName()+"-counter");
		Meter meter=SimonManager.getMeter(SimonUtils.generateName()+"-meter");
		
		c.increase(2);
		sw.start().stop();
		
		meter.mark(2);

		Split split = sw.start();
		
		//noinspection StatementWithEmptyBody
		for (int i = 0; i < 1000000; i++) {
			// what does JVM do with empty loop? :-)))
		}
		split.stop();

		sw.start().stop();

		System.out.println("\nAdditional stop() does nothing, Split state is preserved.");
		split.stop();
		System.out.println("split = " + split);
	}
}

