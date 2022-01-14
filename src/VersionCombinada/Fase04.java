package VersionCombinada;

public class Fase04 extends Fase {

	@Override
	public void inicializaFase() {
		
		// Inicializamos los ladrillos
		JefeFinal m = new JefeFinal(4* 48, 4 * 24);
		actores.add(m);
		numLadrillos++;
		
	}

}
