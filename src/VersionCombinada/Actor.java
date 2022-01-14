package VersionCombinada;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representará a cualquier elemento que queramos pintar sobre la pantalla
 * @author R
 *
 */
public class Actor {
	// Propiedades que contienen las coordenadas del actor y la imagen que corresponda con el mismo
	protected int x, y;
	protected int ancho, alto;
	protected BufferedImage spriteActual;
	protected boolean marcadoParaEliminacion = false;
	// Posibilidad de que el actor sea animado, a través del siguiente array de sprites y las variables
	// velocidadDeCambioDeSprite y unidadDeTiempo
	protected List<BufferedImage> spritesDeAnimacion = new ArrayList<BufferedImage>();
	protected int velocidadDeCambioDeSprite = 0;
	protected int unidadDeTiempo = 0;
	
	
	
	
	
	/**
	 * Método para pintar el actor en la pantalla
	 * @param g
	 */
	public void paint(Graphics2D g){
		// Cuidado, el sprite del actor puede ser nulo, de manera que el actor se pinte por gráficos vectoriales
		if (this.spriteActual != null) {
			g.drawImage(this.spriteActual, this.x, this.y, null);
		}
	}
	
	
	/**
	 * Método que se llamará para cada actor, en cada refresco de pantalla del juego
	 */
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
	}

	
	/**
	 * El método siguiente se llamará para cada actor cuando se detecte una colisión del actor con otro actor
	 * @param actorColisionado
	 */
	public void colisionProducidaConOtroActor(Actor actorColisionado) {
	}
	
	
	/**
	 * Con este método no eliminamos realmente el actor, en lugar de eso lo que hacemos es activar una bandera booleana
	 * para que, en la siguiente iteración principal del bucle, un proceso de "updateWorld" se encargue de eliminar a este actor
	 */
	public void eliminar () {
		this.marcadoParaEliminacion = true;
	}
	

	// Métodos setters y getters
	public int getX() {	return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	public int getAncho() { return ancho; }
	public void setAncho(int ancho) { this.ancho = ancho; }
	public int getAlto() { return alto; }
	public void setAlto(int alto) { this.alto = alto; }
	public BufferedImage getSpriteActual() { return spriteActual; }
	public void setSpriteActual(BufferedImage spriteActual) { this.spriteActual = spriteActual; }
	public List<BufferedImage> getSpritesDeAnimacion() { return spritesDeAnimacion; }
	public void setSpritesDeAnimacion(List<BufferedImage> spritesDeAnimacion) { 
		this.spritesDeAnimacion = spritesDeAnimacion;
		// Al darnos unos nuevos sprites de animación debo calcular el nuevo ancho y alto del actor, ajustándolo
		// al máximo de los nuevos sprites
		if (this.spritesDeAnimacion != null) {
			for (BufferedImage imagen : this.spritesDeAnimacion) {
				if (imagen.getWidth() > this.ancho) this.ancho = imagen.getWidth();
				if (imagen.getHeight() > this.alto) this.alto = imagen.getHeight();
			}
		}
	}
	public int getVelocidadDeCambioDeSprite() { return velocidadDeCambioDeSprite; }
	public void setVelocidadDeCambioDeSprite(int velocidadDeCambioDeSprite) { this.velocidadDeCambioDeSprite = velocidadDeCambioDeSprite; }
	
}
