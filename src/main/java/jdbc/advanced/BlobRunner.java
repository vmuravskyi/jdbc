package jdbc.advanced;

import jdbc.util.connectio_pool_with_wrapper.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobRunner {

    public static void main(String[] args) {
        // blob (binary lange object), bytea
        // clob (character large object), TEXT

//        saveImageToDb();
        getImage();
    }

    private static void getImage() {
        var sql = """
                SELECT image
                FROM aircraft
                WHERE id = ?
                """;

        try (Connection connection = ConnectionPool.get()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, 1);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                InputStream image = resultSet.getBinaryStream("image");
                byte[] allBytes = image.readAllBytes();
                Path outputPath = Path.of("src/main/resources", "newFile.jpeg");
                Files.write(outputPath, allBytes, StandardOpenOption.CREATE);
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveImageToDb() {
        var sql = """
                UPDATE aircraft
                SET image = ?
                WHERE id = ?
                """;
        try (Connection connection = ConnectionPool.get()) {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBytes(1, Files.readAllBytes(Path.of("src/main/resources/boeing-777.jpeg")));
            preparedStatement.setLong(2, 1);

            int result = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private static void saveImageToDb() {
//        var sql = """
//                UPDATE aircraft
//                SET image = ?
//                WHERE id = ?
//                """;
//        try (Connection connection = ConnectionManager.open()) {
//            connection.setAutoCommit(false);
//            Blob blob = connection.createBlob();
//            blob.setBytes(1, Files.readAllBytes(Path.of("resources", "boeing-777.jpeg")));
//
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setBlob(1, blob);
//            preparedStatement.setLong(2, 1);
//
//            int result = preparedStatement.executeUpdate();
//            connection.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
