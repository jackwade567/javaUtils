import java.io.File;
import java.nio.file.*;
import java.util.Base64;
import java.util.Scanner;

public class base64Dec {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Get the input directory containing encoded text files
        System.out.print("Enter the path to the input directory with encoded files: ");
        String inputDirectoryStr = scanner.nextLine();
        Path inputDirectory = Paths.get(inputDirectoryStr);

        // Get the output directory to save decoded files
        System.out.print("Enter the path to the output directory for decoded files: ");
        String outputDirectoryStr = scanner.nextLine();
        Path outputDirectory = Paths.get(outputDirectoryStr);

        // Create the output directory if it doesn't exist
        if (!Files.exists(outputDirectory)) {
            Files.createDirectories(outputDirectory);
        }

        if (Files.exists(inputDirectory) && Files.isDirectory(inputDirectory)) {
            File[] encodedFiles = inputDirectory.toFile().listFiles(file -> file.getName().endsWith(".txt"));

            if (encodedFiles != null) {
                for (File encodedFile : encodedFiles) {
                    byte[] encodedData = Files.readAllBytes(encodedFile.toPath());

                    // Decode the Base64-encoded data to binary format
                    byte[] binaryData = Base64.getDecoder().decode(encodedData);

                    // Remove ".txt" extension from the original filename
                    String originalFileName = encodedFile.getName().replace(".txt", "");

                    // Save the decoded data as a ZIP file in the output directory with the original name
                    Path outputFile = outputDirectory.resolve(originalFileName + ".zip");
                    Files.write(outputFile, binaryData);

                    System.out.println(
                        "Decoded " + encodedFile.getName() + " and saved as " + outputFile.getFileName());
                }
            } else {
                System.out.println("No encoded files found in the input directory.");
            }
        } else {
            System.out.println("Invalid input directory or directory not found.");
        }

        scanner.close();
    }
}
