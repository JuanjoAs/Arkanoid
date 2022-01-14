package VersionCombinada;

public class Fase03 extends Fase {

	@Override
	public void inicializaFase() {
		int mapa[][] = new int[][] { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 4, 0, 0, 0, 0, 0, 0, 0 }};
		// Inicializamos los ladrillos
		
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 9 + 1; i++) {
				Ladrillo m = new Ladrillo(i * 48, j * 24, mapa[j][i]);
				if (mapa[j][i] > 0) {
					numLadrillos++;
				}
				actores.add(m);
			}
		}
	}

}
