package com.logistica.collaborator;

import com.logistica.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollaboratorService {

    public final CollaboratorRepository repository;

    public CollaboratorService(CollaboratorRepository repository) {
        this.repository = repository;
    }

    //Adicionar um novo colaborador
    public CollaboratorDTO addCollaborator(CollaboratorDTO collaborator) {
        CollaboratorModel model = toModel(collaborator);
        CollaboratorModel saved = repository.save(model);
        return toDTO(saved);
    }

    //Obter um colaborador por ID
    public CollaboratorDTO getCollaboratorById(Long id){
        CollaboratorModel model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
        return toDTO(model);
    }

    //Obter todos os colaboradores
    public List<CollaboratorDTO> getAllCollaborators(Pageable pageable) {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    //Atualizar um colaborador existente
    public CollaboratorDTO updateCollaborator(Long id, CollaboratorDTO collaborator) {
        CollaboratorModel model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colaborador não encontrado"));
        model.setNome(collaborator.name());
        CollaboratorModel saved = repository.save(model);
        return toDTO(saved);
    }

    //Excluir um colaborador por ID
    public void deleteCollaborator(Long id) {
        repository.deleteById(id);
    }

    // Mapeadores entre DTO e Model
    private CollaboratorModel toModel(CollaboratorDTO dto) {
        if (dto == null) return null;
        return new CollaboratorModel(dto.id(), dto.name(), null);
    }

    private CollaboratorDTO toDTO(CollaboratorModel model) {
        if (model == null) return null;
        return new CollaboratorDTO(model.getId(), model.getNome());
    }
}


