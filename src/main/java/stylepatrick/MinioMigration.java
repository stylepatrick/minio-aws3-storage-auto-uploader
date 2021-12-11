package stylepatrick;

import stylepatrick.config.AppConfig;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

@Component
public class MinioMigration {

    public MinioMigration(MinioClient minioClient, AppConfig appConfig) {
        System.out.println("Start Upload from " + appConfig.getSourcePath() + " to " + appConfig.getDestinationPath());
        try {
            Stream<Path> paths = Files.walk(Paths.get(appConfig.getSourcePath()));
            paths.filter(Files::isRegularFile).forEach(file -> {
                System.out.println(file.toFile().getAbsolutePath());
                try {
                    byte[] data = Files.readAllBytes(file);
                    String minioPath = appConfig.getDestinationPath() + "/" + (file.getParent().toString().substring(file.getParent().toString().lastIndexOf(appConfig.getPathSyntax()) + 1)) + "/" + file.getFileName();
                    InputStream inputStream = new ByteArrayInputStream(data);
                    ObjectWriteResponse response = minioClient.putObject(
                            PutObjectArgs.builder().bucket(appConfig.getBucketName()).object(minioPath).stream(
                                            inputStream, Files.size(Path.of(file.toFile().getAbsolutePath())), -1)
                                    .build());
                    if (response.etag().isEmpty()) {
                        System.out.println("Error with file: " + minioPath);
                    }
                } catch (IOException | InternalException | XmlParserException | InvalidResponseException | NoSuchAlgorithmException | InvalidKeyException | ErrorResponseException | ServerException | InsufficientDataException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished Upload!");
    }
}
