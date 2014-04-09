package com.takipi.tests.counters.implementations;

import com.takipi.tests.counters.Counter;

public class Dirty implements Counter
{
	private long counter;
	
	public long getCounter()
	{
		return counter;
	}
	
	public void increment() 
	{
		++counter;
	}
}
