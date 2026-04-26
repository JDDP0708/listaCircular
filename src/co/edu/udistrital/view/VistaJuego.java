package co.edu.udistrital.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaJuego extends JFrame {

    private JTextField txtNombre;
    private JButton btnAgregar, btnIniciar, btnLanzar, btnReiniciar;
    private JList<String> listJugadores;
    private DefaultListModel<String> listModel;
    private JTextArea txtLog;
    private JLabel lblTurno;

    public VistaJuego() {
        setTitle("Juego de Suerte");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Font fuenteGeneral = new Font("SansSerif", Font.PLAIN, 14);

        //agregae jugadores
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

        // Lista y registrro
        JPanel panelCenter = new JPanel(new BorderLayout(10, 10));
        panelCenter.setBorder(new EmptyBorder(10, 10, 10, 10));

        // indicador de turno
        JPanel panelTurno = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblTurno = new JLabel("Turno actual: Esperando inicio...");
        lblTurno.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTurno.setForeground(new Color(41, 128, 185)); // Azul oscuro
        panelTurno.add(lblTurno);

        // para listas
        JPanel panelListas = new JPanel(new GridLayout(1, 2, 10, 10));
        
        listModel = new DefaultListModel<>();
        listJugadores = new JList<>(listModel);
        listJugadores.setFont(fuenteGeneral);
        JScrollPane scrollLista = new JScrollPane(listJugadores);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Jugadores Actuales"));

        txtLog = new JTextArea();
        txtLog.setFont(fuenteGeneral);
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Registro del Juego"));

        panelListas.add(scrollLista);
        panelListas.add(scrollLog);

        panelCenter.add(panelTurno, BorderLayout.NORTH);
        panelCenter.add(panelListas, BorderLayout.CENTER);

        // botones de controles del juego
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBottom.setBorder(new EmptyBorder(0, 10, 10, 10));

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

    // metodo para los colores
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setOpaque(true); // fondo
        boton.setBorderPainted(false); // quitar el borde nativo que oculta el color
        boton.setFont(new Font("SansSerif", Font.BOLD, 13));
        return boton;
    }

    // getters y setters para actualizar
    public JTextField getTxtNombre() { 
        return txtNombre; 
    }
    public JButton getBtnAgregar() { 
        return btnAgregar; 
    }
    public JButton getBtnIniciar() { 
        return btnIniciar; 
    }
    public JButton getBtnLanzar() { 
        return btnLanzar; 
    }
    public JButton getBtnReiniciar() { 
        return btnReiniciar; 
    }
    
    public void setMensajeTurno(String mensaje) {
        lblTurno.setText(mensaje);
    }

    public void actualizarLista(String[] jugadores) {
        listModel.clear();
        for (String j : jugadores) {
            listModel.addElement(j);
        }
    }

    public void agregarLog(String mensaje) {
        txtLog.append(mensaje + "\n\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength()); 
    }

    public void limpiarLogs() {
        txtLog.setText("");
    }
}