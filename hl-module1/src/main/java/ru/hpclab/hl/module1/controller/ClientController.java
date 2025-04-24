package ru.hpclab.hl.module1.controller;

import ru.hpclab.hl.module1.model.Client;
import ru.hpclab.hl.module1.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @GetMapping
    public List<Client> getAll() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getOne(@PathVariable Long id) {
        return clientService.getClient(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
