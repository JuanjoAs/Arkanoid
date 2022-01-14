package VersionCombinada;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TresBolas extends Pildora {

	public TresBolas(int x, int y) {
		super(x, y);
		// Carga de los sprites de la explosión
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate0.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate1.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate2.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate3.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate4.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate5.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate6.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate7.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate8.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("Duplicate9.png"));
		this.setSpritesDeAnimacion(nuevosSprites);
		// Sprite actual
		this.spriteActual = this.getSpritesDeAnimacion().get(0);
		
	}
	
	public void colisionProducidaConOtroActor(Actor actorColisionado) {
		if(actorColisionado instanceof Nave) {
			this.eliminar();
			
			if(Arkanoid.getInstancia().getBola2()==null) {
				System.out.println("se incia la bola 2");
				Arkanoid.getInstancia().setBola2(new Bola(Color.decode("#f44253")));
				Arkanoid.getInstancia().getBola2().trayectoria=null;
				Arkanoid.getInstancia().getBola2().setX(Arkanoid.getInstancia().getNave().getX() + Arkanoid.getInstancia().getNave().getAncho() / 3);
				Arkanoid.getInstancia().getBola2().setY(Arkanoid.getInstancia().getNave().getY() - Arkanoid.getInstancia().getNave().getAlto() - 1);
				
				Arkanoid.getInstancia().getBola2().naveCambiaPosicion((Nave) actorColisionado);
				Arkanoid.getInstancia().agregarActor(Arkanoid.getInstancia().getBola2());
			}
			if(Arkanoid.getInstancia().getBola3()==null) {
				System.out.println("se incia la bola 3");
				Arkanoid.getInstancia().setBola3(new Bola(Color.decode("#f44253")){
					@Override
					public void naveCambiaPosicion (Nave nave) {
						// Si la velocidad de la bola todavía está a cero indica que no ha comenzado a moverse
						// Si la nave se mueve y la bola está quieta, la bola debe permanecer pegada a la nave.
						if (this.trayectoria == null) {
							// Cada vez que la nave se mueva y, por tanto, la pelota pegada a la nave, actualizo las coordenadas de
							// alta precisión para, a continuación, redondearlas en las coordenadas enteras
							this.coordenadas.x = nave.getX() + nave.getAncho() / 5;
							this.coordenadas.y = nave.getY() - this.getAlto() - 1;
							this.x = Math.round(this.coordenadas.x);
							this.y = Math.round(this.coordenadas.y);
						}
					}
				});
				
				Arkanoid.getInstancia().agregarActor(Arkanoid.getInstancia().getBola3());
			}
			
		}
		
	}

}
