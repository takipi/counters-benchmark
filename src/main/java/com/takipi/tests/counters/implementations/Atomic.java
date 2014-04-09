package com.takipi.tests.counters.implementations;

import java.util.concurrent.atomic.AtomicLong;

import com.takipi.tests.counters.Counter;

public class Atomic implements Counter
{
	private final AtomicLong atomic = new AtomicLong();
	
	public long getCounter()
	{
		return atomic.get();
	}
	
	public void increment()
	{
		atomic.incrementAndGet();
	}
}
