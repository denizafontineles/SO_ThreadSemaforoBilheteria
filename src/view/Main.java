package view;
import java.util.concurrent.Semaphore;

import controller.ThreadSemaforoBilheteria;

public class Main {
	
	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);
		for(int op = 0; op < 300; op++) {
			Thread threadSemaforoBilheteria = new ThreadSemaforoBilheteria(op, semaforo);
			threadSemaforoBilheteria.start();
		}
	}
}
