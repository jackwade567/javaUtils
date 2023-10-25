import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BatchInsert {

    private static final String DB_URL = "jdbc:postgresql://your_host:your_port/your_db";
    private static final String DB_USER = "your_user";
    private static final String DB_PASS = "your_password";
    private static final int BATCH_SIZE = 1000; // Number of rows per batch, you can adjust this as needed

    public static void main(String[] args) {
        String inputFile = "path_to_your_file.txt";
        String insertBase = "INSERT INTO your_table (column1, column2, ... columnN) VALUES ";

        List<String> values = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            String line;
            while ((line = br.readLine()) != null) {
                values.add(line.trim());

                if (values.size() >= BATCH_SIZE) {
                    insertBatch(insertBase, values, connection);
                    values.clear();
                }
            }

            // Insert any remaining values
            if (!values.isEmpty()) {
                insertBatch(insertBase, values, connection);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertBatch(String baseQuery, List<String> values, Connection connection) throws SQLException {
        String query = baseQuery + String.join(", ", values);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        }
    }
}
