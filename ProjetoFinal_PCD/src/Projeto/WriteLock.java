package Projeto;

public class WriteLock {

	private int lock;

	public WriteLock() {
		lock = 0;
	}

	public synchronized boolean tryLock(){
		while(lock == 1){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock=1;
		return true;
	}
	
	public synchronized void unlock(){
		lock =0;
		notifyAll();
	}

}
