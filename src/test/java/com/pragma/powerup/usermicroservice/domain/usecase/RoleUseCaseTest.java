package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RoleUseCaseTest {

    @Mock
    private IRolePersistencePort rolePersistencePort;

    private RoleUseCase roleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleUseCase = new RoleUseCase(rolePersistencePort);
    }

    @Test
    void getAllRoles() {
        // Arrange
        List<Role> expectedRoles = Arrays.asList(
                new Role(1L, "ROLE_ADMIN", "ROLE_ADMIN"),
                new Role(2L, "ROLE_OWNER", "ROLE_OWNER"),
                new Role(3L, "ROLE_EMPLOYEE", "ROLE_EMPLOYEE"),
                new Role(4L, "ROLE_USER", "ROLE_USER")
        );
        when(rolePersistencePort.getAllRoles()).thenReturn(expectedRoles);

        // Act
        List<Role> actualRoles = roleUseCase.getAllRoles();

        // Assert
        assertEquals(expectedRoles, actualRoles);
    }
}
