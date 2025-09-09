package Projeto;

public class ReadLock {
	
	private int semaphore;
	
	public ReadLock(){
		semaphore=3;
	}
	
	
	public synchronized boolean tryAcquire(){
		while(semaphore == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		semaphore--;
		return true;
	}
	
	public synchronized void release(){
		semaphore++;
		notifyAll();
	}
}
