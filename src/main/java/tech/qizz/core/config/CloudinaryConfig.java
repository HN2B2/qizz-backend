package tech.qizz.core.config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CloudinaryConfig {

    @Value("${cloudinary.url}")
    private String CLOUDINARY_URL;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
            CLOUDINARY_URL
        );
    }
}
