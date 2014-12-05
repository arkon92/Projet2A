package Client;


public class CacheTabBytes {
	private byte[] tabBytes;
	private boolean cache;
	
	public CacheTabBytes() {
		this.cache = false;
	}

	synchronized public void setTab( byte[] t) {
		if (this.cache) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.tabBytes = t;
		this.cache = true;
		notify();
	}
	synchronized byte[] getTab() {
		if ( !this.cache ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.cache = false;
		notify();
		return this.tabBytes;
	}
	
	
}
