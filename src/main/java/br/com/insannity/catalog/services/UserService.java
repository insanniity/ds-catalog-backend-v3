package br.com.insannity.catalog.services;

import br.com.insannity.catalog.entities.User;
import br.com.insannity.catalog.exceptions.DatabaseException;
import br.com.insannity.catalog.exceptions.EntityNotFoundException;
import br.com.insannity.catalog.payloads.UserDao;
import br.com.insannity.catalog.payloads.UserInsertDao;
import br.com.insannity.catalog.repositories.RoleRepository;
import br.com.insannity.catalog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDao> findAll(Pageable pageable) {
        Page<User> list = repository.findAll(pageable);
        return list.map(UserDao::new);
    }

    @Transactional(readOnly = true)
    public UserDao findById(Long id) {
        User entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new UserDao(entity);
    }

    @Transactional
    public UserDao insertNew(UserInsertDao dto) {
        User entity = new User();
        copyDtoEntity(dto, entity);
        entity.setPassword(dto.getPassword());
        entity = repository.save(entity);
        return new UserDao(entity);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Id not found: "+id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("User can't be deleted");
        }
    }

    public UserDao update(Long id, UserInsertDao dto) {
        User entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: "+id));
        copyDtoEntity(dto, entity);
        entity = repository.save(entity);
        return new UserDao(entity);
    }

    private void copyDtoEntity (UserDao dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        dto.getRoles().forEach(role -> {
            entity.getRoles().add(roleRepository.findByAuthority(role));
        });
    }

}
