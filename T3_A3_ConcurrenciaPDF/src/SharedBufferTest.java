import java.security.SecureRandom;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
import java.util.concurrent.TimeUnit;
/*
En una relación productor / consumidor, la porción de productor de una aplicación genera
datos y los almacena en un objeto compartido, y la parte del consumidor de la aplicación 
lee datos del objeto compartido. La relación productor / consumidor separa la tarea de 
identificar el trabajo a realizar de las tareas involucradas en la realización del trabajo
*/

 interface Buffer{ 
	 // place int value into Buffer 6   
	 public void blockingPut(int value) throws InterruptedException; //SET
	  // return int value from Buffer 9   
	 public int blockingGet() throws InterruptedException; //GEtter
	 
	 } // end interface 

class Producer implements Runnable{

	private static final SecureRandom generator = new SecureRandom();
	
	private final Buffer sharedLocation; //reference to shared object 
	
	public Producer(Buffer sharedLocation) {
		this.sharedLocation = sharedLocation;
	}

	@Override
	public void run() {
		int sum =0;
		for (int count = 1;count <= 10; count++) {
			try {
				Thread.sleep(generator.nextInt(3000));
				sharedLocation.blockingPut(count);
				sum += count;             
				System.out.printf("\t%2d%n", sum);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.printf("Prodecer done producing%nTerminating Producer%n");
	}
	
}
class Consumer implements Runnable{
private static final SecureRandom generator = new SecureRandom();
	
	private final Buffer sharedLocation;
	
	public Consumer(Buffer sharedLocation) {
		this.sharedLocation = sharedLocation;
	}

	@Override
	public void run() {
		int sum =0;
		for (int count = 1;count <= 10; count++) {
			try {
				Thread.sleep(generator.nextInt(3000));
				sharedLocation.blockingPut(count);
				sum += count;             
				System.out.printf("\t%2d%n", sum);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.printf("%n%s %d%n%s%n","Consumer read values totaling", sum, "Terminating Consumer");
	}
	
}

 class UnsychronizedBuffer implements Buffer{
	private int buffer = -1;
	public void blockingPut(int value) throws InterruptedException{
		System.out.printf("Producer writes\t%2d", value);
		buffer= value;
	}
	public int blockingGet()throws InterruptedException{
		System.out.printf("Consumer reads \t%2d", buffer);
		return buffer;
	}
	
}
public class SharedBufferTest {
	public static void main(String[] args) throws InterruptedException{
	    ExecutorService executorService = Executors.newCachedThreadPool();
	    System.out.println("Action\t\tValue\tSum of Produced\tSum of Consumed");
	    
	    System.out.printf("------\t\t-----\t---------------\t---------------%n%n"); 
	        executorService.shutdown(); 
	        executorService.awaitTermination(1, TimeUnit.MINUTES); 
	        }
		} // end class SharedBufferTest Action   
		
