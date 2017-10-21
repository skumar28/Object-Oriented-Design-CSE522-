
// HW2:  Outline of Producer-Consumer:
/**
 * skumar28
 */
import java.util.Random;

public class ProducerConsumer {
	public static void main(String[] args) {
		DropBox db = new DropBox(5);
		Producer p = new Producer(db);
		Consumer c = new Consumer(db);
		p.start();
		c.start();
	}
}

class Producer extends Thread {
	private DropBox db;

	public Producer(DropBox db) {
		this.db = db;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			db.put(i);
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (Exception e) {
			}
		}
	}
}

class Consumer extends Thread {
	private DropBox db;
	int value;

	public Consumer(DropBox db) {
		this.db = db;
	}

	public void run() {
		while (true) {
			value = db.get();
			try {
				Thread.sleep(new Random().nextInt(500));
			} catch (Exception e) {
			}
		}
	}
}

class DropBox {
	// to be filled in by you
	int data[];

	public DropBox(int i) {
		data = new int[i];
	}

	private int p = 0; // index in array where next value placed by producer
	private int g = 0; // index in array where next value taken by consumer
	private int count = 0; // no. of value can be taken by consumer

	public synchronized int get() {
		int ans = -1;
		try {
			while (empty())
				wait();
			ans = data[g];
			g =  g + 1;
			count = count - 1;
			if(g == 5) {
				g = 0;     //reset the value
			}
			System.out.println(" Got " + ans);			
			notify();
		} catch (Exception e) {			
		}
		return ans;
	}

	public synchronized void put(int d) {

		try {
			while (full())
				wait();
			data[p] = d;
			p = p + 1;		//reset the value	
			count = count + 1;
			if(p == 5) {
				p = 0;
			}
			System.out.println(" Put " + d);
			
			notify();
		} catch (Exception e) {			
		}
	}

	boolean full() {
		if (count == 5) {			
			return true;
		} else
			return false;
	}

	boolean empty() {
		if (count == 0) {			
			return true;
		} else
			return false;
	}
}
