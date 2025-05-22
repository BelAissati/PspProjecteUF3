package bilal.elaissati.pspchat.controladors;

import bilal.elaissati.pspchat.dades.ConnexioBD;
import bilal.elaissati.pspchat.dades.HashUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField campNom;
    @FXML private PasswordField campContrasenya;
    @FXML private Label missatgeError;

    @FXML
    public void iniciarSessio(ActionEvent event) {
        missatgeError.setText("");  // netegem errors anteriors
        String nom = campNom.getText().trim();
        String passClear = campContrasenya.getText().trim();
        String contrasenyaHash = HashUtil.sha256(passClear);

        boolean valid = false;
        try (Connection conn = ConnexioBD.obtenirConnexio()) {
            valid = validarCredencials(conn, nom, contrasenyaHash);
        } catch (ClassNotFoundException e) {
            missatgeError.setText("Driver SQLite no trobat");
            e.printStackTrace();
            return;
        } catch (SQLException e) {
            String msg = e.getMessage().contains("database is locked")
                    ? "Base de dades bloquejada. Tanca altres programes."
                    : "Error BD: " + e.getMessage();
            missatgeError.setText(msg);
            e.printStackTrace();
            return;
        }

        if (valid) {
            carregarXat(nom);
        } else {
            missatgeError.setText("Credencials incorrectes");
        }
    }

    /**
     * Comprova si existeix l'usuari amb aquesta contrasenya.
     */
    private boolean validarCredencials(Connection conn, String nom, String hash) throws SQLException {
        String sql = "SELECT 1 FROM usuaris WHERE nom = ? AND contrasenya = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, hash);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    /**
     * Canvia a la vista de xat passant-li el nom d'usuari.
     */
    private void carregarXat(String nomUsuari) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistes/xat.fxml"));
            Scene escena = new Scene(loader.load());
            escena.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            XatController ctrl = loader.getController();
            ctrl.setUsuari(nomUsuari);

            Stage stage = (Stage) campNom.getScene().getWindow();
            stage.setScene(escena);
        } catch (Exception e) {
            missatgeError.setText("No s'ha pogut obrir el xat");
            e.printStackTrace();
        }
    }
}
