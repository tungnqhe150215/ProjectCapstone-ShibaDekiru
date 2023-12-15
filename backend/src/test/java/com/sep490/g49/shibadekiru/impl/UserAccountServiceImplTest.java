import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.UserAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sep490.g49.shibadekiru.dto.UserAccountRegisterDto;
import com.sep490.g49.shibadekiru.entity.Role;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.repository.RoleRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;

@ExtendWith(MockitoExtension.class)
public class UserAccountServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserAccountServiceImpl userAccountService;

    @Test
    public void testCreateUserAccount_WhenExistingUserPresent_ThrowsIllegalStateException() {
        // Arrange
        UserAccountRegisterDto userAccountDto = new UserAccountRegisterDto();
        userAccountDto.setEmail("test@example.com");
        userAccountDto.setMemberId("123456");
        userAccountDto.setRoleId(1L);

        UserAccount existingUser = new UserAccount();
        Optional<UserAccount> existingUserOptional = Optional.of(existingUser);
        when(userAccountRepository.findByEmailOrMemberId(userAccountDto.getEmail(), userAccountDto.getMemberId()))
                .thenReturn(existingUserOptional);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            userAccountService.createUserAccount(userAccountDto);
        });
    }

    @Test
    public void testCreateUserAccount_WhenRoleNotPresent_ThrowsResourceNotFoundException() {
        // Arrange
        UserAccountRegisterDto userAccountDto = new UserAccountRegisterDto();
        userAccountDto.setEmail("test@example.com");
        userAccountDto.setMemberId("123456");
        userAccountDto.setRoleId(1L);

        Optional<Role> roleOptional = Optional.empty();
        when(roleRepository.findById(userAccountDto.getRoleId())).thenReturn(roleOptional);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            userAccountService.createUserAccount(userAccountDto);
        });
    }

//    @Test
//    public void testCreateUserAccount_WhenValidParameters_ReturnsSavedUserAccount() {
//        // Arrange
//        UserAccountRegisterDto userAccountDto = new UserAccountRegisterDto();
//        userAccountDto.setEmail("test@example.com");
//        userAccountDto.setMemberId("123456");
//        userAccountDto.setRoleId(1L);
//
//        Optional<Role> roleOptional = Optional.of(new Role());
//        when(roleRepository.findById(userAccountDto.getRoleId())).thenReturn(roleOptional);
//
//        when(userAccountRepository.findByEmailOrMemberId(userAccountDto.getEmail(), userAccountDto.getMemberId()))
//                .thenReturn(Optional.empty());
//
//        UserAccount savedUser = new UserAccount();
//        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(savedUser);
//
//        // Act
//        userAccountService.createUserAccount(userAccountDto);
//
//        // Assert
//        verify(userAccountRepository).findByEmailOrMemberId(userAccountDto.getEmail(), userAccountDto.getMemberId());
//        verify(roleRepository).findById(userAccountDto.getRoleId());
//        verify(userAccountRepository).save(any(UserAccount.class));
//    }

}