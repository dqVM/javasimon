package org.javasimon;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

//import org.javasimon.utils.SimonUtils;

public class MeterImpl extends AbstractSimon implements Meter {

	/**
	 * startTime: Meter start nano Time 
	 * 
	 * */
	private long startTime; 
	private long counter; 
	private long incrementSum;
	private AtomicLong lastTick;
	
	private static final long TICK_INTERVAL = TimeUnit.SECONDS.toNanos(5);
	private final EWMA m1Rate = EWMA.oneMinuteEWMA();
    private final EWMA m5Rate = EWMA.fiveMinuteEWMA();
    private final EWMA m15Rate = EWMA.fifteenMinuteEWMA();

	

	MeterImpl(String name, Manager manager) {
		super(name, manager);
		this.start();
	}

	
	@Override
	public void start() {
		this.startTime=manager.nanoTime();
		this.lastTick=new AtomicLong(this.startTime);
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
		/*
		 * incremental simon update*/
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
		tickIfNecessary();
		m1Rate.update(inc);
		m5Rate.update(inc);
		m15Rate.update(inc);
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

	

	private void tickIfNecessary() {
		// TODO Auto-generated method stub
		 final long oldTick = startTime;
	     final long newTick = manager.nanoTime();
	     final long age = newTick - oldTick;
	        if (age > TICK_INTERVAL) {
	            final long newIntervalStartTick = newTick - age % TICK_INTERVAL;
	            if (lastTick.compareAndSet(oldTick, newIntervalStartTick)) {
	                final long requiredTicks = age / TICK_INTERVAL;
	                for (long i = 0; i < requiredTicks; i++) {
	                    m1Rate.tick();
	                    m5Rate.tick();
	                    m15Rate.tick();
	                }
	            }
	        }
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
		tickIfNecessary();
		return m1Rate.getRate(TimeUnit.SECONDS);
		
		
	}
	
	
	@Override
	public double getFiveMinuteRate() {
		// TODO Auto-generated method stub
		tickIfNecessary();
		return m5Rate.getRate(TimeUnit.SECONDS);
	}
	
	@Override
	public double getFifteenMinuteRate() {
		tickIfNecessary();
        return m15Rate.getRate(TimeUnit.SECONDS);
	}
	
	@Override
	public synchronized MeterSample sample() {
		MeterSample sample = new MeterSample();
		
		sample.setCounter(counter);
		sample.setOneMinuteRate(getOneMinuteRate());
		sample.setFiveMinuteRate(getFiveMinuteRate());
		sample.setFifteenMinuteRate(getFifteenMinuteRate());
		sample.setMeanRate(getMeanRate());
		sampleCommon(sample);
		return sample;
		
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
	/**
	 * Returns Simon basic information, Meter, counter, meanRate, lastMinuteRate.
	 *
	 * @return basic information, meter, count, meanRate, lastMinuteRate 
	 * @see AbstractSimon#toString()
	 */
	@Override
	public synchronized String toString() {
		/*re-define */
		return "Simon Meter: current Count=" + getCount()+
			", meanRate=" + getMeanRate() +
			", min=" + getOneMinuteRate()+
			super.toString();
	}
	

}
