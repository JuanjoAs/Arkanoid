package VersionCombinada;

public class Fase02 extends Fase {
	
	public static final int ESPACIO_SUPERIOR_SOBRE_LADRILLOS = 60;

	@Override
	public void inicializaFase() {

		int mapa[][] = new int[][] { 
			{ 1, 2, 0, 0, 0, 0, 0, 0, 2, 1 },
			{ 2, 1, 2, 4, 4, 4, 4, 2, 1, 2 },
			{ 0, 2, 1, 6, 6, 6, 6, 1, 2, 0 },
			{ 0, 4, 6, 1, 5, 5, 1, 6, 4, 0 },
			{ 0, 4, 6, 7, 1, 1, 7, 6, 4, 0 },
			{ 0, 4, 6, 6, 1, 1, 6, 6, 4, 0 },
			{ 0, 2, 4, 1, 6, 6, 1, 4, 2, 0 },
			{ 0, 2, 1, 4, 4, 4, 4, 1, 0, 0 },
			{ 0, 1, 2, 2, 2, 2, 2, 2, 1, 0 },
			{ 1, 2, 4, 2, 3, 3, 2, 4, 2, 1 }};
		// Inicializamos los ladrillos
		
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				Ladrillo m = new Ladrillo(i * 48, j * 24, mapa[j][i]);
				
				if (j > 0) {
					numLadrillos++;
				}
				actores.add(m);
			}
		}
	}

}
