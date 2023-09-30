    import java.io.*;
    import java.util.*;
    import java.util.logging.*;

    public class AddressBook2_0 {

        private static final Logger LOGGER = Logger.getLogger(AddressBook2_0.class.getName());
        private HashMap<String, String> contacts = new HashMap<>();
        private final String filePath = "contacts.txt";  // Asegúrate de que este archivo exista en la misma ubicación que tu proyecto

        public void load() {
            File file = new File(filePath);
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] contactInfo = line.split(",");
                        contacts.put(contactInfo[0], contactInfo[1]);
                    }
                } catch (IOException e) {
                    LOGGER.severe("Error al cargar los contactos: " + e.getMessage());
                }
            } else {
                LOGGER.warning("Archivo de contactos no encontrado. Se creará uno nuevo al guardar.");
            }
        }

        public void save() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (Map.Entry<String, String> entry : contacts.entrySet()) {
                    bw.write(entry.getKey() + "," + entry.getValue() + "\n");
                }
            } catch (IOException e) {
                LOGGER.severe("Error al guardar los contactos: " + e.getMessage());
            }
        }

        public void list() {
            if (contacts.isEmpty()) {
                LOGGER.info("No hay contactos para mostrar.");
            } else {
                StringBuilder contactList = new StringBuilder();
                for (Map.Entry<String, String> entry : contacts.entrySet()) {
                    contactList.append("{").append(entry.getKey()).append("},{").append(entry.getValue()).append("}\n");
                }
                LOGGER.info("Contactos:\n" + contactList.toString());
            }
        }


        public void create(String number, String name) {
            Scanner scanner = new Scanner(System.in);  // Inicializamos el scanner aquí para capturar la entrada del usuario
            if (contacts.containsKey(number)) {
                LOGGER.warning("El número ya existe. Corresponde a: " + contacts.get(number));
                LOGGER.info("¿Desea actualizar el contacto? (si/no): ");
                String response = scanner.nextLine();
                if(response.equalsIgnoreCase("si")) {
                    contacts.put(number, name);  // Actualiza el contacto existente con el nuevo nombre
                    LOGGER.info("Contacto actualizado exitosamente.");
                } else {
                    LOGGER.info("Operación cancelada por el usuario.");
                }
            } else if (!number.matches("\\d{10}")) {
                LOGGER.warning("Número inválido. Por favor, ingrese exactamente 10 dígitos.");
            } else if (!name.matches("[a-zA-Z\\s]+")) {
                LOGGER.warning("Nombre inválido. Por favor, ingrese solo letras y espacios.");
            } else {
                contacts.put(number, name);
                LOGGER.info("Contacto creado exitosamente.");
            }
        }

        public void delete(String number) {
            if (contacts.containsKey(number)) {
                contacts.remove(number);
                LOGGER.info("Contacto eliminado exitosamente.");
            } else {
                LOGGER.warning("El número no existe. No se puede eliminar el contacto.");
            }
        }

        public void showMenu() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                LOGGER.info("1. Listar contactos\n2. Crear contacto\n3. Borrar contacto\n4. Guardar y Salir\nSeleccione una opción: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        list();
                        break;
                    case "2":
                        LOGGER.info("Ingrese el número: ");
                        String number = scanner.nextLine();
                        LOGGER.info("Ingrese el nombre: ");
                        String name = scanner.nextLine();
                        create(number, name);
                        break;
                    case "3":
                        LOGGER.info("Ingrese el número del contacto a borrar: ");
                        String delNumber = scanner.nextLine();
                        delete(delNumber);
                        break;
                    case "4":
                        save();
                        scanner.close();
                        return;
                    default:
                        LOGGER.warning("Opción inválida. Por favor, intente de nuevo.");
                }
            }
        }

        public static void main(String[] args) {
            AddressBook2_0 addressBook = new AddressBook2_0();
            addressBook.load();
            addressBook.showMenu();
        }
    }
