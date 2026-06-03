package com.logistica.administrator;

import com.logistica.controlelog.exception.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministratorService {

    public final AdministratorRepository repository;

    public AdministratorService(AdministratorRepository repository) {
        this.repository = repository;
    }

    //Adicionar um novo administrador
    public AdministratorDTO addAdministrator(AdministratorDTO administrator) {
        AdministratorModel model = toModel(administrator);
        AdministratorModel saved = repository.save(model);
        return toDTO(saved);
    }

    //Obter um administrador por ID
    public AdministratorDTO getAdministratorById(Long id){
        AdministratorModel model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado"));
        return toDTO(model);
    }

    //Obter todos os administradores
    public List<AdministratorDTO> getAllAdministrators(Pageable pageable) {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    //Atualizar um administrador existente
    public AdministratorDTO updateAdministrator(Long id, AdministratorDTO administrator) {
        AdministratorModel model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado"));
        model.setNome(administrator.name());
        AdministratorModel saved = repository.save(model);
        return toDTO(saved);
    }

    //Excluir um administrador por ID
    public void deleteAdministrator(Long id) {
        repository.deleteById(id);
    }

    // Mapeadores entre DTO e Model
    private AdministratorModel toModel(AdministratorDTO dto) {
        if (dto == null) return null;
        return new AdministratorModel(dto.id(), dto.name(), null); // User is not updated directly via this DTO
    }

    private AdministratorDTO toDTO(AdministratorModel model) {
        if (model == null) return null;
        return new AdministratorDTO(model.getId(), model.getNome());
    }
}
