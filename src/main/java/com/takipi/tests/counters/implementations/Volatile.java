package com.takipi.tests.counters.implementations;

import com.takipi.tests.counters.Counter;

public class Volatile implements Counter
{
	private volatile long counter;
	
	public long getCounter()
	{
		return counter;
	}
	
	public void increment() 
	{
		++counter;
	}
}