package co.edu.udistrital.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PanelAnilloJugadores extends JPanel {

    private String[] nombresJugadores;
    private String nombreJugadorActual;

    // Colores bonitos para los estados
    private final Color colorJugadorNormal = new Color(189, 195, 199); // Gris plata
    private final Color colorJugadorTurno = new Color(52, 152, 219);  // Azul brillante
    private final Color colorBorde = Color.WHITE;

    public PanelAnilloJugadores() {
        // Fondo blanco para que resalten los círculos
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Visualización del Anillo"));
        this.nombresJugadores = new String[0];
        this.nombreJugadorActual = "";
    }

    // Método para actualizar los datos desde el controlador
    public void actualizarDatos(String[] nuevosNombres, String actual) {
        this.nombresJugadores = nuevosNombres;
        this.nombreJugadorActual = actual;
        // Obliga al panel a redibujarse inmediatamente con los nuevos datos
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Usamos Graphics2D para suavizar bordes (Anti-aliasing)
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int totalJugadores = nombresJugadores.length;
        if (totalJugadores == 0) {
            drawMensajeVacio(g2d);
            return;
        }

        // --- CÁLCULOS GEOMÉTRICOS ---
        int width = getWidth();
        int height = getHeight();
        int centroX = width / 2;
        int centroY = height / 2;

        // El radio del anillo principal es el 35% del ancho o alto, el que sea menor
        int radioAnillo = (int) (Math.min(width, height) * 0.35);
        // El tamaño de cada "asiento" de jugador
        int radioJugador = 25;

        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));

        // --- DIBUJAR JUGADORES EN CÍRCULO ---
        for (int i = 0; i < totalJugadores; i++) {
            String nombre = nombresJugadores[i];

            // Calculamos el ángulo para este jugador i
            // (2 * PI es el círculo completo, lo dividimos en 'totalJugadores')
            // Restamos PI/2 para que el primer jugador empiece arriba, no a la derecha.
            double angulo = (2 * Math.PI * i / totalJugadores) - (Math.PI / 2);

            // Coordenadas cartesianas (x, y) usando Polares (radio, angulo)
            int x = (int) (centroX + radioAnillo * Math.cos(angulo));
            int y = (int) (centroY + radioAnillo * Math.sin(angulo));

            // --- DIBUJAR EL CÍRCULO (JUGADOR) ---
            // Decidir color según el turno
            if (nombre.equals(nombreJugadorActual)) {
                g2d.setColor(colorJugadorTurno);
            } else {
                g2d.setColor(colorJugadorNormal);
            }

            // Dibujar el fondo del círculo
            Ellipse2D.Double circulo = new Ellipse2D.Double(x - radioJugador, y - radioJugador, radioJugador * 2, radioJugador * 2);
            g2d.fill(circulo);

            // Dibujar el borde del círculo
            g2d.setColor(colorBorde);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(circulo);

            // --- DIBUJAR EL NOMBRE ---
            g2d.setColor(Color.BLACK);
            // Centrar el texto debajo del círculo
            FontMetrics fm = g2d.getFontMetrics();
            int textoX = x - (fm.stringWidth(nombre) / 2);
            int textoY = y + radioJugador + fm.getAscent() + 2;
            g2d.drawString(nombre, textoX, textoY);
        }
    }

    private void drawMensajeVacio(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("SansSerif", Font.ITALIC, 16));
        String msg = "Agrega jugadores para ver el anillo...";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
    }
}