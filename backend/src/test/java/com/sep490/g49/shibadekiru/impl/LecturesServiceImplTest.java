import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.LecturesServiceImpl;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LecturesServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private LecturesServiceImpl lecturesService;

//    @Test
//    void createLecturerFromUserAccount_Success() {
//        // Arrange
//        LecturesDto inputDto = new LecturesDto();
//        inputDto.setMemberId("testMemberId");
//        inputDto.setFirstName("John");
//        inputDto.setLastName("Doe");
//
//        UserAccount mockUserAccount = new UserAccount();
//        when(userAccountRepository.findByMemberId("testMemberId")).thenReturn(mockUserAccount);
//
//        Lectures mockLectures = new Lectures();
//        when(modelMapper.map(inputDto, Lectures.class)).thenReturn(mockLectures);
//        when(lecturersRepository.save(any(Lectures.class))).thenReturn(mockLectures);
//
//        // Act
//        LecturesDto resultDto = lecturesService.createLecturerFromUserAccount(inputDto);
//
//        // Assert
//        assertThat(resultDto).isNotNull();
//        assertThat(resultDto.getFirstName()).isEqualTo("John");
//        assertThat(resultDto.getLastName()).isEqualTo("Doe");
//    }

    @Test
    void getLectureById_Success() {
        // Arrange
        long existingLectureId = 1L;
        Lectures mockLecture = new Lectures();
        when(lecturersRepository.findById(existingLectureId)).thenReturn(java.util.Optional.ofNullable(mockLecture));

        // Act
        Lectures resultLecture = lecturesService.getLectureById(existingLectureId);

        // Assert
        assertThat(resultLecture).isSameAs(mockLecture);
    }

    @Test
    void getLectureById_ThrowsException_WhenLectureNotFound() {
        // Arrange
        long nonExistingLectureId = 2L;
        when(lecturersRepository.findById(nonExistingLectureId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> lecturesService.getLectureById(nonExistingLectureId));
    }

    @Test
    void getByUserAccount_Success() {
        // Arrange
        UserAccount mockUserAccount = new UserAccount();
        Lectures mockLecture = new Lectures();
        when(lecturersRepository.findByUserAccount(mockUserAccount)).thenReturn(mockLecture);

        // Act
        Lectures resultLecture = lecturesService.getByUserAccount(mockUserAccount);

        // Assert
        assertThat(resultLecture).isSameAs(mockLecture);
    }
}
