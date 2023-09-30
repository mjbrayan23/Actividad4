import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AddressBook {

    private HashMap<String, String> contacts = new HashMap<>();
    private final String filePath = "contacts.txt";  // Suponiendo que tu archivo está en la misma ubicación que tu proyecto

    // Método para cargar contactos del archivo
    public void load() {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                contacts.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar contactos en el archivo
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos los contactos
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Método para crear un nuevo contacto
    public void create(String number, String name) {
        contacts.put(number, name);
    }

    // Método para borrar un contacto
    public void delete(String number) {
        contacts.remove(number);
    }

    // Método para mostrar el menú interactivo
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Guardar y Salir");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume el newline

            switch (choice) {
                case 1:
                    list();
                    break;
                case 2:
                    System.out.print("Ingrese el número: ");
                    String number = scanner.nextLine();
                    System.out.print("Ingrese el nombre: ");
                    String name = scanner.nextLine();
                    create(number, name);
                    break;
                case 3:
                    System.out.print("Ingrese el número del contacto a borrar: ");
                    String delNumber = scanner.nextLine();
                    delete(delNumber);
                    break;
                case 4:
                    save();
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.load();
        addressBook.showMenu();
    }
}
