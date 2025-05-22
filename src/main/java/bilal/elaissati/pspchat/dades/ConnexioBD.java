package bilal.elaissati.pspchat.dades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe per gestionar la connexió a la base de dades SQLite.
 * També crea la taula d'usuaris i insereix un usuari de prova si no existeix.
 */
public class ConnexioBD {

    // URL de connexió a la base de dades SQLite local anomenada 'usuaris.db'
    private static final String URL = "jdbc:sqlite:usuaris.db";

    /**
     * Obté una connexió activa a la base de dades SQLite.
     * Si la taula d'usuaris no existeix, la crea.
     * També crea un usuari de prova amb nom 'bilal' i contrasenya xifrada.
     *
     * @return Una connexió activa a la base de dades.
     * @throws SQLException Si hi ha un error en executar sentències SQL.
     * @throws ClassNotFoundException Si no es troba el driver JDBC de SQLite.
     */
    public static Connection obtenirConnexio() throws SQLException, ClassNotFoundException {
        // 1) Registra el driver JDBC per SQLite
        Class.forName("org.sqlite.JDBC");

        // 2) Obté la connexió a la base de dades
        Connection conn = DriverManager.getConnection(URL);

        // 3) Sentència SQL per crear la taula 'usuaris' si no existeix
        String sqlCrearTaula =
                "CREATE TABLE IF NOT EXISTS usuaris (" +
                        "  nom TEXT PRIMARY KEY," +
                        "  contrasenya TEXT NOT NULL" +
                        ");";

        // 4) Sentència SQL per inserir un usuari de prova si no existeix (utilitzant INSERT OR IGNORE)
        String sqlInserirUsuari =
                "INSERT OR IGNORE INTO usuaris(nom, contrasenya) VALUES (" +
                        "  'bilal'," +
                        "  '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4'" +
                        ");";

        // 5) Executa les sentències SQL dins d'un bloc try-with-resources per assegurar tancament
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCrearTaula);    // Crea la taula si cal
            stmt.execute(sqlInserirUsuari); // Insereix l'usuari de prova si no existeix
        }

        // 6) Retorna la connexió activa
        return conn;
    }
}
