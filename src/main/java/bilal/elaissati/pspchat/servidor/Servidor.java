package bilal.elaissati.pspchat.servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {

    private static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(12345)) {
            System.out.println("Servidor engegat al port 12345");

            while (true) {
                Socket client = servidor.accept();
                PrintWriter sortida = new PrintWriter(client.getOutputStream(), true);
                clients.add(sortida);

                new Thread(() -> gestionarClient(client, sortida)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gestionarClient(Socket client, PrintWriter sortida) {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            String missatge;
            while ((missatge = entrada.readLine()) != null) {
                for (PrintWriter pw : clients) {
                    pw.println(missatge);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clients.remove(sortida);
        }
    }
}
