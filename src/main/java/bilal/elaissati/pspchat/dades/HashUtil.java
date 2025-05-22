package bilal.elaissati.pspchat.dades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe utilitària per calcular el hash SHA-256 d'una cadena de text.
 * Aquesta funció és útil per encriptar contrasenyes o dades sensibles.
 */
public class HashUtil {

    /**
     * Calcula el hash SHA-256 d'una cadena de text i retorna el resultat en format hexadecimal.
     *
     * @param text La cadena de text que es vol hashejar.
     * @return El hash SHA-256 en format hexadecimal com a String.
     * @throws RuntimeException Si l'algoritme SHA-256 no està disponible al sistema.
     */
    public static String sha256(String text) {
        try {
            // Obtenim una instància de MessageDigest per l'algoritme SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Calculem el hash com un array de bytes
            byte[] hash = digest.digest(text.getBytes());

            // Convertim l'array de bytes a una cadena hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                // Convertim cada byte a hexadecimal (0xff & b per evitar signes negatius)
                String hex = Integer.toHexString(0xff & b);

                // Afegim un zero al principi si la longitud és 1 per mantenir el format
                if (hex.length() == 1) hexString.append('0');

                hexString.append(hex);
            }

            // Retornem la cadena hexadecimal resultant
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // Aquesta excepció no hauria de passar, ja que SHA-256 és estàndard
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
