package hexlet.code.app.service;

import hexlet.code.app.dto.LabelsDTO.LabelCreateDTO;
import hexlet.code.app.dto.LabelsDTO.LabelDTO;
import hexlet.code.app.dto.LabelsDTO.LabelUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.LabelMapper;
import hexlet.code.app.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    public List<LabelDTO> findAll() {
        return labelRepository.findAll().stream()
                .map(labelMapper::map)
                .toList();
    }

    public LabelDTO findById(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("label with id " + id + " not found"));
        return labelMapper.map(label);
    }

    public LabelDTO save(LabelCreateDTO labelData) {
        var label = labelMapper.map(labelData);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    public LabelDTO updateById(LabelUpdateDTO labelData, Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("label with id " + id + " not found"));
        labelMapper.update(labelData, label);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    public void deleteById(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("label with id " + id + " not found"));
        labelRepository.deleteById(id);
    }

}
