package VersionCombinada;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a cada ladrillo de los que pondremos sobre la pantalla
 * @author R
 *
 */
public class Ladrillo extends Actor {
	// Damos un ancho y un alto específico al ladrillo. Suponemos que todos los ladrillos serán iguales
	public static final int ANCHO = 48;
	public static final int ALTO = 24;
	public static final int ESPACIO_ENTRE_LADRILLOS = 2;
	private int codigoImagen;
	private boolean dosVidas=false;
	
	/**
	 * Constructor
	 */
	public Ladrillo() {
		super();
		
		this.x = 10;
		this.y = 10;
		this.ancho = ANCHO;
		this.alto = ALTO;
		
	}

	/**
	 * Constructor parametrizado
	 * @param x
	 * @param y
	 * @param color
	 */
	public Ladrillo(int x, int y,int imagen) {
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("GoldWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("SilverWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("WhiteWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("RedWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("PinkWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("LightBlueWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("GreenWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("YellowWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("OrangeWall.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("BlueWall.png"));
		this.x = x;
		this.y = y;
		this.ancho = ANCHO;
		this.alto = ALTO;
		this.spriteActual = nuevosSprites.get(imagen);
		this.codigoImagen=imagen;
		if(codigoImagen==1) {
			dosVidas=true;
		}
		
	}
	

	
	/**
	 * Colisión detectada
	 */
	@Override
	public void colisionProducidaConOtroActor(Actor actorColisionado) {
	if(this.codigoImagen!=0){
			super.colisionProducidaConOtroActor(actorColisionado);
			if(this.codigoImagen==1) {
				if(this.dosVidas==true) {
					CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-05.wav");
					this.dosVidas=false;
					return;
				}
			}
			// Si un ladrillo detecta una colisión con un objeto de tipo "Bola", debe desaparecer
			if (actorColisionado instanceof Bola) {
				eliminar();
				// Creo un nuevo actor de tipo Explosion y lo centró respecto a la posición del ladrillo
				Explosion explosion = new Explosion(this.getX(), this.getY());
				explosion.setX(this.x + Ladrillo.ANCHO / 2 - explosion.getAncho() / 2);
				Arkanoid.getInstancia().agregarActor(explosion);
				// Reproduzco el sonido de la explisión
				CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-01.wav");
				
				
				double x=Math.random();
				if(true) {
					if(x>0.66) {
						SlowPill pill = new SlowPill(this.getX(), this.getY());
						pill.setX(this.x + Ladrillo.ANCHO / 2 - pill.getAncho() / 2);
						Arkanoid.getInstancia().agregarActor(pill);
					}
					else if(x>0.33) {
						LivePill pill2 = new LivePill(this.getX(), this.getY());
						pill2.setX(this.x + Ladrillo.ANCHO / 2 - pill2.getAncho() / 2);
						Arkanoid.getInstancia().agregarActor(pill2);
					}
					else {
						TresBolas pill3 = new TresBolas(this.getX(), this.getY());
						pill3.setX(this.x + Ladrillo.ANCHO / 2 - pill3.getAncho() / 2);
						Arkanoid.getInstancia().agregarActor(pill3);
					}
					
				}
				return;
			}
		}
	CacheRecursos.getInstancia().playSonido("Arkanoid-SFX-05.wav");
	}

}
