package VersionCombinada;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LivePill extends Pildora {

	public LivePill(int x, int y) {
		super(x, y);
		// Carga de los sprites de la explosión
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser0.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser1.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser2.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser3.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser4.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser5.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser6.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser7.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser8.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Laser9.png"));
		this.setSpritesDeAnimacion(nuevosSprites);
		// Sprite actual
		this.spriteActual = this.getSpritesDeAnimacion().get(0);
		
	}
	
	public void colisionProducidaConOtroActor(Actor actorColisionado) {
		if(actorColisionado instanceof Nave) {
			this.eliminar();
			double x=Math.random();
			if(x<0.5 && Arkanoid.getInstancia().getVidas()<10) {
				if(Arkanoid.getInstancia().getVidas()<10) {
					Arkanoid.getInstancia().setVidas(Arkanoid.getInstancia().getVidas()+1);
				}
			}
			else {
				Arkanoid.getInstancia().setVidas(Arkanoid.getInstancia().getVidas()-1);
			}
		}
		
	}

}
