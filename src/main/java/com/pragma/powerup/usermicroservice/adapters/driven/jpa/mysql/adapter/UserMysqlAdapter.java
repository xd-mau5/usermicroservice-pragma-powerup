package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.*;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IPersonRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IRoleRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.domain.model.Role;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pragma.powerup.usermicroservice.configuration.Constants.*;

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
    public void checkAge(User user){
        String email = String.valueOf(personRepository.findById(user.getPerson().getId()).get().getMail());
        if (email == null) {
            throw new MailIsNullException();
        } else {
            String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new MailNotValidException();
            }
        }
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate birthDate = LocalDate.parse(personRepository.findById(user.getPerson().getId()).get().getBirthDate(), formatter);
        Period period = Period.between(birthDate, currentDate);
        int age = period.getYears();
        if (age < 18) {
            throw new OwnerIsNotOver18Exception();
        }
    }
    @Override
    public void saveOwner(User user) {
        if (user.getRole().getId().equals(PROVIDER_ROLE_ID))
        {
            throw new RoleNotAllowedForCreationException();
        }
        if (user.getRole().getId().equals(USER_ROLE_ID))
        {
            throw new RoleNotAllowedForCreationException();
        }
        if (userRepository.findByPersonEntityIdAndRoleEntityId(user.getPerson().getId(), user.getRole().getId()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        checkAge(user);
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
        UserEntity userEntity = userRepository.findByPersonEntityIdAndRoleEntityId(id, OWNER_ROLE_ID).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public User getClient(Long id) {
        UserEntity userEntity = userRepository.findByPersonEntityIdAndRoleEntityId(id, ADMIN_ROLE_ID).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUser(userEntity);
    }
}
