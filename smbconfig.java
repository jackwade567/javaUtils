import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.paths.PathResolveException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

@Configuration
public class SmbConfig {

    @Bean
    public SMBClient smbClient() {
        return new SMBClient();
    }

    @Component
    public class SmbService {
        private final SMBClient smbClient;
        private final String remoteAddress = "your_remote_address"; // e.g., "fileserver.company.com"
        private final String username = "your_username";
        private final String password = "your_password";
        private final String domain = "your_domain"; // can be "" if not applicable

        public SmbService(SMBClient smbClient) {
            this.smbClient = smbClient;
        }

        public List<String> listFiles(String shareName, String directory) throws IOException {
            List<String> fileList = new ArrayList<>();
            AuthenticationContext ac = new AuthenticationContext(username, password.toCharArray(), domain);
            try (Connection connection = smbClient.connect(remoteAddress)) {
                Session session = connection.authenticate(ac);
                try (DiskShare share = (DiskShare) session.connectShare(shareName)) {
                    for (com.hierynomus.smbj.share.FileIdBothDirectoryInformation f : share.list(directory)) {
                        // Apply your file filters here
                        if (isXMLFile(f.getFileName())) {
                            fileList.add(f.getFileName());
                        }
                    }
                }
            }
            return fileList;
        }

        private boolean isXMLFile(String fileName) {
            // Implement your XML file checking logic here
            return fileName.endsWith(".xml");
        }
    }
}
