package stylepatrick.config;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MinioConfig {

    private final AppConfig appConfig;

    @Bean
    public MinioClient generateMinioClient() {
        return MinioClient.builder()
                .endpoint(appConfig.getMinioUrl())
                .credentials(appConfig.getAccessKey(), appConfig.getAccessSecret())
                .build();

    }
}
