package br.com.insannity.catalog.controllers;

import br.com.insannity.catalog.payloads.RoleDao;
import br.com.insannity.catalog.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    public ResponseEntity<Page<RoleDao>> findAll(Pageable pageable) {
        Page<RoleDao> list = service.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDao> findById(@PathVariable Long id) {
        RoleDao dao = service.findById(id);
        return ResponseEntity.ok().body(dao);
    }

    @PostMapping
    public ResponseEntity<RoleDao> insertNew(@RequestBody RoleDao dao) {
        RoleDao newdao = service.insertNew(dao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dao.getId()).toUri();
        return ResponseEntity.created(uri).body(newdao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDao> update(@PathVariable Long id, @RequestBody RoleDao dao) {
        RoleDao newdao = service.update(id, dao);
        return ResponseEntity.ok().body(newdao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
