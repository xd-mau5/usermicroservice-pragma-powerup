package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Person;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserUseCaseTest {

    private UserUseCase userUseCase;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userUseCase = new UserUseCase(userPersistencePort);
    }

    @Test
    void saveUser() {
        User user = new User(1L, new Person(1L, "Omar", "Izquierdo", "mau5@mau5.co",
                "+573126791586", "Calle 123", "1", 1234567890L,
                "Test", "10-08-1998"), new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN"));

        userUseCase.saveUser(user);

        verify(userPersistencePort).saveUser(user);
    }

    @Test
    void saveOwner() {
        User user = new User(1L, new Person(1L, "Omar", "Izquierdo", "mau5@mau5.co",
                "+573126791586", "Calle 123", "1", 1234567890L,
                "Test", "10-08-1998"), new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN"));

        userUseCase.saveOwner(user);

        verify(userPersistencePort).saveOwner(user);
    }

    @Test
    void deleteUser() {
        User user = new User(1L, new Person(1L, "Omar", "Izquierdo", "mau5@mau5.co",
                "+573126791586", "Calle 123", "1", 1234567890L,
                "Test", "10-08-1998"), new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN"));

        userUseCase.deleteUser(user);

        verify(userPersistencePort).deleteUser(user);
    }

    @Test
    void getAllProviders() {
        int page = 1;

        List<User> expectedProviders = Collections.singletonList(new User(1L, new Person(1L, "Omar", "Izquierdo", "mau5@mau5.co",
                "+573126791586", "Calle 123", "1", 1234567890L,
                "Test", "10-08-1998"), new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN")));

        when(userPersistencePort.getAllProviders(page)).thenReturn(expectedProviders);

        List<User> actualProviders = userUseCase.getAllProviders(page);

        assertEquals(expectedProviders, actualProviders);
    }

    @Test
    void getProvider() {
        Long id = 1L;

        User expectedProvider = new User(1L, new Person(1L, "Omar", "Izquierdo", "mau5@mau5.co",
                "+573126791586", "Calle 123", "1", 1234567890L,
                "Test", "10-08-1998"), new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN"));

        when(userPersistencePort.getProvider(id)).thenReturn(expectedProvider);

        User actualProvider = userUseCase.getProvider(id);

        assertEquals(expectedProvider, actualProvider);
    }

    @Test
    void getEmployee() {
        Long id = 1L;

        User expectedEmployee = new User(1L, new Person(1L, "Omar", "Izquierdo", "mau5@mau5.co",
                "+573126791586", "Calle 123", "1", 1234567890L,
                "Test", "10-08-1998"), new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN"));

        when(userPersistencePort.getEmployee(id)).thenReturn(expectedEmployee);

        User actualEmployee = userUseCase.getEmployee(id);

        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void getClient() {
        Long id = 1L;

        User expectedClient = new User(1L, new Person(1L, "Omar", "Izquierdo", "mau5@mau5.co",
                "+573126791586", "Calle 123", "1", 1234567890L,
                "Test", "10-08-1998"), new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN"));

        when(userPersistencePort.getClient(id)).thenReturn(expectedClient);

        User actualClient = userUseCase.getClient(id);

        assertEquals(expectedClient, actualClient);
    }
}