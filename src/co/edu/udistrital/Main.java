package co.edu.udistrital;

import co.edu.udistrital.controller.ControladorJuego;
import co.edu.udistrital.model.Juego;
import co.edu.udistrital.view.VistaJuego;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Hace que la interfaz se vea como una ventanita de windows
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // instancias de mvc
        Juego modelo = new Juego();
        VistaJuego vista = new VistaJuego();
        ControladorJuego controlador = new ControladorJuego(modelo, vista);

        // iniciar
        controlador.iniciar();
    }
}