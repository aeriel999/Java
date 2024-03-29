package org.example.services;

import lombok.AllArgsConstructor;
import org.example.dto.blog.PostDTO;
import org.example.dto.blog.PostListByCategoryDTO;
import org.example.dto.blog.PostListByTag;
import org.example.dto.blog.TagForShowPostDTO;
import org.example.entities.blog.BlogCategoryEntity;
import org.example.entities.blog.PostEntity;
import org.example.entities.blog.TagEntity;
import org.example.mapper.BlogMapper;
import org.example.repositories.BlogCategoryRepository;
import org.example.repositories.PostRepository;
import org.example.repositories.PostTagMapRepository;
import org.example.repositories.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService{
    private final PostRepository postRepository;
    private  final PostTagMapRepository postTagMapRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private  final TagRepository  tagRepository;
    private final BlogMapper blogMapper;

    @Override
    public Page<PostDTO> getPostsByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("postedOn").descending());
        Page<PostEntity> postPage = postRepository.findByPublished(true, pageable);

        return postPage.map(post -> {
            List<Integer> tagIdsList = postTagMapRepository.findTagIdsByPostId(post.getId());
            List<TagForShowPostDTO> tagsDTOList = tagIdsList.stream()
                    .map(tagId -> {
                        Optional<TagEntity> tagOptional = tagRepository.findById(tagId);
                        return tagOptional.map(tagEntity -> {
                            TagForShowPostDTO tagDTO = new TagForShowPostDTO();
                            tagDTO.setId(tagEntity.getId());
                            tagDTO.setName(tagEntity.getName());
                            tagDTO.setUrlSlug(tagEntity.getUrlSlug());
                            return tagDTO;
                        }).orElse(null);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            PostDTO postItemDTO = blogMapper.postItemDto(post);
            postItemDTO.setTags(tagsDTOList);

            // Fetch categoryUrlSlug based on categoryId
            String categoryUrlSlug = post.getBlogCategory().getUrlSlug(); // Assuming the field name is getUrlSlug()
            postItemDTO.setCategoryUrlSlug(categoryUrlSlug);

            return postItemDTO;
        });
    }

    @Override
    public PostListByCategoryDTO getPostsByCategoryId(int categoryId, int page, int pageSize) {
        // Create a pageable object with sorting by postedOn date in descending order
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("postedOn").descending());

        // Fetch posts for the specified category sorted by postedOn date
        Page<PostEntity> postPage = postRepository.findByBlogCategory_Id(categoryId, pageable);

        // Map the PostEntity objects to PostDTO and fetch total count
        List<PostDTO> postDTOList = postPage.map(post -> {
            // Fetch tags for the post
            List<Integer> tagIdsList = postTagMapRepository.findTagIdsByPostId(post.getId());
            List<TagForShowPostDTO> tagsDTOList = tagIdsList.stream()
                    .map(tagId -> {
                        Optional<TagEntity> tagOptional = tagRepository.findById(tagId);
                        return tagOptional.map(tagEntity -> {
                            TagForShowPostDTO tagDTO = new TagForShowPostDTO();
                            tagDTO.setId(tagEntity.getId());
                            tagDTO.setName(tagEntity.getName());
                            tagDTO.setUrlSlug(tagEntity.getUrlSlug());
                            return tagDTO;
                        }).orElse(null);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Map the PostEntity to PostDTO
            PostDTO postDTO = blogMapper.postItemDto(post);
            // Set the tagsDTOList in the PostDTO
            postDTO.setTags(tagsDTOList);

            String categoryUrlSlug = post.getBlogCategory().getUrlSlug(); // Assuming the field name is getUrlSlug()
            postDTO.setCategoryUrlSlug(categoryUrlSlug);

            return postDTO;
        }).getContent();

        // Retrieve category details based on categoryId
        Optional<BlogCategoryEntity> categoryOptional = blogCategoryRepository.findById(categoryId);
        String categoryName = categoryOptional.map(BlogCategoryEntity::getName).orElse(null);
        String categoryDescription = categoryOptional.map(BlogCategoryEntity::getDescription).orElse(null);

        return new PostListByCategoryDTO(postDTOList, (int)postPage.getTotalElements(), categoryName, categoryDescription);
    }

    @Override
    public PostListByTag getPostsByTagId(int tagId, int page, int pageSize) {
        // Create a pageable object with sorting by postedOn date in descending order
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("postedOn").descending());

        // Fetch posts for the specified tag sorted by postedOn date
        Page<PostEntity> postPage = postRepository.findByTags_TagId(tagId, pageable);

        // Retrieve tag information
        Optional<TagEntity> tagOptional = Optional.ofNullable(tagRepository.findById(tagId));
        String tagName = tagOptional.map(TagEntity::getName).orElse(null);
        String tagDescription = tagOptional.map(TagEntity::getDescription).orElse(null);

        // Map the PostEntity objects to PostDTO and fetch total count
        List<PostDTO> postDTOList = postPage.map(post -> {
            // Fetch tags for the post
            List<Integer> tagIdsList = postTagMapRepository.findTagIdsByPostId(post.getId());
            List<TagForShowPostDTO> tagsDTOList = tagIdsList.stream()
                    .map(tagIdItem -> {
                        Optional<TagEntity> tagOptionalItem = tagRepository.findById(tagIdItem);
                        return tagOptionalItem.map(tagEntity -> {
                            TagForShowPostDTO tagDTO = new TagForShowPostDTO();
                            tagDTO.setId(tagEntity.getId());
                            tagDTO.setName(tagEntity.getName());
                            return tagDTO;
                        }).orElse(null);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Map the PostEntity to PostDTO
            PostDTO postDTO = blogMapper.postItemDto(post);
            // Set the tagsDTOList in the PostDTO
            postDTO.setTags(tagsDTOList);

            String categoryUrlSlug = post.getBlogCategory().getUrlSlug(); // Assuming the field name is getUrlSlug()
            postDTO.setCategoryUrlSlug(categoryUrlSlug);

            return postDTO;
        }).getContent();

        return new PostListByTag(postDTOList, (int) postPage.getTotalElements(), tagName, tagDescription);
    }



    public Optional<PostDTO> getPostById(int postId) {
        Optional<PostEntity> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            PostEntity postEntity = optionalPost.get();
            PostDTO postDto = blogMapper.postItemDto(postEntity);

            // Fetch tags for the post
            var tagIdsList = postTagMapRepository.findTagIdsByPostId(postEntity.getId());
            var tagsDTOList = new ArrayList<TagForShowPostDTO>();
            for (var tagId : tagIdsList) {
                var tagOptional = tagRepository.findById(tagId);
                tagOptional.ifPresent(tagEntity -> {
                    var tagDTO = new TagForShowPostDTO();
                    tagDTO.setId(tagEntity.getId());
                    tagDTO.setName(tagEntity.getName());
                    tagDTO.setUrlSlug(tagEntity.getUrlSlug());
                    tagsDTOList.add(tagDTO);
                });
            }
            postDto.setTags(tagsDTOList);

            String categoryUrlSlug = postEntity.getBlogCategory().getUrlSlug(); // Assuming the field name is getUrlSlug()
            postDto.setCategoryUrlSlug(categoryUrlSlug);

            return Optional.of(postDto);
        } else {
            return Optional.empty(); // Return an empty Optional if the post is not found
        }
    }
}
