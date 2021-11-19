/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javi
 */
public class AdressBook {

    private static final File FILE = new File("contactos.txt");
    private HashMap<String, String> contactos;
    private boolean erase;

    public AdressBook() {
        contactos = new HashMap<>();
        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
                obtenerContactos();
            } catch (Exception ex) {
                System.err.println("No se pudo crear el archivo contactos.txt");
            }
        } else {
            try {
                obtenerContactos();
            } catch (Exception ex) {
                Logger.getLogger(AdressBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void obtenerContactos() throws Exception {
        FileReader reader = new FileReader(FILE);
        BufferedReader br = new BufferedReader(reader);

        boolean bandera = true;

        while (bandera) {
            String cadena = br.readLine();
            if (cadena != null) {
                StringTokenizer stk = new StringTokenizer(cadena, ",");
                String numero = stk.nextToken();
                String nombre = stk.nextToken();
                contactos.put(numero, nombre);
            } else {
                br.close();
                reader.close();
                bandera = false;
            }
        }
    }

    public void agregarContacto(String numero, String nombre) {
        contactos.put(numero, nombre);
    }

    public void saveContacto(String numero, String nombre, boolean borrar) throws IOException {
        try (FileWriter fw = new FileWriter(FILE, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter pw = new PrintWriter(bw)) {
            if (borrar) {
                this.erase = false;
                PrintWriter pw2 = new PrintWriter(FILE);
                pw2.print("");
                System.out.println("Se debio haber borrao");
            }
            pw.println(numero + "," + nombre);
            pw.close();
            bw.close();
            fw.close();
        }
    }

    public void save() {
        erase = true;
        contactos.forEach((t, u) -> {
            try {
                saveContacto(t, u, erase);
            } catch (IOException ex) {
                Logger.getLogger(AdressBook.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public HashMap<String, String> getContactos() {
        return this.contactos;
    }

}
