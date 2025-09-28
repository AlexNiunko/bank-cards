package com.example.bankcards.service;

import com.example.bankcards.dto.request.RegistrationUserRequestDto;
import com.example.bankcards.dto.request.UpdateUserStatusRequestDto;
import com.example.bankcards.dto.response.FullUserResponseDto;
import com.example.bankcards.dto.response.UserResponseDto;
import com.example.bankcards.entity.ProfileStatus;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.UserInfo;
import com.example.bankcards.entity.Users;
import com.example.bankcards.exception.BusinessException;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.bankcards.util.encryption.ExceptionMessage.ROLE_NOT_EXIST;
import static com.example.bankcards.util.encryption.ExceptionMessage.USER_NOT_EXIST_BY_LOGIN;
import static com.example.bankcards.util.encryption.ExceptionMessage.USER_NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_WithValidData_ShouldCreateUserSuccessfully() {
        // Given
        RegistrationUserRequestDto requestDto = createRegistrationRequest();
        Role userRole = createUserRole();
        Users savedUser = createTestUser();

        when(userRepository.findByLogin("test@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        // When
        UserResponseDto result = userService.createUser(requestDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.userId());
        assertEquals("John", result.firstname());
        assertEquals("Doe", result.lastName());

        verify(userRepository).findByLogin("test@example.com");
        verify(roleRepository).findByName("USER");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(Users.class));
    }

    @Test
    void createUser_WithExistingLogin_ShouldThrowBusinessException() {
        // Given
        RegistrationUserRequestDto requestDto = createRegistrationRequest();
        Users existingUser = createTestUser();

        when(userRepository.findByLogin("test@example.com")).thenReturn(Optional.of(existingUser));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userService.createUser(requestDto));

        assertTrue(exception.getMessage().contains(String.format(USER_NOT_EXIST_BY_LOGIN, existingUser.getLogin())));
        verify(roleRepository, never()).findByName(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_WhenUserRoleNotFound_ShouldThrowBusinessException() {
        // Given
        RegistrationUserRequestDto requestDto = createRegistrationRequest();

        when(userRepository.findByLogin("test@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userService.createUser(requestDto));

        assertTrue(exception.getMessage().contains(String.format(ROLE_NOT_EXIST, "USER")));
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldEncodePassword() {
        // Given
        RegistrationUserRequestDto requestDto = createRegistrationRequest();
        Role userRole = createUserRole();
        Users savedUser = createTestUser();

        when(userRepository.findByLogin("test@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        // When
        userService.createUser(requestDto);

        // Then
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(argThat(user ->
            user.getPassword().equals("encodedPassword123")
        ));
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Given
        Users user1 = createTestUser();
        Users user2 = createTestUser();
        user2.setId(2L);
        user2.getUserInfo().setFirstname("Jane");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // When
        List<FullUserResponseDto> result = userService.getAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        FullUserResponseDto firstUser = result.get(0);
        assertEquals(1L, firstUser.userId());
        assertEquals("test@example.com", firstUser.login());
        assertEquals("John", firstUser.firstname());
        assertEquals("Doe", firstUser.lastname());
        assertEquals(List.of("USER"), firstUser.roles());
        assertEquals(ProfileStatus.ACTIVE, firstUser.status());

        verify(userRepository).findAll();
    }

    @Test
    void getAllUsers_WhenNoUsers_ShouldReturnEmptyList() {
        // Given
        when(userRepository.findAll()).thenReturn(List.of());

        // When
        List<FullUserResponseDto> result = userService.getAllUsers();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void deleteUser_WithExistingUser_ShouldDeleteSuccessfully() {
        // Given
        Long userId = 1L;
        Users user = createTestUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        // When
        UserResponseDto result = userService.deleteUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.userId());
        assertEquals("John", result.firstname());
        assertEquals("Doe", result.lastName());

        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_WithNonExistentUser_ShouldThrowBusinessException() {
        // Given
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userService.deleteUser(userId));

        assertTrue(exception.getMessage().contains(String.format(USER_NOT_FOUND_BY_ID, userId)));
        verify(userRepository, never()).delete(any());
    }

    @Test
    void updateUserStatus_WithValidData_ShouldUpdateSuccessfully() {
        // Given
        Long userId = 1L;
        UpdateUserStatusRequestDto requestDto =
            new UpdateUserStatusRequestDto(userId, "BLOCKED");

        Users user = createTestUser();
        user.setStatus(ProfileStatus.ACTIVE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        UserResponseDto result = userService.updateUserStatus(requestDto);

        // Then
        assertNotNull(result);
        assertEquals(ProfileStatus.BLOCKED, user.getStatus());
        assertEquals(userId, result.userId());

        verify(userRepository).findById(userId);
    }

    @Test
    void updateUserStatus_WithNonExistentUser_ShouldThrowBusinessException() {
        // Given
        Long userId = 999L;
        UpdateUserStatusRequestDto requestDto =
            new UpdateUserStatusRequestDto(userId, "BLOCKED");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
            () -> userService.updateUserStatus(requestDto));

        assertTrue(exception.getMessage().contains(String.format(USER_NOT_FOUND_BY_ID, userId)));
    }

    @Test
    void updateUserStatus_WithInvalidStatus_ShouldThrowException() {
        // Given
        Long userId = 1L;
        UpdateUserStatusRequestDto requestDto =
            new UpdateUserStatusRequestDto(userId, "INVALID_STATUS");

        Users user = createTestUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When & Then
        assertThrows(IllegalArgumentException.class,
            () -> userService.updateUserStatus(requestDto));
    }

    private RegistrationUserRequestDto createRegistrationRequest() {
        return new RegistrationUserRequestDto(
            "test@example.com",
            "password123",
            "USER",
            "John",
            "Doe",
            LocalDate.of(1990, 1, 1),
            "+375291234567"
        );
    }

    private Users createTestUser() {
        UserInfo userInfo = UserInfo.builder()
            .firstname("John")
            .lastname("Doe")
            .birthDate(LocalDate.of(1990, 1, 1))
            .phoneNumber("+375291234567")
            .build();

        Role userRole = createUserRole();

        return Users.builder()
            .id(1L)
            .login("test@example.com")
            .password("encodedPassword")
            .status(ProfileStatus.ACTIVE)
            .userInfo(userInfo)
            .roles(List.of(userRole))
            .build();
    }

    private Role createUserRole() {
        return Role.builder()
            .Id(1)
            .name("USER")
            .build();
    }
}
