package VersionCombinada;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Arkanoid extends Canvas {
	// Variables estáticas
	public static final int ANCHO = 480;
	public static final int ALTO = 600;
	public static final int FPS = 100; // Frames por segundo
	// Ventana
	JFrame ventana = null;
	// Lista de actores que se representan en pantalla
	List<Actor> actores = new ArrayList<Actor>();
	List<Actor> bolas = new ArrayList<Actor>();
	List<Actor> actoresGuardado = new ArrayList<Actor>();
	// Nave y bola
	Nave nave = new Nave();
	Bola bola;
	
	Bola bola2;
	Bola bola3;
	
	// Fase activa en el juego
	Fase faseActiva = null;
	Fase fases[] = new Fase[] { new Fase01(),new Fase04(),  new Fase03() };
	int numFase = 0;
	// Estrategia de Doble Buffer
	private BufferStrategy strategy;
	// Variable para patrón Singleton
	private static Arkanoid instancia = null;
	// Lista con actores nuevos que se deben incorporar en la siguiente iteración
	// del juego
	List<Actor> actoresAInsertar = new ArrayList<Actor>();
	boolean bolaBorrada = false;
	AnimacionComienzo animacionNueva;
	AnimacionComienzo animacionCambioDeFase;
	AnimacionComienzo animacionFinal;
	boolean animacionTerminada = false;
	int vidas = 3;
	boolean animacionPrimera = false;
	boolean haSidoIniciadaYa = false;
	int bolasEnLista=0;
	boolean finDejuego;

	/**
	 * Getter Singleton
	 * 
	 * @return
	 */
	public synchronized static Arkanoid getInstancia() {
		if (instancia == null) {
			instancia = new Arkanoid();
		}
		return instancia;
	}

	/**
	 * Constructor
	 */
	public Arkanoid() {
		// Creación de la ventana
		ventana = new JFrame("Arkanoid");
		// Obtenemos el panel principal del JFrame
		JPanel panel = (JPanel) ventana.getContentPane();
		// Establezco las dimensiones del Canvas
		this.setBounds(0, 0, ANCHO, ALTO);
		// El panel tendrá un tamaño preferido
		panel.setPreferredSize(new Dimension(ANCHO, ALTO));
		panel.setLayout(null);
		// Agregamos el Canvas al panel
		panel.add(this);
		ventana.setIconImage(CacheRecursos.getInstancia().getImagen("descarga.ico"));
		// Dimensionamos el JFrame
		ventana.setBounds(0, 0, ANCHO + 5, ALTO + 28);
		// Hacemos el JFrame visible
		ventana.setVisible(true);
		// Con el siguiente código preguntamos al usuario si realmente desea cerrar la
		// aplicación, cuando
		// pulse sobre el aspa de la ventana
		ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cerrarAplicacion();
			}
		});
		// Creación de la estrategia de doble búffer
		this.createBufferStrategy(2);
		strategy = this.getBufferStrategy();
		// Con ignoreRepaint le decimos al JFrame que no debe repintarse cuando el
		// Sistema Operativo se lo indique,
		// nosotros nos ocupamos totalmente del refresco de la pantalla
		ventana.setIgnoreRepaint(true);
		// La ventana no podrá redimensionarse
		ventana.setResizable(false);
		// Hacemos que el Canvas obtenga automáticamente el foco del programa para que,
		// si se pulsa una tecla, la pulsación
		// se transmita directamente al Canvas.
		this.requestFocus();
		// Para resolver un problema de sincronización con la memoria de vídeo de Linux,
		// utilizamos esta línea
		Toolkit.getDefaultToolkit().sync();

		// Agrego los controladores de ratón y de teclado
		ControladorRaton controladorRaton = new ControladorRaton();
		this.addMouseMotionListener(controladorRaton);
		this.addMouseListener(controladorRaton);
		this.addKeyListener(new ControladorTeclado());
	}

	/**
	 * Al cerrar la aplicación preguntaremos al usuario si está seguro de que desea
	 * salir.
	 */
	private void cerrarAplicacion() {
		String[] opciones = { "Aceptar", "Cancelar" };
		int eleccion = JOptionPane.showOptionDialog(ventana, "¿Desea cerrar la aplicación?", "Salir de la aplicación",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
		if (eleccion == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * Al principio del juego, lo primero que se debe hacer es inicializar todo lo
	 * necesario para que se pueda mostrar la primera fase.
	 */
	public void initWorld() {
		bola = new Bola();
		// Preparación de la primera fase
		this.faseActiva = fases[numFase];
		this.faseActiva.inicializaFase();
		// Agregamos los actores de la primera fase a nuestro juego
		this.actores.clear();
		this.actores.addAll(this.faseActiva.getActores());
		if(!this.actoresGuardado.isEmpty()) {
			this.actores.addAll(this.actoresGuardado);
			this.actoresGuardado.clear();
		}
		// Creación de los actores Nave y Bola
		this.actores.add(this.nave);
		this.actores.add(this.bola);

	}

	private boolean detectarYNotificarColisionConNave(Actor actor) {
		Rectangle rectActor = new Rectangle(actor.getX(), actor.getY(), actor.getAncho(), actor.getAlto());
		if (rectActor.intersects(this.nave.getRectanguloParaColisiones())) {
			if(actor instanceof Bullet)System.out.println("bala");
			actor.colisionProducidaConOtroActor(nave);
			this.nave.colisionProducidaConOtroActor(actor);
			return true;
		}
		return false;
	}
	/**
	 * @return the vidas
	 */
	public int getVidas() {
		return vidas;
	}

	/**
	 * @param vidas the vidas to set
	 */
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public void updateWorld() {
		for (Actor actor : this.actores) {
			if (actor instanceof Ladrillo || actor instanceof Nave || actor instanceof JefeFinal) {
				if (detectarYNotificarColisionConBola(actor)) {
					break; // Una vez que detecto la primera colisión dejo de buscar más colisiones.
				}
			}
			if(actor instanceof Pildora || actor instanceof Bullet) {
				if(detectarYNotificarColisionConNave(actor)) {
					break;
				}
			}
		}
		

		// A continuación se revisa si alguno de los actores de la lista ha sido marcado
		// para su eliminación. En caso de ser así
		// se procede a borrarlo de la lista.
		for (int i = this.actores.size() - 1; i >= 0; i--) {
			if (this.actores.get(i).marcadoParaEliminacion) {
				if (this.actores.get(i) instanceof Bola) {
					if (this.actores.get(i).equals(bola3)) {
						this.actores.remove(i);
						this.bola3=null;
					}
					else if (this.actores.get(i).equals(bola2)) {
						this.actores.remove(i);
						this.bola2=null;
					}
					else {
						vidas--;
						bolaBorrada = true;
						this.actores.remove(i);
						if (vidas > 0 ) {
							this.bola = new Bola();
							this.bola.trayectoria = null;
						}
					}

				} else {
					if (this.actores.get(i) instanceof Ladrillo) {
						this.faseActiva.numLadrillos--;
					}
					
					this.actores.remove(i);

				}
			}
		}
		if (bolaBorrada) {
			this.actores.add(this.bola);
			bolaBorrada = false;
		}

		// Agregamos aquellos nuevos actores que se desea incorporar a la siguiente
		// escena
		for (Actor nuevoActor : this.actoresAInsertar) {
			this.actores.add(0, nuevoActor);
		}
		this.actoresAInsertar.clear(); // Limpio el array de actores a insertar

		// Actualización de todos los actores
	
		for (Actor actor : this.actores) {
			actor.act();
		}
		for (Actor actor : this.bolas) {
			actor.act();
		}
		for (int i = this.bolas.size() - 1; i >= 0; i--) {
			if (this.bolas.get(i).marcadoParaEliminacion) {
				if (this.bolas.get(i) instanceof Bola) {
					this.actores.remove(i);

				} else {
					this.actores.remove(i);
				}
			}
		}
		
		
	}

	/**
	 * @param bolas the bolas to set
	 */
	public void setBolas(Bola bola) {
		this.bolas.add(0,bola);
	}

	/**
	 * 
	 * @param a1
	 * @param a2
	 */
	private boolean detectarYNotificarColisionConBola(Actor actor) {
		
		
		Rectangle rectActor = new Rectangle(actor.getX(), actor.getY(), actor.getAncho(), actor.getAlto());
		
		if (rectActor.intersects(this.bola.getRectanguloParaColisiones())) {
			// En el caso de que exista una colisión, informo a los dos actores de que han
			// colisionado y les indico el
			// actor con el que se ha producido el choque
			actor.colisionProducidaConOtroActor(this.bola);
			this.bola.colisionProducidaConOtroActor(actor);
			return true;
		}
		if(this.bola2!=null) {
			if (rectActor.intersects(this.bola2.getRectanguloParaColisiones())) {
				// En el caso de que exista una colisión, informo a los dos actores de que han
				// colisionado y les indico el
				// actor con el que se ha producido el choque
				actor.colisionProducidaConOtroActor(this.bola2);
				this.bola2.colisionProducidaConOtroActor(actor);
				return true;
			}
		}
		if(this.bola3!=null) {
			if (rectActor.intersects(this.bola3.getRectanguloParaColisiones())) {
				// En el caso de que exista una colisión, informo a los dos actores de que han
				// colisionado y les indico el
				// actor con el que se ha producido el choque
				actor.colisionProducidaConOtroActor(this.bola3);
				this.bola3.colisionProducidaConOtroActor(actor);
				return true;
			}
		}
		return false;
	}
	

	/**
	 * Método responsable de repintar cada frame del juego
	 */
	public void paintWorld() {
		// Obtenemos el objeto Graphics (la brocha) desde la estrategia de doble búffer
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		// Lo primero que hace cada frame es pintar un rectángulo tan grande como la
		// escena,
		// para superponer la escena anterior.
		g.setColor(Color.decode(Fichero.getProperty("COLOR")));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		// Ejecutamos el método paint de cada uno de los actores
		for (Actor actor : this.actores) {
			actor.paint(g);
		}
		// Una vez construida la escena nueva, la mostramos.
		g.setColor(Color.RED);
		if (animacionPrimera == false) {
			g.setColor(Color.BLACK);
		}
		g.setFont(new Font("Times New Roman", Font.BOLD, 23));
		if (vidas == 10) {
			g.drawString("♥x10", 0, 600 - 25);
		}
		if (vidas == 9) {
			g.drawString("♥x9", 0, 600 - 25);
		}
		if (vidas == 8) {
			g.drawString("♥x8", 0, 600 - 25);
		}
		if (vidas == 7) {
			g.drawString("♥x7", 0, 600 - 25);
		}
		if (vidas == 6) {
			g.drawString("♥x6", 0, 600 - 25);
		}
		if (vidas ==5) {
			g.drawString("♥x5", 0, 600 - 25);
		}
		if (vidas == 4) {
			g.drawString("♥x4", 0, 600 - 25);
		}
		if (vidas == 3) {
			g.drawString("♥♥♥", 0, 600 - 25);
		}
		if (vidas == 2) {
			g.drawString("♥♥", 0, 600 - 25);
		}
		if (vidas == 1) {
			g.drawString("♥", 0, 600 - 25);
		}

		strategy.show();
	}

	/**
	 * Método principal del juego. Contiene el bucle principal
	 */
	public void game() {
		finDejuego=false;

		Toolkit.getDefaultToolkit().sync();

		iniciarAnimacion();
		
		
		initWorld();

		while (this.isVisible()) {
			animacionPrimera = true;

			long millisAntesDeConstruirEscena = System.currentTimeMillis();

			updateWorld();
			paintWorld();

			int millisUsados = (int) (System.currentTimeMillis() - millisAntesDeConstruirEscena);

			try {
				int millisADetenerElJuego = 1000 / FPS - millisUsados;
				if (millisADetenerElJuego >= 0) {
					Thread.sleep(millisADetenerElJuego);
				}
			} catch (InterruptedException e) {
			}

			if (this.faseActiva.numLadrillos <= 0) {
				numFase++;
				setAnimacionTerminada(false);
				iniciarCambioDeFase();
				
			} else if (vidas <= 0) {
				for(Actor actor:this.actores) {
					if(actor instanceof Bola||actor instanceof Nave) {
						System.out.println("tpm");
					}else {
						this.actoresGuardado.add(actor);
					}
				}
				
				this.actores.clear();
				setAnimacionTerminada(false);
				iniciarFinDeJuego();
				
			}
		}

		
	}

	/**
	 * @param animacionTerminada the animacionTerminada to set
	 */
	public void setAnimacionTerminada(boolean animacionTerminada) {
		this.animacionTerminada = animacionTerminada;
	}

	public void iniciarAnimacion() {
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		if (haSidoIniciadaYa) {
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga3.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga4.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga5.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga6.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCargaFinal.png"));
			animacionNueva = new AnimacionComienzo(nuevosSprites, -1);
		} else {
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga1.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga2.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga1.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga2.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga3.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga4.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga5.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga6.png"));
			nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCargaFinal.png"));
			animacionNueva = new AnimacionComienzo(nuevosSprites, 5);
		}
		haSidoIniciadaYa = true;
		actores.add(animacionNueva);
		if (!animacionTerminada) {

			while (!animacionTerminada) {

				long millisAntesDeConstruirEscena = System.currentTimeMillis();

				updateWorld();
				paintWorld();

				int millisUsados = (int) (System.currentTimeMillis() - millisAntesDeConstruirEscena);

				try {
					int millisADetenerElJuego = 1000 / FPS - millisUsados;
					if (millisADetenerElJuego >= 0) {
						Thread.sleep(millisADetenerElJuego);
					}
				} catch (InterruptedException e) {
				}
			}
		}

	}

	public void iniciarCambioDeFase() {
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga3.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga4.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga5.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCarga6.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("pantallaCargaFinal.png"));
		animacionNueva = new AnimacionComienzo(nuevosSprites, -1);
		
		actores.add(animacionNueva);
		while (!animacionTerminada) {

			long millisAntesDeConstruirEscena = System.currentTimeMillis();

			updateWorld();
			paintWorld();

			int millisUsados = (int) (System.currentTimeMillis() - millisAntesDeConstruirEscena);

			try {
				int millisADetenerElJuego = 1000 / FPS - millisUsados;
				if (millisADetenerElJuego >= 0) {
					Thread.sleep(millisADetenerElJuego);
				}
			} catch (InterruptedException e) {
			}
			vidas++;
			game();
		}
	}

	private void iniciarFinDeJuego() {
		finDejuego=true;
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver1.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver2.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver22.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver3.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver33.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver4.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver44.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver5.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver55.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver6.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver66.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver7.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver77.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver8.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver88.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver9.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver99.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver0.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("gameOver00.png"));
		animacionNueva = new AnimacionComienzo(nuevosSprites, -1);
		
		
		
		actores.add(animacionNueva);
		while (!animacionTerminada) {

			long millisAntesDeConstruirEscena = System.currentTimeMillis();

			updateWorld();
			paintWorld();

			int millisUsados = (int) (System.currentTimeMillis() - millisAntesDeConstruirEscena);

			try {
				int millisADetenerElJuego = 1000 / FPS - millisUsados;
				if (millisADetenerElJuego >= 0) {
					Thread.sleep(millisADetenerElJuego);
				}
			} catch (InterruptedException e) {
				
			}
			if(vidas>0) {
				
				game();
				
			}
		}

	}

	/**
	 * Método que permite agregar un nuevo actor
	 * 
	 * @param nuevoActor
	 */
	public void agregarActor(Actor nuevoActor) {
		this.actoresAInsertar.add(nuevoActor);
	}

	// Getters
	public Nave getNave() {
		return nave;
	}

	public Bola getBola() {
		return bola;
	}

	public AnimacionComienzo getAnimacion() {
		return animacionNueva;
	}
	/**
	 * @return the bola2
	 */
	public Bola getBola2() {
		return bola2;
	}

	/**
	 * @return the bola3
	 */
	public Bola getBola3() {
		return bola3;
	}

	/**
	 * @param bola the bola to set
	 */
	public void setBola(Bola bola) {
		this.bola = bola;
	}

	/**
	 * @param bola2 the bola2 to set
	 */
	public void setBola2(Bola bola2) {
		this.bola2 = bola2;
	}

	/**
	 * @param bola3 the bola3 to set
	 */
	public void setBola3(Bola bola3) {
		this.bola3 = bola3;
	}

	/**
	 * Método main()
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CacheRecursos.getInstancia().cargarRecursosEnMemoria();
		Arkanoid.getInstancia().game();
	}

	public void finalDelJuego() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		finDejuego=true;
		List<BufferedImage> nuevosSprites = new ArrayList<BufferedImage>();
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		nuevosSprites.add(CacheRecursos.getInstancia().getImagen("perfectWin.png"));
		animacionNueva = new AnimacionComienzo(nuevosSprites, 5);
	
		
		animacionNueva = new AnimacionComienzo(nuevosSprites, 0);
		
		
		
		actores.add(animacionNueva);
		while (true) {

			long millisAntesDeConstruirEscena = System.currentTimeMillis();

			actores.get(0).act();
			paintWorld();
			

			int millisUsados = (int) (System.currentTimeMillis() - millisAntesDeConstruirEscena);

			try {
				int millisADetenerElJuego = 1000 / FPS - millisUsados;
				if (millisADetenerElJuego >= 0) {
					Thread.sleep(millisADetenerElJuego);
				}
			} catch (InterruptedException e) {
				
			}
			
		}
		
	}

	

}
