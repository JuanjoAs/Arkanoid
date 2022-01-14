package VersionCombinada;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;



public class AnimacionComienzo extends Actor {
	boolean introPulsado=false;
	private int puntoDeInicio;

	public AnimacionComienzo(List<BufferedImage> nuevosSprites,int puntoDeInicio) {
		super();
		this.x = 0;
		this.y = 0;
		// Carga de los sprites de la explosión
		
		this.setSpritesDeAnimacion(nuevosSprites);
		// Sprite actual
		this.spriteActual = this.getSpritesDeAnimacion().get(0);
		// Velocidad de cambio de sprite
		this.velocidadDeCambioDeSprite = 50;
		this.puntoDeInicio=puntoDeInicio;
	}
	




	@Override
	public void act() {
		
		
		// En el caso de que exista un array de sprites el actor actual se tratará de una animación, para eso llevaremos a cabo los siguientes pasos
				if (this.spritesDeAnimacion != null && this.spritesDeAnimacion.size() > 0) {
					unidadDeTiempo++;
					
					if (unidadDeTiempo % velocidadDeCambioDeSprite == 0){
						unidadDeTiempo = 0;
						int indiceSpriteActual = spritesDeAnimacion.indexOf(this.spriteActual);
						int indiceSiguienteSprite = (indiceSpriteActual + 1) % spritesDeAnimacion.size();
						this.spriteActual = spritesDeAnimacion.get(indiceSiguienteSprite);
					}
				}
		if (this.spriteActual.equals(this.spritesDeAnimacion.get(this.spritesDeAnimacion.size()-1))) {
			this.eliminar();
		}
	}






	public void eliminar() {
		Arkanoid.getInstancia().setAnimacionTerminada(true);
	}





	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER&&introPulsado==false) {
			cambioDeImagen();
			introPulsado=true;
	  	}
		if(e.getKeyCode() == KeyEvent.VK_5) {
			if(Arkanoid.getInstancia().vidas<5) {
				Arkanoid.getInstancia().vidas++;
				return;
			}
			
		}
	}





	private void cambioDeImagen() {
		if(this.puntoDeInicio>0) {
			this.spriteActual = this.getSpritesDeAnimacion().get(puntoDeInicio);
		}
	}





	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER&&introPulsado==false) {
			cambioDeImagen();
			introPulsado=true;
	  	}
	}
	

}
