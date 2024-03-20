package org.example.services;

import com.github.javafaker.Faker;
import org.example.entities.blog.BlogCategoryEntity;
import org.example.entities.blog.PostEntity;
import org.example.entities.blog.PostTagMap;
import org.example.entities.blog.TagEntity;
import org.example.repositories.BlogCategoryRepository;
import org.example.repositories.PostRepository;
import org.example.repositories.PostTagMapRepository;
import org.example.repositories.TagRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class DatabaseSeeder {
    private final Faker faker;
    private final BlogCategoryRepository blogCategoryRepository;
    private final PostRepository postRepository;
    private final PostTagMapRepository postTagMapRepository;
    private final TagRepository tagRepository;

    public DatabaseSeeder(BlogCategoryRepository blogCategoryRepository,
                          PostRepository postRepository,
                          PostTagMapRepository postTagMapRepository,
                          TagRepository tagRepository,
                          PasswordEncoder passwordEncoder) {
        this.blogCategoryRepository = blogCategoryRepository;
        this.postRepository = postRepository;
        this.postTagMapRepository = postTagMapRepository;
        this.tagRepository = tagRepository;
        faker = new Faker(new Locale("uk"));
    }

    public void SeedAllTables() {
        seedCategories(10);
        seedTags(10);
        generatePosts(20);
        generatePostTags(5);
    }
    private void seedCategories(int n) {
        if (blogCategoryRepository.count() < n) {
            for (int i = 0; i < n; i++) {
                BlogCategoryEntity category = new BlogCategoryEntity();
                category.setName(faker.commerce().department());
                category.setUrlSlug(UrlSlugGenerator.generateUrlSlug(category.getName()));
                category.setDescription(faker.lorem().word());
                blogCategoryRepository.save(category);
            }
        }
    }

    private void seedTags(int n) {
        if (tagRepository.count() < n) {
            for (int i = 0; i < n; i++) {
                TagEntity tag = new TagEntity();
                tag.setName(faker.lorem().word());
                tag.setUrlSlug(UrlSlugGenerator.generateUrlSlug(tag.getName()));
                tag.setDescription(faker.lorem().word());
                tagRepository.save(tag);
            }
        }
    }

    public void generatePosts(int count) {
        if (postRepository.count() < count) {
            var categories = blogCategoryRepository.findAll();
            for (int i = 0; i < count; i++) {
                PostEntity post = new PostEntity();
                post.setTitle(faker.lorem().characters(10, 30));
                post.setShortDescription(faker.lorem().characters(20, 50));
                post.setDescription(faker.lorem().characters(100, 150));
                post.setMeta(faker.lorem().characters(20, 30));
                post.setUrlSlug(faker.lorem().characters(5, 10));
                post.setPublished(faker.random().nextBoolean());
                post.setPostedOn(LocalDateTime.now());
                post.setBlogCategory(categories.get(faker.random().nextInt(categories.size())));
                postRepository.save(post);
            }
        }
    }

    public void generatePostTags(int count) {
        if (postTagMapRepository.count() < count) {
            var posts = postRepository.findAll();
            var tags = tagRepository.findAll();
            for (int i = 0; i < count; i++) {
                PostTagMap postTag = new PostTagMap();
                postTag.setTag(tags.get(faker.random().nextInt(tags.size())));
                postTag.setPost(posts.get(faker.random().nextInt(posts.size())));
                postTagMapRepository.save(postTag);
            }
        }
    }

}
