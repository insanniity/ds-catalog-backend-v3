package br.com.insannity.catalog.services;

import br.com.insannity.catalog.entities.Role;
import br.com.insannity.catalog.exceptions.DatabaseException;
import br.com.insannity.catalog.exceptions.EntityNotFoundException;
import br.com.insannity.catalog.payloads.RoleDao;
import br.com.insannity.catalog.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    @Transactional(readOnly = true)
    public Page<RoleDao> findAll(Pageable pageable) {
        Page<Role> list = repository.findAll(pageable);
        return list.map(RoleDao::new);
    }

    @Transactional(readOnly = true)
    public RoleDao findById(Long id) {
        Role enity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        return new RoleDao(enity);
    }

    @Transactional
    public RoleDao insertNew(RoleDao dto) {
        Role enity = new Role();
        copyDtoEntity(dto, enity);
        enity = repository.save(enity);
        return new RoleDao(enity);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Id not found: "+id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Role can't be deleted");
        }
    }

    public RoleDao update(Long id, RoleDao dto) {
        Role enity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: "+id));
        copyDtoEntity(dto, enity);
        enity = repository.save(enity);
        return new RoleDao(enity);
    }

    private void copyDtoEntity (RoleDao dto, Role entity) {
        entity.setAuthority(dto.getAuthority());
        entity.setDescription(dto.getDescription());
    }

}
