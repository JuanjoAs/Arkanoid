package VersionCombinada;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Explosion extends Actor {
	/**
	 * Constructor
	 */
	public Explosion(int x, int y) {
		super();
		CacheRecursos.getInstancia().playSonido("explosion.wav");
		this.x = x;
		this.y = y;
		// Carga de los sprites de la explosión
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp1.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp2.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp3.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp4.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp5.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp6.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp7.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp8.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp9.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp10.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("exp11.png"));
		this.setSpritesDeAnimacion(nuevosSprites);
		// Sprite actual
		this.spriteActual = this.getSpritesDeAnimacion().get(0);
		// Velocidad de cambio de sprite
		this.velocidadDeCambioDeSprite = 5;
	}
	

	/**
	 * Método que se llamará para cada actor, en cada refresco de pantalla del juego
	 */
	@Override
	public void act() {
		super.act();
		if (this.spriteActual.equals(this.spritesDeAnimacion.get(this.spritesDeAnimacion.size()-1))) {
			this.eliminar();
		}
	}

}
