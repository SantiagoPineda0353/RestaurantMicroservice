package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserUseCase userUseCase;
    private UserModel userValid;

    @BeforeEach
    void setUp(){
        userValid= new UserModel(null,"Santiago", "Pineda", "1022598694",
                "+57315687459", LocalDate.now().minusYears(21),
                "santiago@test.com","contrasena",0);
    }

    @Test
    void saveOwner_userValid(){
        when(userPersistencePort.existsByEmail(userValid.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userValid.getPassword())).thenReturn("hashed");

        userUseCase.saveOwer(userValid);

        verify(userPersistencePort).saveUser(any(UserModel.class));
        assertEquals("hashed",userValid.getPassword());
    }

    @Test
    void saveOwner_emailInvalid(){
        userValid.setEmail("emailnovalido");
        assertThrows(InvalidEmailException.class, () ->userUseCase.saveOwer(userValid));
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveOwner_cellphoneInvalid(){
        userValid.setCellphone("+312322233212233");
        assertThrows(InvalidCellphoneException.class, () ->userUseCase.saveOwer(userValid));
    }

    @Test
    void saveOwner_documentInvalid(){
        userValid.setDocument("qwerty12");
        assertThrows(InvalidDocumentException.class, () ->userUseCase.saveOwer(userValid));
    }

    @Test
    void saveOwner_ageInvalid(){
        userValid.setBirthDate(LocalDate.now().minusYears(15));
        assertThrows(InvalidUserAgeException.class, () ->userUseCase.saveOwer(userValid));
    }

    @Test
    void saveOwner_emailDuplicate(){
        when(userPersistencePort.existsByEmail(userValid.getEmail())).thenReturn(true);
        assertThrows(InvalidEmailDuplicate.class, () ->userUseCase.saveOwer(userValid));
        verify(userPersistencePort, never()).saveUser(any());
    }

}
