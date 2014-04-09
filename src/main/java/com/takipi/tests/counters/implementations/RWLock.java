package com.takipi.tests.counters.implementations;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.takipi.tests.counters.Counter;

public class RWLock implements Counter
{
	private ReadWriteLock rwlock = new ReentrantReadWriteLock();
	
	private Lock rlock = rwlock.readLock();
	private Lock wlock = rwlock.writeLock();
	
	private long counter;
	
	public long getCounter()
	{
		try
		{
			rlock.lock();		
			return counter;
		}
		finally
		{
			rlock.unlock();
		}
	}
	
	public void increment()
	{
		try
		{
			wlock.lock();		
			++counter;
		}
		finally
		{
			wlock.unlock();
		}
	}
}
