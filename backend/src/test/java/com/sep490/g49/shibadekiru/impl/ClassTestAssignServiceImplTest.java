import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassTestAssign;
import com.sep490.g49.shibadekiru.impl.ClassTestAssignServiceImpl;
import com.sep490.g49.shibadekiru.repository.ClassTestAssignRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassTestAssignServiceImplTest {

    @InjectMocks
    private ClassTestAssignServiceImpl classTestAssignService;

    @Mock
    private ClassTestAssignRepository classTestAssignRepository;

    @Test
    void getAllClassTestByClass() {
        // Mock data
        Class assignedClass = new Class();
        ClassTestAssign classTestAssign1 = new ClassTestAssign();
        ClassTestAssign classTestAssign2 = new ClassTestAssign();
        List<ClassTestAssign> classTestAssignList = Arrays.asList(classTestAssign1, classTestAssign2);

        // Mock behavior
        when(classTestAssignRepository.findAllByAssignedClass(assignedClass)).thenReturn(classTestAssignList);

        // Call the method
        List<ClassTestAssign> result = classTestAssignService.getAllClassTestByClass(assignedClass);

        // Assertions
        assertEquals(classTestAssignList.size(), result.size());

        // Verify interactions
        verify(classTestAssignRepository, times(1)).findAllByAssignedClass(assignedClass);
    }

    @Test
    void getAllClassTestByTest() {
        // Mock data
        com.sep490.g49.shibadekiru.entity.Test test = new com.sep490.g49.shibadekiru.entity.Test();
        ClassTestAssign classTestAssign1 = new ClassTestAssign();
        ClassTestAssign classTestAssign2 = new ClassTestAssign();
        List<ClassTestAssign> classTestAssignList = Arrays.asList(classTestAssign1, classTestAssign2);

        // Mock behavior
        when(classTestAssignRepository.findAllByTest(test)).thenReturn(classTestAssignList);

        // Call the method
        List<ClassTestAssign> result = classTestAssignService.getAllClassTestByTest(test);

        // Assertions
        assertEquals(classTestAssignList.size(), result.size());

        // Verify interactions
        verify(classTestAssignRepository, times(1)).findAllByTest(test);
    }

    @Test
    void updateExpireDate() {
        // Mock data
        Long id = 1L;
        int expirationMinutes = 30;
        ClassTestAssign classTestAssign = new ClassTestAssign();
        classTestAssign.setId(id);

        // Mock behavior
        when(classTestAssignRepository.findById(id)).thenReturn(Optional.of(classTestAssign));
        when(classTestAssignRepository.save(classTestAssign)).thenReturn(classTestAssign);

        // Call the method
        ClassTestAssign updatedClassTestAssign = classTestAssignService.updateExpireDate(id, expirationMinutes);

        // Assertions
        assertNotNull(updatedClassTestAssign);
        assertEquals(LocalDateTime.now().plusMinutes(expirationMinutes).getMinute(), updatedClassTestAssign.getAccessExpirationDate().getMinute());

        // Verify interactions
        verify(classTestAssignRepository, times(1)).findById(id);
        verify(classTestAssignRepository, times(1)).save(classTestAssign);
    }

    @Test
    void deleteClassTest() {
        // Mock data
        Long id = 1L;
        ClassTestAssign classTestAssign = new ClassTestAssign();
        classTestAssign.setId(id);

        // Mock behavior
        when(classTestAssignRepository.findById(id)).thenReturn(Optional.of(classTestAssign));

        // Call the method
        classTestAssignService.deleteClassTest(id);

        // Verify interactions
        verify(classTestAssignRepository, times(1)).findById(id);
        verify(classTestAssignRepository, times(1)).delete(classTestAssign);
    }
}
