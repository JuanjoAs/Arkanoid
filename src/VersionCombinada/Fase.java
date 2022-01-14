package VersionCombinada;

import java.util.ArrayList;
import java.util.List;

/**
 * Todas las fases deberán extender esta clase abstracta y personalizar la apariencia y el comportamiento
 * @author R
 *
 */
public abstract class Fase {
	protected int numLadrillos=0;
	// Lista de actores a colocar en cualquier fase
	protected List<Actor> actores = new ArrayList<Actor>();
	
	// Método que debe implementar cada Fase
	public abstract void inicializaFase ();

	// Getter de la lista de actores
	public List<Actor> getActores() { return actores; }
	
	// Archivo de sonido del principio de la fase
	public String getNombreSonidoInicio () {
		return "Amusement_Park_8_bits.wav";
	}
}
