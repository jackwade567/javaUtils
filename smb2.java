// ... [Other imports] ...
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

// ... [Existing SmbConfig and SmbService code] ...

@Component
public class SmbService {
    // ... [Existing SMBClient, AuthenticationContext, and remoteAddress] ...

    // Method to download a file given its remote path
    public Path downloadFile(String shareName, String remoteFilePath) throws IOException {
        Path localTempFilePath = Paths.get(System.getProperty("java.io.tmpdir"), new File(remoteFilePath).getName());

        try (Connection connection = smbClient.connect(remoteAddress)) {
            Session session = connection.authenticate(authenticationContext);
            try (DiskShare share = (DiskShare) session.connectShare(shareName)) {
                File remoteFile = share.openFile(remoteFilePath,
                        EnumSet.of(AccessMask.GENERIC_READ),
                        null,
                        SMB2ShareAccess.ALL,
                        SMB2CreateDisposition.FILE_OPEN,
                        null);
                try (InputStream in = remoteFile.getInputStream();
                     OutputStream out = new FileOutputStream(localTempFilePath.toFile())) {
                    byte[] buffer = new byte[8 * 1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
        return localTempFilePath;
    }
}



@Service
public class YourService {
    private final SmbService smbService;

    @Autowired
    public YourService(SmbService smbService) {
        this.smbService = smbService;
    }

    public void processFiles() {
        // ... your existing logic ...
        List<String> files = smbService.listFiles(shareName, directory);
        for (String fileName : files) {
            try {
                Path downloadedFile = smbService.downloadFile(shareName, fileName);
                // Now you can call parseXMLAndCreateMetaData or any other method
                parseXMLAndCreateMetaData(downloadedFile.toString());
                // Consider deleting the temporary file if no longer needed
                Files.deleteIfExists(downloadedFile);
            } catch (IOException e) {
                LOGGER.error("Failed to download or process file: " + fileName, e);
            }
        }
        // ... more logic ...
    }
}
