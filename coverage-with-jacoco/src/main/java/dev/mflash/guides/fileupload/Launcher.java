package dev.mflash.guides.fileupload;

import dev.mflash.guides.fileupload.configuration.StorageProperties;
import dev.mflash.guides.fileupload.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties(StorageProperties.class)
public @SpringBootApplication class Launcher implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(Launcher.class, args);
  }

  @Bean CommandLineRunner init(StorageService storageService) {
    return (args) -> {
      storageService.deleteAll();
      storageService.init();
    };
  }

  public @Override void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("http://localhost:4200");
  }
}
