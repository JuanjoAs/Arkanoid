package VersionCombinada;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Clase que controla las pulsaciones de teclado y las trasladamos a la nave
 * @author R
 *
 */
public class ControladorTeclado extends KeyAdapter {

	/**
	 * Se activa este método cuando dejamos pulsada una tecla
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		super.keyPressed(event);
		Arkanoid.getInstancia().getNave().keyPressed(event);
		Arkanoid.getInstancia().getBola().keyPressed(event);
		Arkanoid.getInstancia().getAnimacion().keyPressed(event);
		if(Arkanoid.getInstancia().getBola2()!=null) {
			Arkanoid.getInstancia().getBola2().keyPressed(event);;
		}
		if(Arkanoid.getInstancia().getBola3()!=null) {
			Arkanoid.getInstancia().getBola3().keyPressed(event);
		}
	}

	/**
	 * Se activa este método cuando levantamos una tecla pulsada
	 */
	@Override
	public void keyReleased(KeyEvent event) {
		super.keyReleased(event);
		Arkanoid.getInstancia().getNave().keyReleased(event);
		Arkanoid.getInstancia().getAnimacion().keyReleased(event);
	}

}
