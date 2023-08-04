package coffeebase.api.domain.tag.service;

import coffeebase.api.domain.tag.TagRepository;
import coffeebase.api.domain.tag.model.TagDTO;
import coffeebase.api.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagDTO> getAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return tagRepository.findAllByCreatedByUserId(user.getUserId()).stream()
                .map(tagMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TagDTO> search(String name) {
        if (name.isBlank()) {
            return getAll();
        }
        return searchByName(name);
    }

    public void deleteTag(Long id) {
        tagRepository.findById(id)
                .map(tag -> {
                    tagRepository.deleteById(id);
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("Tag with given id not found"));
        log.info("Delete tag with id: " + id + "CALLED!");
    }

    private List<TagDTO> searchByName(String name) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return tagRepository.findByName(name, user.getUserId()).stream()
                .map(tagMapper::toDTO)
                .collect(Collectors.toList());
    }
}
