package br.com.insannity.catalog.controllers;

import br.com.insannity.catalog.payloads.UserDao;
import br.com.insannity.catalog.payloads.UserInsertDao;
import br.com.insannity.catalog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<Page<UserDao>> findAll(Pageable pageable) {
        Page<UserDao> list = service.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDao> findById(@PathVariable Long id) {
        UserDao dao = service.findById(id);
        return ResponseEntity.ok().body(dao);
    }

    @PostMapping
    public ResponseEntity<UserDao> insertNew(@Valid @RequestBody UserInsertDao dao) {
        UserDao newdao = service.insertNew(dao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dao.getId()).toUri();
        return ResponseEntity.created(uri).body(newdao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDao> update(@PathVariable Long id,@Valid @RequestBody UserInsertDao dao) {
        UserDao newdao = service.update(id, dao);
        return ResponseEntity.ok().body(newdao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
