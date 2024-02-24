package org.example.initializer;

import com.github.javafaker.Faker;
import org.example.entities.CategoryEntity;
import org.example.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class DataInitializationConfig {

    @Autowired
    private CategoryRepository categoryRepository;

    @Bean
    @Transactional
    public CommandLineRunner initData() {
        return args -> {
            if (categoryRepository.count() == 0) {
                addSampleCategories();
            }
        };
    }

    private void addSampleCategories() {
        Faker faker = new Faker();

        List<CategoryEntity> categories = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> createRandomCategory(faker))
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }

    private CategoryEntity createRandomCategory(Faker faker) {
        CategoryEntity category = new CategoryEntity();
        category.setCreationTime(LocalDateTime.now());
        category.setName(faker.food().ingredient());

        // Modify to use a random sample image from the resources
        String randomImageUrl = getRandomSampleImage();
        category.setImage(randomImageUrl);

        category.setDescription(faker.lorem().paragraph());
        return category;
    }

    private String getRandomSampleImage() {
        // Generate a random image ID (1 to 100)
        int randomImageNumber = new Random().nextInt(100) + 1;

        // Construct the URL for the image using Lorem Picsum API
        return "https://picsum.photos/200/300?image=" + randomImageNumber;
    }

}
