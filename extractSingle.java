import java.io.*;
import java.util.zip.*;

public class extractSingle {
    public static void main(String[] args) {
        try {
            // Prompt the user for the folder containing the split ZIP files
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the folder containing split ZIP files: ");
            String folderPath = reader.readLine();
			
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the file name: ");
            String filename = reader.readLine();
			

            File folder = new File(folderPath);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".zip"));
            
            if (files != null && files.length > 0) {
                FileOutputStream fos = new FileOutputStream(filename); 
                
                for (File file : files) {
                    try (FileInputStream fis = new FileInputStream(file);
                         ZipInputStream zis = new ZipInputStream(fis)) {

                        zis.getNextEntry();
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }

                fos.close();
                System.out.println("Reconstructed file created.");
            } else {
                System.out.println("No split ZIP files found in the folder.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
