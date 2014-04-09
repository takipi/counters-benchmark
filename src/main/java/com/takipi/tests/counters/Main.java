package com.takipi.tests.counters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.takipi.tests.counters.implementations.Adder;
import com.takipi.tests.counters.implementations.Atomic;
import com.takipi.tests.counters.implementations.Dirty;
import com.takipi.tests.counters.implementations.RWLock;
import com.takipi.tests.counters.implementations.Synchronized;
import com.takipi.tests.counters.implementations.Volatile;

public class Main
{
	public static long TARGET_NUMBER 	= 100000000l;
	public static int THREADS 			= 10;
	public static int ROUNDS 			= 10;
	private static String COUNTER 		= Counters.DIRTY.toString();
	
	private static ExecutorService es;
	
	private static int round;
	private static long start;
	
	private static Boolean[] rounds;
	
	private static enum Counters
	{
		DIRTY,
		VOLATILE,
		SYNCHRONIZED,
		RWLOCK,
		ATOMIC,
		ADDER
	}
	
	public static void main(String[] args)
	{
		COUNTER = Counters.DIRTY.toString();
		
		if (args.length > 0)
		{
			COUNTER = args[0];
		}
		
		if (args.length > 1)
		{
			THREADS = Integer.valueOf(args[1]);
		}
		
		if (args.length > 2)
		{
			ROUNDS = Integer.valueOf(args[2]);
		}
		
		if (args.length > 3)
		{
			TARGET_NUMBER = Long.valueOf(args[3]);
		}
		
		rounds = new Boolean[ROUNDS];
		
		System.out.println("Using " + COUNTER + ". threads: " + THREADS + ". rounds: " + ROUNDS +
				". Target: " + TARGET_NUMBER);
		
		for (round = 0; round < ROUNDS; round++)
		{
			rounds[round] = Boolean.FALSE;
			
			Counter counter = getCounter();
			
			es = Executors.newFixedThreadPool(THREADS);
			
			start = System.currentTimeMillis();
			
			for (int j = 0; j < THREADS; j+=2)
			{	
				es.execute(new Reader(counter));
				es.execute(new Writer(counter));
			}
			
			try
			{
				es.awaitTermination(10, TimeUnit.MINUTES);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static Counter getCounter()
	{
		Counters counterTypeEnum = Counters.valueOf(COUNTER);
		
		switch (counterTypeEnum)
		{
			case ADDER:
				return new Adder();
			case ATOMIC:
				return new Atomic();
			case DIRTY:
				return new Dirty();
			case RWLOCK:
				return new RWLock();
			case SYNCHRONIZED:
				return new Synchronized();
			case VOLATILE:
				return new Volatile();
		}
		
		return null;
	}
	
	public static void publish(long end)
	{
		synchronized (rounds[round])
		{
			if (rounds[round] == Boolean.FALSE)
			{
				System.out.println(end-start);
				
				rounds[round] = Boolean.TRUE;
				
				es.shutdownNow();
			}
		}
	}
}
