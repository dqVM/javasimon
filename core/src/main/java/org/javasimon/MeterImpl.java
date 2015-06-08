package org.javasimon;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class MeterImpl extends AbstractSimon implements Meter {

	private long startTime;
	private long counter; 
	private long incrementSum;
	
	private static final long TICK_INTERVAL = TimeUnit.SECONDS.toNanos(5);
	
	

	MeterImpl(String name, Manager manager) {
		super(name, manager);
		this.start();
	}

	
	@Override
	public void start() {
		this.startTime=manager.milliTime();
		this.counter=0;
	}
	
	
	@Override
	public Meter mark() {
		return mark(1);
	}

	@Override
	public Meter mark(long inc) {
		if(!enabled){
			return this;
		}
		long now = manager.milliTime();
		MeterSample sample;
		synchronized (this) {
			increasePrivate(inc, now);
			updateIncrementalSimonsIncrease(inc, now);
			sample = sampleIfCallbacksNotEmpty();
		}
		manager.callback().onMeterIncrease(this, inc, sample);
		return this;
	}

	
	
	private MeterSample sampleIfCallbacksNotEmpty() {
		if (!manager.callback().callbacks().isEmpty()) {
			return sample();
		}
		return null;
	}


	private void increasePrivate(long inc, long now) {
		updateUsages(now);
		setIncrementSum(getIncrementSum() + inc);
		counter += inc;
	}
	
	
	


	private void updateIncrementalSimonsIncrease(long inc, long now) {
		Collection<Simon> simons = incrementalSimons();
		if (simons != null) {
			for (Simon simon : simons) {
				((MeterImpl) simon).increasePrivate(inc, now);
			}
		}
	}
	
	
	@Override
	public long getCount() {
		return counter;
	}

	@Override
	public double getFifteenMinuteRate() {
		return 0;
	}

	@Override
	public double getFiveMinuteRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMeanRate() {
		
		if(counter==0){
			return 0.0;
		}else{
			final double elapsed=manager.milliTime()-startTime;
			return getCount() / elapsed * TimeUnit.MILLISECONDS.toNanos(1);
		}
	}

	@Override
	public double getOneMinuteRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MeterSample sample() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MeterSample sampleIncrement(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MeterSample sampleIncrementNoReset(Object key) {
		// TODO Auto-generated method stub
		return null;
	}


	public long getIncrementSum() {
		return incrementSum;
	}


	public void setIncrementSum(long incrementSum) {
		this.incrementSum = incrementSum;
	}	

}
