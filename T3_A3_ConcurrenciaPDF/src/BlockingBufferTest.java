import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class BlockingBuffer implements Buffer
{
	private final ArrayBlockingQueue<Integer> buffer; 
	
	public BlockingBuffer()
	{
		buffer = new ArrayBlockingQueue<Integer>(1);	
	}
	
	public void blockingPut(int value) throws InterruptedException
	{
		buffer.put(value);
		System.out.printf("%s%2d\t%s%d%n", "Productor Escribe ", value,
				"Buffer celulas ocupadas: ", buffer.size());
	}
	public int blockingGet() throws InterruptedException
	{
		int readValue = buffer.take(); 
		System.out.printf("%s %2d\t%s%d%n", "Lectura de Consumo ",
				readValue, "Buffer celulas ocupadas: ", buffer.size());
		return readValue;
	}
	
}

public class BlockingBufferTest {

	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService executorService = Executors.newCachedThreadPool(); 
		
		Buffer sharedLocation = new BlockingBuffer(); 
		
		executorService.execute(new Producer(sharedLocation));
		executorService.execute(new Consumer(sharedLocation));
		
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES); 
	}

}
