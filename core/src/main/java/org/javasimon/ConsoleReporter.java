package org.javasimon;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;



public class ConsoleReporter extends ScheduledReporter {


	private final PrintStream output;
    private final Locale locale;
   
    private final DateFormat dateFormat;
	
	 public ConsoleReporter(Manager manager, PrintStream output,
			Locale locale, TimeZone timeZone, TimeUnit rateUnit,
			TimeUnit durationUnit) {
		 	super(manager, "console-reporter", rateUnit, durationUnit);
	        this.output = output;
	        this.locale = locale;
	        this.dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,
	                                                         DateFormat.MEDIUM,
	                                                         locale);
	        dateFormat.setTimeZone(timeZone);
	}


	public static Builder forManager(Manager manager) {
	        return new Builder(manager);
	 }
	 
	 
	 public static class Builder {
	        private final Manager manager;
	        private PrintStream output;
	        private Locale locale;
	        private TimeZone timeZone;
	        private TimeUnit rateUnit;
	        private TimeUnit durationUnit;
	 

	        private Builder(Manager manager) {
	            this.manager = manager;
	            this.output = System.out;
	            this.locale = Locale.getDefault();
	            this.timeZone = TimeZone.getDefault();
	            this.rateUnit = TimeUnit.SECONDS;
	            this.durationUnit = TimeUnit.MILLISECONDS;
	           
	        }

	        /**
	         * Write to the given {@link PrintStream}.
	         *
	         * @param output a {@link PrintStream} instance.
	         * @return {@code this}
	         */
	        public Builder outputTo(PrintStream output) {
	            this.output = output;
	            return this;
	        }

	        /**
	         * Format numbers for the given {@link Locale}.
	         *
	         * @param locale a {@link Locale}
	         * @return {@code this}
	         */
	        public Builder formattedFor(Locale locale) {
	            this.locale = locale;
	            return this;
	        }

	        /**
	         * Use the given {@link Clock} instance for the time.
	         *
	         * @param clock a {@link Clock} instance
	         * @return {@code this}
	         */
	      

	        /**
	         * Use the given {@link TimeZone} for the time.
	         *
	         * @param timeZone a {@link TimeZone}
	         * @return {@code this}
	         */
	        public Builder formattedFor(TimeZone timeZone) {
	            this.timeZone = timeZone;
	            return this;
	        }

	        /**
	         * Convert rates to the given time unit.
	         *
	         * @param rateUnit a unit of time
	         * @return {@code this}
	         */
	        public Builder convertRatesTo(TimeUnit rateUnit) {
	            this.rateUnit = rateUnit;
	            return this;
	        }

	        /**
	         * Convert durations to the given time unit.
	         *
	         * @param durationUnit a unit of time
	         * @return {@code this}
	         */
	        public Builder convertDurationsTo(TimeUnit durationUnit) {
	            this.durationUnit = durationUnit;
	            return this;
	        }

	       
	       

	        /**
	         * Builds a {@link ConsoleReporter} with the given properties.
	         *
	         * @return a {@link ConsoleReporter}
	         */
	        public ConsoleReporter build() {
	            return new ConsoleReporter(manager,
	                                       output,
	                                       locale,
	                                       timeZone,
	                                       rateUnit,
	                                       durationUnit
	                                  );
	        }
	    }

	 
	
	@Override
    public void report(Collection<Simon> simons){
		
		for(Simon simon: simons){
			if(simon instanceof Meter){
				System.out.println("--Meter--");
				System.out.println((Meter)simon);
			}
		}
	}

}
