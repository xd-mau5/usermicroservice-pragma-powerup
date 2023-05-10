package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.*;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IPersonRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IRoleRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.pragma.powerup.usermicroservice.configuration.Constants.CLIENT_ROLE_ID;
import static com.pragma.powerup.usermicroservice.configuration.Constants.EMPLOYEE_ROLE_ID;
import static com.pragma.powerup.usermicroservice.configuration.Constants.MAX_PAGE_SIZE;
import static com.pragma.powerup.usermicroservice.configuration.Constants.PROVIDER_ROLE_ID;

@RequiredArgsConstructor
@Transactional
public class UserMysqlAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IPersonRepository personRepository;
    private final IRoleRepository roleRepository;
    private final IUserEntityMapper userEntityMapper;
    @Override
    public void saveUser(User user) {
        if (user.getRole().getId().equals(PROVIDER_ROLE_ID))
        {
            throw new RoleNotAllowedForCreationException();
        }
        if (userRepository.findByPersonEntityIdAndRoleEntityId(user.getPerson().getId(), user.getRole().getId()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        personRepository.findById(user.getPerson().getId()).orElseThrow(PersonNotFoundException::new);
        roleRepository.findById(user.getRole().getId()).orElseThrow(RoleNotFoundException::new);
        userRepository.save(userEntityMapper.toEntity(user));
    }
    @Override
    public void saveOwner(User user) {
        if (user.getRole().getId().equals(PROVIDER_ROLE_ID))
        {
            throw new RoleNotAllowedForCreationException();
        }
        if (userRepository.findByPersonEntityIdAndRoleEntityId(user.getPerson().getId(), user.getRole().getId()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(personRepository.findById(user.getPerson().getId()).get().getBirthDate(), formatter);
        Period period = Period.between(birthDate, currentDate);
        int age = period.getYears();
        if (age < 18) {
            throw new OwnerIsNotOver18();
        }
        personRepository.findById(user.getPerson().getId()).orElseThrow(PersonNotFoundException::new);
        roleRepository.findById(user.getRole().getId()).orElseThrow(RoleNotFoundException::new);
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public void deleteUser(User user) {
        if (userRepository.findByPersonEntityIdAndRoleEntityId(user.getPerson().getId(), user.getRole().getId()).isPresent()) {
            userRepository.deleteByPersonEntityIdAndRoleEntityId(user.getPerson().getId(), user.getRole().getId());
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> getAllProviders(int page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        List<UserEntity> userEntityList = userRepository.findAllByRoleEntityId(PROVIDER_ROLE_ID, pagination);
        if (userEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userEntityMapper.toUserList(userEntityList);
    }

    @Override
    public User getProvider(Long id) {
        UserEntity userEntity = userRepository.findByPersonEntityIdAndRoleEntityId(id, PROVIDER_ROLE_ID).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public User getEmployee(Long id) {
        UserEntity userEntity = userRepository.findByPersonEntityIdAndRoleEntityId(id, EMPLOYEE_ROLE_ID).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public User getClient(Long id) {
        UserEntity userEntity = userRepository.findByPersonEntityIdAndRoleEntityId(id, CLIENT_ROLE_ID).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }
}
