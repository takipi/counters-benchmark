package com.takipi.tests.counters;

public class Writer implements Runnable
{
	private final Counter counter;
	
	public Writer(Counter counter)
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
			
			counter.increment();
		}
	}
}
