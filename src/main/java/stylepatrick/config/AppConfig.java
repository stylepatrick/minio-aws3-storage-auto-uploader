package stylepatrick.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter(AccessLevel.PROTECTED)
public class AppConfig {

    @Value("${minio.path.syntax}")
    private String pathSyntax;

    @Value("${minio.destination.path}")
    private String destinationPath;

    @Value("${minio.source.path}")
    private String sourcePath;

    @Value("${minio.access.name}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String accessSecret;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucket.name}")
    private String bucketName;

}
