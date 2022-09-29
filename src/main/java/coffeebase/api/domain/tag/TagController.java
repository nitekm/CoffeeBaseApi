package coffeebase.api.domain.tag;

import coffeebase.api.domain.tag.model.TagDTO;
import coffeebase.api.exception.IllegalExceptionProcessing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@IllegalExceptionProcessing
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(final TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<TagDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(tagService.search(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
