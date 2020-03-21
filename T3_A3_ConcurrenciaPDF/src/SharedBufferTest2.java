import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SynchronizedBuffer implements Buffer {
	private int buffer = -1; 
	private boolean occupied = false; 
	
	public synchronized void blockingPut (int value) 
		throws InterruptedException
		{
		while (occupied)
		{
			System.out.println("Producer tries to write");
			displayState("Buffer full. Producer Waits");
			wait();
		}
		buffer = value; 
		
		occupied = true; 
		
		displayState("Producer writes " + buffer);
		
		notifyAll();
		}
	public synchronized int blockingGet() throws InterruptedException
	{
		while(!occupied)
		{
		System.out.println("Consumer tries to read.");	
		displayState("Buffer empty. Consumer waits");
		wait();
		}
		occupied = false; 
		
		displayState("Consumer reads " + buffer); 
		
		notifyAll();
		return buffer; 	
	}	
	private synchronized void displayState(String operation)
	{
		System.out.printf("%-40s%d\t\t%b%n%n", operation, buffer,
				occupied);
	}
}
public class SharedBufferTest2 {
	public static void main(String[] args) throws InterruptedException {
	ExecutorService executorService = Executors.newCachedThreadPool();
	Buffer sharedLocalitation = new SynchronizedBuffer(); 
	System.out.printf("%-40s%s\t\t%s%n%-40s%s%n%n", "Operation",
			"Buffer", "Occupied", "--------", "------\t\t------");
	executorService.execute(new Producer(sharedLocalitation));
	executorService.execute(new Consumer(sharedLocalitation));	
	executorService.shutdown();
	executorService.awaitTermination(1, TimeUnit.MINUTES);
	}
}