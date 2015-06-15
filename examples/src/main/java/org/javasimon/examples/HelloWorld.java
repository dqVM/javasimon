package org.javasimon.examples;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.javasimon.Meter;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;

/**
 * HelloWorld is the most basic example of Stopwatch usage. You can show this
 * even to managers - it's that easy. :-) Hello world line shows that stopwatch
 * doesn't contain any results yet, these are added after the split is stopped.
 * <p/>
 * You can experiment with this example, try to put start/stop into the loop and
 * check total time, or whatever.
 *
 * @author <a href="mailto:virgo47@gmail.com">Richard "Virgo" Richter</a>
 * @since 1.0
 */
public final class HelloWorld {

	private HelloWorld() {
	}

	/**
	 * Entry point of the demo application.
	 *
	 * @param args command line arguments
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
//		Stopwatch stopwatch = SimonManager.getStopwatch("org.javasimon.examples.HelloWorld-Meter");
//		System.out.println("Hello World");
//
//		Split split = stopwatch.start();
//		System.out.println("Hello world, " + stopwatch);
//		split.stop();
		// Manager manager = new EnabledManager();
		
		 FileReader reader = new FileReader("/Users/xiaod/git/javasimon/examples/Test/my-config.xml");
		 SimonManager.configuration().readConfig(reader);
		 reader.close();
		
		
		Meter meter=SimonManager.getMeter("org.javasimon.examples.HelloWorld-Meter");
		
		System.out.println("Hello World"+meter);
		
		for(int i=0;i<10;i++){
			meter.mark();
			Thread.sleep(2000);
		}
		
		
		
		//System.out.println("Hello World End,"+meter);
		
	
		
	}
}
