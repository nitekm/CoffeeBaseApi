package coffeebase.api.domain.tag;

import coffeebase.api.domain.tag.model.TagDTO;
import coffeebase.api.domain.tag.model.TagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private static final Logger log = LoggerFactory.getLogger(TagService.class);
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(final TagRepository tagRepository, final TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<TagDTO> search(String name) {
        return tagRepository.findByName(name).stream()
                .map(tagMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteTag(int id) {
        tagRepository.findById(id)
                .map(tag -> {
                    tagRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("Tag with given id not found"));
        log.info("Delete tag with id: " + id + "CALLED!");
    }


}
