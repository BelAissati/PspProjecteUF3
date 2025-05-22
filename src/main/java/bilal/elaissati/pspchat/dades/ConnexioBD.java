package bilal.elaissati.pspchat.dades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexioBD {
    private static final String URL = "jdbc:sqlite:usuaris.db";

    /**
     * Obté una connexió a la base de dades SQLite, crea la taula i l'usuari de prova si cal.
     * @return Connection activat.
     * @throws SQLException si hi ha un error SQL.
     * @throws ClassNotFoundException si el driver no es troba.
     */
    public static Connection obtenirConnexio() throws SQLException, ClassNotFoundException {
        // 1) Registra el driver SQLite
        Class.forName("org.sqlite.JDBC");

        // 2) Obté la connexió
        Connection conn = DriverManager.getConnection(URL);

        // 3) Crea la taula i l'usuari de prova si no existeixen
        String sqlCrearTaula =
                "CREATE TABLE IF NOT EXISTS usuaris (" +
                        "  nom TEXT PRIMARY KEY," +
                        "  contrasenya TEXT NOT NULL" +
                        ");";
        String sqlInserirUsuari =
                "INSERT OR IGNORE INTO usuaris(nom, contrasenya) VALUES (" +
                        "  'bilal'," +
                        "  '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'" +
                        ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCrearTaula);
            stmt.execute(sqlInserirUsuari);
        }

        return conn;
    }
}
