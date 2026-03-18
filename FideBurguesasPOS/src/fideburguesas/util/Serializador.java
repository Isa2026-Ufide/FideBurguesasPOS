package fideburguesas.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializador {

    public static void guardar(String archivo, Object objeto) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(archivo)
            );
            oos.writeObject(objeto);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object cargar(String archivo) {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(archivo)
            );
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (Exception e) {
            return null;
        }
    }
}
