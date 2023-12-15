import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassStudent;
import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.ClassStudentServiceImpl;
import com.sep490.g49.shibadekiru.repository.ClassStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClassStudentServiceImplTest {

    @Mock
    private ClassStudentRepository classStudentRepository;

    @InjectMocks
    private ClassStudentServiceImpl classStudentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getClassStudentByClass() {
        // Arrange
        Class aClass = new Class();
        when(classStudentRepository.findClassStudentsByBelongClass(aClass)).thenReturn(Arrays.asList(new ClassStudent(), new ClassStudent()));

        // Act
        List<ClassStudent> result = classStudentService.getClassStudentByClass(aClass);

        // Assert
        assertEquals(2, result.size());
        verify(classStudentRepository, times(1)).findClassStudentsByBelongClass(aClass);
    }

    @Test
    void getClassStudentByStudent() {
        // Arrange
        Student student = new Student();
        when(classStudentRepository.findByStudent(student)).thenReturn(Arrays.asList(new ClassStudent(), new ClassStudent()));

        // Act
        List<ClassStudent> result = classStudentService.getClassStudentByStudent(student);

        // Assert
        assertEquals(2, result.size());
        verify(classStudentRepository, times(1)).findByStudent(student);
    }

    @Test
    void getClassStudentById() {
        // Arrange
        Long id = 1L;
        ClassStudent classStudent = new ClassStudent();
        when(classStudentRepository.findById(id)).thenReturn(Optional.of(classStudent));

        // Act
        ClassStudent result = classStudentService.getClassStudentById(id);

        // Assert
        assertEquals(classStudent, result);
        verify(classStudentRepository, times(1)).findById(id);
    }

//    @Test
//    void createClassStudent() {
//        // Arrange
//        ClassStudent classStudentRequest = new ClassStudent();
//
//        // Mock the save method to return the same object passed to it
//        when(classStudentRepository.save(any(ClassStudent.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        ClassStudent result = classStudentService.createClassStudent(classStudentRequest);
//
//        // Assert
//        assertNotNull(result.getJoinedAt());
//        verify(classStudentRepository, times(1)).save(any(ClassStudent.class));
//    }

    @Test
    void deleteClassStudent() {
        // Arrange
        Long id = 1L;
        ClassStudent classStudent = new ClassStudent();
        when(classStudentRepository.findById(id)).thenReturn(Optional.of(classStudent));

        // Act
        assertDoesNotThrow(() -> classStudentService.deleteClassStudent(id));

        // Assert
        verify(classStudentRepository, times(1)).findById(id);
        verify(classStudentRepository, times(1)).delete(classStudent);
    }
}
