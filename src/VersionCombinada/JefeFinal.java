package VersionCombinada;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;





/**
 * Clase que representa a cada ladrillo de los que pondremos sobre la pantalla
 * @author R
 *
 */
public class JefeFinal extends Actor {
	// Damos un ancho y un alto específico al ladrillo. Suponemos que todos los ladrillos serán iguales
	public static final int ANCHO = 62;
	public static final int ALTO = 96;
	public static final int ESPACIO_ENTRE_LADRILLOS = 2;
	protected int vX=2;
	protected int vY=2;
	private boolean dosVidas=true;
	private boolean tresVidas=true;
	private boolean cuatroVidas=true;
	private boolean cincoVidas=true;
	List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
	
	
	/**
	 * Constructor
	 */
	public JefeFinal() {
		super();
		this.x = 10;
		this.y = 10;
		this.ancho = ANCHO;
		this.alto = ALTO;
		
	}
	public void fire() {
	   	Bullet b = new Bullet();
	   	b.setX(b.ancho/2+x);
	   	b.setY(y - b.alto);
	   	Arkanoid.getInstancia().agregarActor(b);
	   	CacheRecursos.getInstancia().playSonido("missile.wav");
	 }

	public void act() {
		super.act();
		x += vX;
		y += vY;
		if (x < 0 || x > (Arkanoid.ANCHO - 62)) vX = -vX;
		if (y < 0 || y > (Arkanoid.ALTO-400)) vY = -vY;
		
		unidadDeTiempo++;
		if (unidadDeTiempo % velocidadDeCambioDeSprite == 0){
			unidadDeTiempo = 0;
			fire();
		}
	}
	/**
	 * Constructor parametrizado
	 * @param x
	 * @param y
	 * @param color
	 */
	public JefeFinal(int x, int y) {
		
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("jefe.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("jefe1.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("jefe3.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("jefe4.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("jefe5.png"));
		this.x = x;
		this.y = y;
		this.ancho = ANCHO;
		this.alto = ALTO;
		this.spriteActual = nuevosSprites.get(0);
		this.velocidadDeCambioDeSprite = 50;
		
	}
	

	
	/**
	 * Colisión detectada
	 */
	@Override
	public void colisionProducidaConOtroActor(Actor actorColisionado) {
		super.colisionProducidaConOtroActor(actorColisionado);
		if (actorColisionado instanceof Bola) {
		if(this.cincoVidas==true) {
			CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-05.wav");
			this.cincoVidas=false;
			this.spriteActual = nuevosSprites.get(1);
			return;
		}
		if(this.cuatroVidas==true) {
			CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-05.wav");
			this.cuatroVidas=false;
			this.spriteActual = nuevosSprites.get(2);
			return;
		}
		if(this.tresVidas==true) {
			CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-05.wav");
			this.tresVidas=false;
			this.spriteActual = nuevosSprites.get(3);
			return;
		}
		if(this.dosVidas==true) {
			CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-05.wav");
			this.dosVidas=false;
			this.spriteActual = nuevosSprites.get(4);
			return;
		}
		
		// Si un ladrillo detecta una colisión con un objeto de tipo "Bola", debe desaparecer
		
			eliminar();
			CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-01.wav");
			Arkanoid.getInstancia().finalDelJuego();
			//Arkanoid.getInstancia().finalDelJuegoChetado();
			return;
		}
		
	CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-05.wav");
	}

}
