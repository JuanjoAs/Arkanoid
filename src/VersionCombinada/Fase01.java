package VersionCombinada;

public class Fase01 extends Fase {
	// Propiedades estáticas de la fase
	public static final int ESPACIO_SUPERIOR_SOBRE_LADRILLOS = 60;


	@Override
	public void inicializaFase() {

		int mapa[][] = new int[][] { 
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 2, 2, 2, 2, 0, 0, 0 },
			{ 0, 0, 2, 4, 4, 4, 4, 2, 0, 0 },
			{ 0, 2, 4, 6, 6, 6, 6, 4, 2, 0 },
			{ 2, 4, 6, 6, 5, 5, 6, 6, 4, 2 },
			{ 2, 4, 6, 7, 1, 1, 7, 6, 4, 2 },
			{ 2, 4, 6, 6, 5, 5, 6, 6, 4, 2 },
			{ 0, 2, 4, 6, 6, 6, 6, 4, 2, 0 },
			{ 0, 0, 2, 4, 4, 4, 4, 2, 0, 0 },
			{ 0, 0, 0, 5, 5, 5, 5, 0, 0, 0 }};
			
			// Inicializamos los ladrillos
			int numLadrillosPosibles = Arkanoid.ANCHO / (Ladrillo.ANCHO + Ladrillo.ESPACIO_ENTRE_LADRILLOS);
			for (int j = 0; j < 10; j++) {
				for (int i = 0; i < numLadrillosPosibles + 1; i++) {
					Ladrillo m = new Ladrillo(i * 48, j * 24, mapa[j][i]);
					
					if (j > 0) {
						numLadrillos++;
					}
					actores.add(m);
				}
			}
	}

}
