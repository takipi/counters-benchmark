package com.takipi.tests.counters.implementations;

import java.util.concurrent.atomic.LongAdder;

import com.takipi.tests.counters.Counter;

public class Adder implements Counter
{
	private final LongAdder adder = new LongAdder();
	
	public long getCounter()
	{
		return adder.longValue();
	}
	
	public void increment()
	{
		adder.increment();
	}
}
