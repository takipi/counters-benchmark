package com.takipi.tests.counters;

public class Reader implements Runnable
{
	private final Counter counter;
	
	public Reader(Counter counter)
	{
		this.counter = counter;
	}
	
	public void run()
	{
		while (true)
		{
			if (Thread.interrupted())
			{
				break;
			}
			
			long count = counter.getCounter();
			
			if (count > Main.TARGET_NUMBER)
			{
				Main.publish(System.currentTimeMillis());
				break;
			}
		}
	}
}
