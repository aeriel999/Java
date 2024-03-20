package org.example.initializer;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.example.constants.Roles;
import org.example.entities.shop.CategoryEntity;
import org.example.entities.shop.ProductEntity;
import org.example.entities.shop.ProductImageEntity;
import org.example.entities.user.RoleEntity;
import org.example.entities.user.UserEntity;
import org.example.entities.user.UserRoleEntity;
import org.example.repositories.*;
import org.example.services.DatabaseSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@AllArgsConstructor
public class DataInitializationConfig {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    private final DatabaseSeeder databaseSeeder;


    @Bean
    @Transactional
    public CommandLineRunner initData() {
        return args -> {
            if (categoryRepository.count() < 100) {
                addSampleCategories();
            }

            if (productRepository.count() < 100) {
                addSampleProducts();
            }

            seedRole();
            seedUser();

            databaseSeeder.SeedAllTables();
        };
    }
    private void seedRole() {
        if(roleRepository.count() == 0) {
            RoleEntity admin = RoleEntity
                    .builder()
                    .name(Roles.Admin)
                    .build();
            roleRepository.save(admin);
            RoleEntity user = RoleEntity
                    .builder()
                    .name(Roles.User)
                    .build();
            roleRepository.save(user);
        }
    }

    private void seedUser() {
        if(userRepository.count() == 0) {
            var user = UserEntity
                    .builder()
                    .email("admin@gmail.com")
                    .firstName("Микола")
                    .lastName("Підкаблучник")
                    .phone("+380 97 67 56 464")
                    .password(passwordEncoder.encode("123456"))
                    .build();
            userRepository.save(user);
            var role = roleRepository.findByName(Roles.Admin);
            var ur = UserRoleEntity
                    .builder()
                    .role(role)
                    .user(user)
                    .build();
            userRoleRepository.save(ur);
        }
    }
    private void addSampleCategories() {
        Faker faker = new Faker();

        List<CategoryEntity> categories = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> createRandomCategory(faker))
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }

    private void addSampleProducts() {
        Faker faker = new Faker();
        List<CategoryEntity> categories = categoryRepository.findAll();

        List<ProductEntity> products = IntStream.rangeClosed(1, 300)
                .mapToObj(i -> createRandomProduct(faker, categories))
                .collect(Collectors.toList());

        productRepository.saveAll(products);
    }

    private ProductEntity createRandomProduct(Faker faker, List<CategoryEntity> categories) {
        ProductEntity product = new ProductEntity();
        product.setDateCreated(LocalDateTime.now());
        product.setName(faker.food().dish());
        product.setPrice(faker.number().randomDouble(2, 10, 100));
        product.setDescription(faker.lorem().paragraph());
        product.setDelete(false);

        // Assign a random category
        CategoryEntity randomCategory = getRandomCategory(categories);
        product.setCategory(randomCategory);

        // Save the product before associating images
        ProductEntity savedProduct = productRepository.save(product);

        createAndSaveProductImages(savedProduct);

        return savedProduct;
    }


    private CategoryEntity getRandomCategory(List<CategoryEntity> categories) {
        Random random = new Random();
        int randomIndex = random.nextInt(categories.size());
        return categories.get(randomIndex);
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

    private void createAndSaveProductImages(ProductEntity product) {
        // Generate some random images for the product
        Faker faker = new Faker();
        List<ProductImageEntity> productImages = IntStream.rangeClosed(1, 3)
                .mapToObj(i -> createRandomProductImage(product, faker))
                .collect(Collectors.toList());

        // Save the product images
        productImageRepository.saveAll(productImages);
    }

    private ProductImageEntity createRandomProductImage(ProductEntity product, Faker faker) {
        ProductImageEntity productImage = new ProductImageEntity();
        String randomImageUrl = getRandomSampleImage();
        productImage.setName(randomImageUrl);
        productImage.setPriority(faker.random().nextInt(1, 10));
        productImage.setDateCreated(LocalDateTime.now());
        productImage.setProduct(product);

        return productImage;
    }

    private String getRandomSampleImage() {
        // Generate a random image ID (1 to 100)
        int randomImageNumber = new Random().nextInt(100) + 1;

        // Construct the URL for the image using Lorem Picsum API
        return "https://picsum.photos/600/800?image=" + randomImageNumber;
    }

}
