package com.takipi.tests.counters.implementations;

import java.util.concurrent.locks.StampedLock;

import com.takipi.tests.counters.Counter;

public class OptimisticStamped implements Counter {

	private StampedLock rwlock = new StampedLock();
	
	private long counter;
	private long success;

	private long total;
	
	
	public long getCounter()
	{
		long stamp = rwlock.tryOptimisticRead();
		
		total++;
		
		if (rwlock.validate(stamp))
		{
			success++;
			return counter;
		}
		
		return counter;
		
		
	}
	
	public void increment()
	{
		long stamp = rwlock.writeLock();
		
		try
		{	
			++counter;
		}
		finally
		{
			rwlock.unlockWrite(stamp);
		}
	}

	public long getSuccess() {
		return success;
	}
	

	public long getTotal() {
		return total;
	}
}
