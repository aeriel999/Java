package org.example;

import org.example.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example", "package-where-SwaggerConfig-is-located"})
@EnableConfigurationProperties(StorageProperties.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//    @Bean
//    CommandLineRunner runner(CategoryRepository categoryRepository, StorageService storageService) {
//        return args -> {
//            storageService.init();
//
//            CategoryEntity category = new CategoryEntity();
//            category.setName("Міні пігі");
//            category.setDescription("Для дорослих людей");
//            category.setImage("pig.jpg");
//            category.setCreationTime(LocalDateTime.now());
//
//            categoryRepository.save(category);
//        };
//    }
}