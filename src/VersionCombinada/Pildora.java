package VersionCombinada;


public class Pildora extends Actor {
	boolean colisionado=false;
	public Pildora(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		setVelocidadDeCambioDeSprite(50);
		
	}
	

	/**
	 * Método que se llamará para cada actor, en cada refresco de pantalla del juego
	 */
	@Override
	public void act() {
		super.act();
		if (this.spriteActual.equals(this.spritesDeAnimacion.get(this.spritesDeAnimacion.size()-1))) {
			this.spriteActual = spritesDeAnimacion.get(0);
		}
		this.setY(this.getY()+5);
		if(this.getY()>=600||colisionado) {
			eliminar();
		}
	}


	/**
	 * @return the haColisionado
	 */
	public boolean isColisionado() {
		return colisionado;
	}


	
	/**
	 * @param haColisionado the haColisionado to set
	 */
	public void setColisionado(boolean colisionado) {
		this.colisionado = colisionado;
	}

	
	
	public void colisionProducidaConOtroActor(Actor actorColisionado) {
	}
	
}