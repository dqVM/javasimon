package org.javasimon;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
//import java.util.logging.Logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledReporter implements Closeable, Reporter{
	
	 private static final Logger LOG = LoggerFactory.getLogger(ScheduledReporter.class);

		private final Manager manger;
	    private final ScheduledExecutorService executor;
	    private final double durationFactor;
	    private final String durationUnit;
	    private final double rateFactor;
	    private final String rateUnit;
	
	    
	    
	    @SuppressWarnings("NullableProblems")
	    private static class NamedThreadFactory implements ThreadFactory {
	        private final ThreadGroup group;
	        private final AtomicInteger threadNumber = new AtomicInteger(1);
	        private final String namePrefix;

	        private NamedThreadFactory(String name) {
	            final SecurityManager s = System.getSecurityManager();
	            this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
	            this.namePrefix = "metrics-" + name + "-thread-";
	        }

	        public Thread newThread(Runnable r) {
	            final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
	            t.setDaemon(true);
	            if (t.getPriority() != Thread.NORM_PRIORITY) {
	                t.setPriority(Thread.NORM_PRIORITY);
	            }
	            return t;
	        }
	    }
	    
	protected ScheduledReporter(Manager manager,String name,TimeUnit rateUnit,TimeUnit durationUnit) {
		
		this.manger=manager;
		this.executor=Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory(name));
		this.rateFactor = rateUnit.toSeconds(1);
        this.rateUnit = calculateRateUnit(rateUnit);
        this.durationFactor = 1.0 / durationUnit.toNanos(1);
        this.durationUnit = durationUnit.toString().toLowerCase(Locale.US);
		
	}
	
	private String calculateRateUnit(TimeUnit unit) {
		 final String s = unit.toString().toLowerCase(Locale.US);
	     return s.substring(0, s.length() - 1);
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		stop();
	}
  
	  /**
     * Starts the reporter polling at the given period.
     *
     * @param period the amount of time between polls
     * @param unit   the unit for {@code period}
     */
    public void start(long period, TimeUnit unit) {
    	
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    report();
                } catch (RuntimeException ex) {
                    ((org.slf4j.Logger) LOG).error("RuntimeException thrown from {}#report. Exception was suppressed.", ScheduledReporter.this.getClass().getSimpleName(), ex);
                }
            }
        }, period, period, unit);
    }
	
	
	public void stop(){
		
	}
	
	public void report(){
		synchronized (this) {
           report(manger.getSimons(null));
        }
	}
	
	public void report(Collection<Simon> simons){
		
	}
}
