package VersionCombinada;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Bullet extends Actor {
	protected static final int BULLET_SPEED=1;
	public static final int ANCHO = 14;
	public static final int ALTO = 19;
	public Bullet() {
		super();
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("disparo.png"));
		this.spriteActual=nuevosSprites.get(0);
		this.ancho = ANCHO;
		this.alto = ALTO;
	}
	
	public void act() {
		super.act();
		y+=BULLET_SPEED;
		if (y > Arkanoid.ALTO)
		  eliminar();
	}
	public void colisionProducidaConOtroActor(Actor actorColisionado) {
		if(actorColisionado instanceof Nave) {
			Arkanoid.getInstancia().setVidas(Arkanoid.getInstancia().getVidas()-1);
			eliminar();
		}
	}
}
