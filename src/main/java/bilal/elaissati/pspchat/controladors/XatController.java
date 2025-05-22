package bilal.elaissati.pspchat.controladors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.Socket;

public class XatController {

    @FXML private ListView<String> llistaMissatges;
    @FXML private TextField campMissatge;

    private PrintWriter sortida;
    private String nomUsuari;

    public void setUsuari(String nom) {
        this.nomUsuari = nom;
        connectarAlServidor();
    }

    private void connectarAlServidor() {
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sortida = new PrintWriter(socket.getOutputStream(), true);

            sortida.println(nomUsuari + " s'ha connectat.");

            new Thread(() -> {
                String missatge;
                try {
                    while ((missatge = entrada.readLine()) != null) {
                        String finalMissatge = missatge;
                        Platform.runLater(() -> llistaMissatges.getItems().add(finalMissatge));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void enviarMissatge() {
        String missatge = campMissatge.getText();
        sortida.println(nomUsuari + ": " + missatge);
        campMissatge.clear();
    }
}
