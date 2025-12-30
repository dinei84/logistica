package com.logistica.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    //Endpoint para listar todos os clientes
    @GetMapping
    public List<ClientDTO> getAllClients(){
        List<ClientModel> clients = service.getAllClients();
        return clients.stream()
            .map(client -> new ClientDTO(client.getId(), client.getName(), client.getEmail(), client.getPhone()))
            .toList();
    }

    //Endpoint para listar Cliente por Id
    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id) {
        return service.getClientById(id);
    }

    //Endpoint para criar novo Cliente
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {        
        ClientDTO createdClient = service.createClient(clientDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdClient.id())
            .toUri();
        return ResponseEntity.created(location).body(createdClient);
    }

    //Endpoint para atualizar Cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updateClient = service.updateClient(id, clientDTO);
        return ResponseEntity.ok(updateClient);
    }

    //Endpoint para deletar Cliente
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        service.deleteClient(id);
    }
}
