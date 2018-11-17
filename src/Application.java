import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get("phonedirectory.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ye");
        }
        String line = null;

        HashMap<String, Contact> phoneDirectory = new HashMap<>();

        while ((line = br.readLine()) != null) {
            String[] contactDetails = line.split(";");

            List<String> telefoonNummers = new ArrayList<>();

            for (int i = 1; i < contactDetails.length; i++) {

                telefoonNummers.add(contactDetails[i]);
            }
            phoneDirectory.put(contactDetails[0], new Contact(contactDetails[0], telefoonNummers));
        }
        br.close();

        String action = "";
        Scanner keyboard = new Scanner(System.in);
        while (!action.equalsIgnoreCase("stop")) {
            System.out.println("'add', 'lookup' 'delete' or 'stop'");
            action = keyboard.nextLine();

            if (action.equalsIgnoreCase("lookup")) {
                System.out.println("Input a name to look up");
                String lookupKey = keyboard.nextLine();
                if (!phoneDirectory.containsKey(lookupKey)) {
                    System.out.println("No such name in the phone directory.");
                } else {
                    System.out.println(phoneDirectory.get(lookupKey));
                }
            } else {
                if (action.equalsIgnoreCase("stop")) {
                    System.out.println("Stopping...");
                } else {
                    if (action.equalsIgnoreCase("add")) {
                        System.out.println("What is the name of your contact?");
                        String name = keyboard.nextLine();
                        BufferedWriter bw = Files.newBufferedWriter(Paths.get("phoneDirectory.txt"), StandardOpenOption.APPEND);

                        Contact newContact = new Contact(name);

                        String telephoneNumbers = "";
                        while (!telephoneNumbers.equalsIgnoreCase("end")) {
                            System.out.println("Provide a telephone number, 'end' to stop.");

                            telephoneNumbers = keyboard.nextLine();

                            if (!isNumber(telephoneNumbers) && !telephoneNumbers.equalsIgnoreCase("end")) {
                                System.out.println("Not a number.");
                            } else {

                                if (!telephoneNumbers.equalsIgnoreCase("end")) {
                                    newContact.addNumber(telephoneNumbers);
                                } else {
                                    StringBuilder sb = new StringBuilder(newContact.getName());

                                    for (int i = 0; i < newContact.getTelefoonNummers().size(); i++) {
                                        sb.append(";" + newContact.getTelefoonNummers().get(i));
                                    }
                                    System.out.println(sb.toString());
                                    phoneDirectory.put(newContact.getName(), newContact);
                                    bw.write(sb.toString());
                                    bw.newLine();
                                    bw.close();
                                }
                            }
                        }
                    } else {
                        if (action.equalsIgnoreCase("delete")) {
                            System.out.println("Please provide the name of the contact you would like to delete.");

                            String contactToDelete = keyboard.nextLine();

                            String currentLine = "";

                            Path original = Paths.get("phonedirectory.txt");
                            Path temp = Paths.get("temp.txt");

                            BufferedReader deleteReader = Files.newBufferedReader(original);
                            BufferedWriter deleteWriter = Files.newBufferedWriter(temp);

                            File tempFile = new File("temp.txt");
                            File originalFile = new File("phonedirectory.txt");


                            while ((currentLine = deleteReader.readLine()) != null) {
                                String[] details = currentLine.split(";");

                                if (!details[0].equalsIgnoreCase(contactToDelete)) {
                                    deleteWriter.write(currentLine);
                                    deleteWriter.newLine();
                                }

                            }

                            deleteWriter.close();
                            deleteReader.close();

                            phoneDirectory.remove(contactToDelete);

                            originalFile.delete();
                            tempFile.renameTo(originalFile);

                        } else {
                            System.out.println("No such command.");
                        }
                    }
                }
            }
        }
        keyboard.close();
    }

    public static boolean isNumber(String number) {
        try {
            Long.parseLong(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}