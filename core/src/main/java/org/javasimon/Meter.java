package org.javasimon;

//import org.mockito.exceptions.Reporter;

public interface Meter extends Simon {
	Meter mark();
	Meter mark(long inc);
	
	void start();
	long getCount();
	double getFifteenMinuteRate();
	double getFiveMinuteRate();
	double getMeanRate();
	double getOneMinuteRate();
	double getPeakRate();
	
	
	@Override
	MeterSample sample();
	MeterSample sampleIncrement(Object key);
	MeterSample sampleIncrementNoReset(Object key);
}
