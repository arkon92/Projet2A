package Client;


public class ClientSIWY {

	private Conecteur conecteur;
	private Camera camera;
	private CacheMatImage cacheMat;
	private TMathBytes tMathBytes;
	private CacheTabBytes cacheBytes;
	private Envoyeur envoyeur;

	public ClientSIWY(String adresse, int numPort) {
		this.conecteur = new Conecteur(adresse, numPort);
		this.cacheMat = new CacheMatImage();
		this.cacheBytes = new CacheTabBytes();
		this.camera = new Camera(0, this.cacheMat);
		this.tMathBytes = new TMathBytes(this.cacheMat, this.cacheBytes);
		this.envoyeur = new Envoyeur( this.cacheBytes, this.conecteur.getSocket());
	}
	
	public void close() {
		this.camera.close();
		this.tMathBytes.close();
		this.envoyeur.close();
	}

}
