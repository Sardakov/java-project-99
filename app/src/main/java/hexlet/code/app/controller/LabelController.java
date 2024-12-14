package hexlet.code.app.controller;

import hexlet.code.app.dto.LabelsDTO.LabelCreateDTO;
import hexlet.code.app.dto.LabelsDTO.LabelDTO;
import hexlet.code.app.dto.LabelsDTO.LabelUpdateDTO;
import hexlet.code.app.service.LabelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
@AllArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @GetMapping()
    public ResponseEntity<List<LabelDTO>> index() {
        var result = labelService.findAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping("/{id}")
    public LabelDTO show(@PathVariable Long id) {
        return labelService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO create(@Valid @RequestBody LabelCreateDTO labelData) {
        return labelService.save(labelData);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO update(@RequestBody LabelUpdateDTO labelData, @PathVariable Long id) {
        return labelService.updateById(labelData, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        labelService.deleteById(id);
    }




}
