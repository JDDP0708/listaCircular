package co.edu.udistrital.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Ventana principal de la aplicación.
 * Maneja la interfaz gráfica de usuario y expone los componentes 
 * para ser escuchados por el Controlador.
 */
public class VistaJuego extends JFrame {

    private JTextField txtNombre;
    private JButton btnAgregar, btnIniciar, btnLanzar, btnReiniciar;
    private JTextArea txtLog;
    private JLabel lblTurno;
    private PanelAnilloJugadores panelVisualAnillo; 

    /**
     * Constructor de la Vista. Configura el layout, inicializa los componentes
     * y ensambla los paneles superior, central e inferior.
     */
    public VistaJuego() {
        setTitle("Juego de la Lista Circular");
        setSize(800, 600); // Un poco más grande para que quepa el anillo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Font fuenteGeneral = new Font("SansSerif", Font.PLAIN, 14);

        // --- PANEL SUPERIOR: Agregar Jugadores ---
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelTop.setBorder(new EmptyBorder(10, 10, 0, 10));
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuenteGeneral);
        txtNombre = new JTextField(15);
        txtNombre.setFont(fuenteGeneral);
        
        btnAgregar = crearBoton("Agregar", new Color(46, 204, 113)); // Verde

        panelTop.add(lblNombre);
        panelTop.add(txtNombre);
        panelTop.add(btnAgregar);

        // --- PANEL CENTRAL: Visualización y Logs ---
        JPanel panelCenter = new JPanel(new BorderLayout(10, 10));
        panelCenter.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Letrero del turno
        JPanel panelTurno = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblTurno = new JLabel("Turno actual: Esperando inicio...");
        lblTurno.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTurno.setForeground(new Color(41, 128, 185));
        panelTurno.add(lblTurno);

        // Sub-panel contenedor para dividirse entre Anillo Visual y Consola de Logs
        JPanel panelContenido = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // INSTANCIAMOS EL NUEVO PANEL VISUAL
        panelVisualAnillo = new PanelAnilloJugadores();

        // Consola de Logs
        txtLog = new JTextArea();
        txtLog.setFont(fuenteGeneral);
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(
                BorderFactory.createTitledBorder("Registro del Juego"));

        // Añadimos el Anillo (izq) y Logs (der)
        panelContenido.add(panelVisualAnillo);
        panelContenido.add(scrollLog);

        panelCenter.add(panelTurno, BorderLayout.NORTH);
        panelCenter.add(panelContenido, BorderLayout.CENTER);

        // --- PANEL INFERIOR: Controles del juego ---
        JPanel panelBottom = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBottom.setBorder(
                new EmptyBorder(0, 10, 10, 10));

        btnIniciar = crearBoton("Iniciar Juego", new Color(52, 152, 219)); // Azul
        btnLanzar = crearBoton("Lanzar Dado", new Color(155, 89, 182)); // Morado
        btnLanzar.setEnabled(false);
        btnReiniciar = crearBoton("Reiniciar", new Color(231, 76, 60)); // Rojo

        panelBottom.add(btnIniciar);
        panelBottom.add(btnLanzar);
        panelBottom.add(btnReiniciar);

        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    /**
     * Crea un botón estilizado para evitar los problemas de renderizado en Windows/Mac.
     * 
     * @param texto      Texto que mostrará el botón.
     * @param colorFondo Color de fondo del botón.
     * @return Instancia de JButton estilizada.
     */
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        return boton;
    }

    // --- GETTERS ---

    /**
     * Retornar nombre
     * 
     * @return retorno del nombre
     */
    public JTextField getTxtNombre() { return txtNombre; }

    /**
     * Retornar boton agregar
     * 
     * @return JButton de agregar
     */
    public JButton getBtnAgregar() { return btnAgregar; }

    /**
     * Retornar boton inicial
     * 
     * @return JButton de iniciar
     */
    public JButton getBtnIniciar() { return btnIniciar; }

    /**
     * Retornar boton lanzar
     * 
     * @return JButton de lanzar
     */
    public JButton getBtnLanzar() { return btnLanzar; }

    /**
     * Retornar boton reiniciar
     * 
     * @return Jbutton de reiniciar
     */
    public JButton getBtnReiniciar() { return btnReiniciar; }
    
    /**
     * Actualiza el texto del letrero superior que indica el turno o estado del juego.
     * 
     * @param mensaje Mensaje a mostrar.
     */
    public void setMensajeTurno(String mensaje) {
        lblTurno.setText(mensaje);
    }

    /**
     * Pasa los datos actualizados al panel que dibuja el anillo.
     * 
     * @param jugadores     Arreglo de nombres de jugadores vivos.
     * @param jugadorActual Nombre del jugador en turno.
     */
    public void actualizarVisualizacionAnillo(String[] jugadores, 
            String jugadorActual) {
        panelVisualAnillo.actualizarDatos(jugadores, jugadorActual);
    }

    /**
     * Agrega un mensaje a la consola de la vista y hace auto-scroll hacia abajo.
     * 
     * @param mensaje Texto del log a añadir.
     */
    public void agregarLog(String mensaje) {
        txtLog.append(mensaje + "\n\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength()); 
    }

    /**
     * Borra todo el texto de la consola de registros.
     */
    public void limpiarLogs() {
        txtLog.setText("");
    }
}