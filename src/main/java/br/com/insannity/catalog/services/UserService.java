package br.com.insannity.catalog.services;

import br.com.insannity.catalog.entities.User;
import br.com.insannity.catalog.exceptions.DatabaseException;
import br.com.insannity.catalog.exceptions.EntityNotFoundException;
import br.com.insannity.catalog.payloads.UserDao;
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
        User enity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new UserDao(enity);
    }

    @Transactional
    public UserDao insertNew(UserDao dto) {
        User enity = new User();
        copyDtoEntity(dto, enity);
        enity = repository.save(enity);
        return new UserDao(enity);
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

    public UserDao update(Long id, UserDao dto) {
        User enity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: "+id));
        copyDtoEntity(dto, enity);
        enity = repository.save(enity);
        return new UserDao(enity);
    }

    private void copyDtoEntity (UserDao dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        dto.getRoles().forEach(role -> {
            entity.getRoles().add(roleRepository.findByAuthority(role));
        });
    }

}
