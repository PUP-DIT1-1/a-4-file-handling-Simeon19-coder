import java.util.*;
import java.io.*;

public class FileHandling {

    static final String FILE_STRING = "records.csv";
    static ArrayList<String[]> records = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void loadRecords() {
        File file = new File(FILE_STRING);

        try {
            if (!file.exists()) {
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("ID,Name,Age\n");
                bw.close();
                return;
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            readRecursive(br);
            br.close();

        } catch (IOException e) {
            System.out.println("Error loading file.");
        }

    }

    public static void readRecursive(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null)
            return;

        String[] data = line.split(",");
        if (data.length == 3)
            records.add(data);

        readRecursive(br);
    }

    public static void saveRecords() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_STRING));
            bw.write("ID,Name,Age\n");
            writeRecursive(bw, 0);
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public static void writeRecursive(BufferedWriter bw, int i) throws IOException {
        if (i >= records.size())
            return;

        bw.write(String.join(",", records.get(i)));
        bw.newLine();

        writeRecursive(bw, i + 1);
    }

    public static void displayRecords() {
        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        displayRecursive(0);
    }

    public static void displayRecursive(int i) {
        if (i >= records.size())
            return;
        String[] r = records.get(i);
        System.out.println(i + ": ID=" + r[0] + ", Name=" + r[1] + ", Age=" + r[2]);
        displayRecursive(i + 1);
    }

    public static void updateRecord() {
        displayRecords();

        try {
            System.out.print("Enter index: ");
            int index = Integer.parseInt(scanner.nextLine());

            if (index < 0 || index >= records.size()) {
                System.out.println("Invalid index.");
                return;
            }

            System.out.print("New ID: ");
            String id = scanner.nextLine();

            System.out.print("New Name: ");
            String name = scanner.nextLine();

            System.out.print("New Age: ");
            String age = scanner.nextLine();

            records.set(index, new String[] { id, name, age });
            saveRecords();

            System.out.println("Updated successfully.");

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public static void deleteRecord() {
        displayRecords();

        try {
            System.out.print("Enter index: ");
            int index = Integer.parseInt(scanner.nextLine());

            if (index < 0 || index >= records.size()) {
                System.out.println("Invalid index.");
                return;
            }

            records.remove(index);
            saveRecords();

            System.out.println("Deleted successfully.");

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public static void menu() {
        System.out.println("\n1. Display Records");
        System.out.println("2. Add Records");
        System.out.println("3. Update Records");
        System.out.println("4. Delete Records");
        System.out.println("5. Exit");

        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                displayRecords();
                break;
            case "2":
                System.out.print("Enter ID: ");
                String id = scanner.nextLine();

                System.out.print("Enter Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Age: ");
                String age = scanner.nextLine();

                records.add(new String[] { id, name, age });
                saveRecords();

                System.out.println("Added successfully.");
                break;
            case "3":
                updateRecord();
                break;
            case "4":
                deleteRecord();
                break;
            case "5":
                System.out.println("Goodbye!");
                return;
            default:
                System.out.println("Invalid choice.");
        }

        menu();
    }

    public static void main(String[] args) {
        loadRecords();
        menu();
    }

}