import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
/** * 
 * @author Skumar28
 *
 */
public class Elevator_Scheduler {

	public static void main(String[] args) {

		Elevator E = new Elevator();

		Scheduler S = new Scheduler(E, 6);

		E.S = S; // added this line for state maintenance

		Person p1 = new Person("A", S);
		Person p2 = new Person("B", S);
		Person p3 = new Person("C", S);
		Person p4 = new Person("D", S);
		Person p5 = new Person("E", S);
		Person p6 = new Person("F", S);

		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		p6.start();

		S.start();

	}
}

class Elevator {

	int current_floor;
	String direction;

	Scheduler S; // added this line for state maintenance

	String state; // for query-based debugging

	public Elevator() {
		current_floor = 0;
		direction = "up";
	}

	synchronized void go(int floor, String dir) {
		String up = S.up.toString();
		String down = S.down.toString();

		if (direction != dir || current_floor != floor)
			state = current_floor + "-" + direction + "-" + up + "-" + down;

		if (direction.compareTo(dir) != 0)
			direction = dir;
		if (current_floor != floor)
			current_floor = floor;

		// System.out.println("State: " + state);
	}
}

class Person extends Thread {
	Scheduler S;
	String name;
	int from = 0;
	int to = 0;

	public Person(String name, Scheduler S) {
		this.name = name;
		this.S = S;
	}

	public void run() {
		try {
			while (true) {
				if (to > from)
					S.up(name, from, to);
				if (from > to)
					S.down(name, from, to);
				from = to;
				to = (from + new Random().nextInt(97)) % S.max;
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
}

class Scheduler extends Thread {

	int max;
	int current_floor;
	String direction;
	Elevator elevator;

	SortedSet<Integer> up = new TreeSet<Integer>();
	SortedSet<Integer> down = new TreeSet<Integer>();

	public Scheduler(Elevator e, int max_floors) {
		elevator = e;
		this.max = max_floors;
		current_floor = 0;
		direction = "up";

	}

	public void run() {
		while (true) {
			schedule();
		}
	}

	synchronized void up(String name, int from, int to) {
		// name can be used for printing, if desired
		try {
			up.add(from); // press up button at floor 'from'
			notifyAll();

			while (current_floor != from)
				wait();

			up.add(to); // get into elevator and press up button to 'to'
			notifyAll();

			while (current_floor != to)
				wait();
			// reach floor 'to'
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

	}

	synchronized void down(String name, int from, int to) {
		// name can be used for printing
		try {
			down.add(-from); // press down button at floor 'from'
			notifyAll();

			while (current_floor != from)
				wait();

			down.add(-to); // get into elevator and press down button to 'to'
			notifyAll();

			while (current_floor != to)
				wait();
			// reach floor 'to'
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	synchronized void schedule() {

		try {

			while (up.isEmpty() && down.isEmpty())
				wait();

			Log.print_queues("Floor " + current_floor + ", direction = " + direction + ", ", up, down);

			if (current_floor == (max - 1))
				direction = "down";

			if (current_floor == 0)
				direction = "up";

			// direction up
			if (direction == "up") {
				//if up list is empty at any time then go to else part where we are checking the 
				//status of down list and setting the direction and current floor accordingly
				if (!up.isEmpty()) {
					Integer firstValue = up.first();
					//This is for case in which if multiple thread press same button at same floor 
					//then accommodate them accordingly.
					if (firstValue != null && firstValue == current_floor) {
						up.remove(firstValue);
					} else {
						//Get the next floor information using higher method
						Integer nf = ((TreeSet<Integer>) up).higher(current_floor);

						//If there is no next floor then change the direction according to the 
						//status of down list
						if (nf != null) {
							elevator.go(nf, direction);
							up.remove(nf);
							current_floor = nf;
						} else {
							if (!down.isEmpty()) {
								int fDown = down.first();
								if (-fDown > current_floor) {
									elevator.go(-fDown, direction);
									current_floor = -fDown;
								} else {
									direction = "down";
								}
							}
						}
					}
				} else {
					//System.out.println("Up list is empty");

					if (!down.isEmpty()) {
						int fDown = down.first();
						if (-fDown > current_floor) {
							elevator.go(-fDown, direction);
							current_floor = -fDown;
						} else {
							direction = "down";
						}
					}

				}
				notifyAll();

			} // direction down, similar logic as for up direction
			else {
				if (!down.isEmpty()) {
					Integer firstValue = down.first();
					if (firstValue != null && -firstValue == current_floor) {
						down.remove(firstValue);
					} else {
						Integer nf = ((TreeSet<Integer>) down).higher(-current_floor);

						if (nf != null) {
							elevator.go(-nf, direction);
							down.remove(nf);
							current_floor = -nf;
						} else {
							if (!up.isEmpty()) {
								int fUp = up.first();
								if (fUp < current_floor) {
									elevator.go(fUp, direction);
									current_floor = fUp;
								} else {
									direction = "up";
								}
							}
						}
					}
				} else {
					// down list is empty
					//System.out.println("Down list is empty");

					if (!up.isEmpty()) {
						int fUp = up.first();
						if (fUp < current_floor) {
							elevator.go(fUp, direction);
							current_floor = fUp;
						} else {
							direction = "up";
						}
					}

				}
				notifyAll();
			}

			Log.print_queues("Floor = " + current_floor + ", direction = " + direction + ", ", up, down);

			notifyAll();
		}

		catch (Exception e) {

			System.out.println(e.getStackTrace() + ": message: " + e.getMessage());
		}
	}
}

// For logging output

class Log {

	static synchronized void print_queues(String action, SortedSet<Integer> up, SortedSet<Integer> down) {

		System.out.print(action + "up:[ ");

		for (int f : up)
			System.out.print(f + " ");

		System.out.print("] down:[ ");

		for (int f : down)
			System.out.print((0 - f) + " ");

		System.out.println("]");
	}

}
