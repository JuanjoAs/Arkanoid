package VersionCombinada;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SlowPill extends Pildora {

	public SlowPill(int x, int y) {
		super(x, y);
		// Carga de los sprites de la explosión
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow0.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow1.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow2.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow3.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow4.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow5.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow6.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow7.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow8.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Slow9.png"));
		this.setSpritesDeAnimacion(nuevosSprites);
		// Sprite actual
		this.spriteActual = this.getSpritesDeAnimacion().get(0);
		
	}
	
	public void colisionProducidaConOtroActor(Actor actorColisionado) {
		if(actorColisionado instanceof Nave) {
			this.eliminar();
			if(Arkanoid.getInstancia().getBola().getVelocidadPorFrame()>3) {
				Arkanoid.getInstancia().getBola().setVelocidadPorFrame(Arkanoid.getInstancia().getBola().getVelocidadPorFrame()/2);
				if(Arkanoid.getInstancia().getBola().getVelocidadPorFrame()<3) {
					Arkanoid.getInstancia().getBola().setVelocidadPorFrame(3+(Arkanoid.getInstancia().getBola().getVelocidadPorFrame()/3));
				}
			}
			
		}
		
	}

}
