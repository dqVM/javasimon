package org.javasimon;

import org.javasimon.utils.SimonUtils;

/**
* Object holds all relevant data from {@link Meter} Simon. Whenever it is important to get more values
* in a synchronous manner, {@link org.javasimon.Meter#sample()} (or {@link Stopwatch#sampleIncrement(Object)}
* should be used to obtain this Java Bean object.
*
* @author <a href="mailto:virgo47@gmail.com">Richard "Virgo" Richter</a>
*/

public class MeterSample  extends Sample {

	 private double fifteenMinuteRate;
	 private double fiveMinuteRate;
	 private double oneMinuteRate;
	 private double meanRate;
	 private double counter;
	
	 @Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("CounterSample{");
			if (getName() != null) {
				sb.append("name=").append(getName()).append(", ");
			}
			sb.append("count=").append(counter);
			sb.append("meanRate=").append(meanRate);
			sb.append("oneMinuteRate=").append(oneMinuteRate);
			sb.append("fiveMinuteRate=").append(fifteenMinuteRate);
			toStringCommon(sb);
			return sb.toString();
		}
	 

	@Override
	public String simonToString() {
		return "Simon Meter: current Count=" + this.counter+
				", meanRate=" + this.meanRate +
				simonToStringCommon();
	}


	public double getFifteenMinuteRate() {
		return fifteenMinuteRate;
	}


	public void setFifteenMinuteRate(double fifteenMinuteRate) {
		this.fifteenMinuteRate = fifteenMinuteRate;
	}


	public double getFiveMinuteRate() {
		return fiveMinuteRate;
	}


	public void setFiveMinuteRate(double fiveMinuteRate) {
		this.fiveMinuteRate = fiveMinuteRate;
	}


	public double getOneMinuteRate() {
		return oneMinuteRate;
	}


	public void setOneMinuteRate(double oneMinuteRate) {
		this.oneMinuteRate = oneMinuteRate;
	}


	public double getMeanRate() {
		return meanRate;
	}


	public void setMeanRate(double meanRate) {
		this.meanRate = meanRate;
	}


	public double getCounter() {
		return counter;
	}


	public void setCounter(double counter) {
		this.counter = counter;
	}


	

}
