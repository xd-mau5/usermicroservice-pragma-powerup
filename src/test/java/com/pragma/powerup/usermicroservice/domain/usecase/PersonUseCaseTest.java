package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Person;
import com.pragma.powerup.usermicroservice.domain.spi.IPersonPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class PersonUseCaseTest {
    private PersonUseCase personUseCase;

    @Mock
    private IPersonPersistencePort personPersistencePort;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personUseCase = new PersonUseCase(personPersistencePort);
    }

    @Test
    void savePerson() {
        Person person = new Person(1L, "Omar", "Izquierdo", "mau5@mau5.co",
                "+573126791586", "Calle 123", "1", 1234567890L,
                "Test", "10-08-1998");
        personUseCase.savePerson(person);
        verify(personPersistencePort).savePerson(person);
    }
}
