/**
 * skumar28
 */
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Readers_Writers_Semaphores {

	public static void main(String[] args) {

		Database d = new Database();

		Writer w1 = new Writer(d, 1);
		Writer w2 = new Writer(d, 10);
		Writer w3 = new Writer(d, 100);
		Writer w4 = new Writer(d, 1000);
		Writer w5 = new Writer(d, 10000);
		Reader r1 = new Reader(d);
		Reader r2 = new Reader(d);
		Reader r3 = new Reader(d);
		Reader r4 = new Reader(d);
		Reader r5 = new Reader(d);

		w1.start();
		r1.start();
		r2.start();
		w2.start();
		r3.start();
		r4.start();
		w3.start();
		r5.start();
		w4.start();
		w5.start();

	}
}

class Database {
	Semaphore mutex = new Semaphore(1);
	Semaphore writer = new Semaphore(0);
	Semaphore reader = new Semaphore(0);

	int data = 0;
	int r = 0; // # active readers
	int w = 0; // # active writers (0 or 1)
	int ww = 0; // # waiting writers
	int wr = 0; // # waiting readers

	public void request_read() {
		try {
			mutex.acquire();				
			while (w == 1 || ww > 0) {	
				wr++; // increase the waiting reader counter first
				mutex.release();	
				//acquire lock for reader
				reader.acquire();				
				
				mutex.acquire();
				wr--;// decrease the reading counter as it's start reading
			} 		
			r++;
			mutex.release();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void done_read() {
		try {
			mutex.acquire();
			r--;
			mutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// if there is active waiting writer then release writer
		if (ww > 0) {
			while (writer.hasQueuedThreads()) {
				writer.release();
			}
		} else {
			while (reader.hasQueuedThreads()) {
				reader.release();
			}
		}
	}

	public void request_write() {
		try {
			mutex.acquire();			
			while (r > 0 || w == 1) {	
				ww++; // Increase the waiting writer count 
				mutex.release();
				//acquire lock for writer
				writer.acquire();			
				
				mutex.acquire();
				ww--; // decrease the waiting writer count
			}
			
			w = 1;
			mutex.release();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void done_write() {
		try {
			mutex.acquire();
			w = 0;
			mutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if there is any active writer then release writers first
		if (ww > 0) {
			while (writer.hasQueuedThreads()) {
				writer.release();
			}
		} else {
			while (reader.hasQueuedThreads()) {
				reader.release();
			}

		}
	}

	int read() {
		return data;
	}

	void write(int x) {
		data = data + x;
	}
}

class Reader extends Thread {
	Database d;

	public Reader(Database d) {
		this.d = d;
	}

	public void run() {

		for (int i = 0; i < 5; i++) {
			d.request_read();
			System.out.println(d.read());
			try {
				Thread.sleep(new Random().nextInt(25));
			} catch (Exception e) {
			}
			d.done_read();

		}
	}
}

class Writer extends Thread {
	Database d;
	int x;

	public Writer(Database d, int x) {
		this.d = d;
		this.x = x;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			d.request_write();
			d.write(x);
			try {
				Thread.sleep(new Random().nextInt(25));
			} catch (Exception e) {
			}
			d.done_write();
		}
	}
}
