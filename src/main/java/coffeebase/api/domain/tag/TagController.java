package coffeebase.api.domain.tag;

import coffeebase.api.domain.tag.model.TagDTO;
import coffeebase.api.domain.tag.service.TagService;
import coffeebase.api.exceptions.processing.ExceptionProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@ExceptionProcessing
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TagDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(tagService.search(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
