package org.example.services;

import lombok.AllArgsConstructor;
import org.example.dto.blog.PostDTO;
import org.example.dto.blog.TagForShowPostDTO;
import org.example.mapper.BlogMapper;
import org.example.repositories.PostRepository;
import org.example.repositories.PostTagMapRepository;
import org.example.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService{
    private final PostRepository postRepository;
    private  final PostTagMapRepository postTagMapRepository;
    private  final TagRepository  tagRepository;
    private final BlogMapper blogMapper;
    @Override
    public List<PostDTO> get() {
        var list = new ArrayList<PostDTO>();
        var data = postRepository.findByPublished(true);
        for(var post : data) {
            var postItemDTO = blogMapper.postItemDto(post);

            var tagIdsList = postTagMapRepository.findTagIdsByPostId(post.getId());

            var tagsDTOList = new ArrayList<TagForShowPostDTO>();

            for(var tagId : tagIdsList) {
                var tagOptional = tagRepository.findById(tagId);
                tagOptional.ifPresent(tagEntity -> {
                    var tagDTO = new TagForShowPostDTO();
                    tagDTO.setId(tagEntity.getId());
                    tagDTO.setName(tagEntity.getName());
                    tagsDTOList.add(tagDTO);
                });
            }

            postItemDTO.setTags(tagsDTOList);
            list.add(postItemDTO);
        }
        return list;
    }
}
