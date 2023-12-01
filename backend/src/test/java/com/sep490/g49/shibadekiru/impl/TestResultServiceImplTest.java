import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.TestResultServiceImpl;
import com.sep490.g49.shibadekiru.repository.StudentRepository;
import com.sep490.g49.shibadekiru.repository.TestRepository;
import com.sep490.g49.shibadekiru.repository.TestResultRepository;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestResultServiceImplTest {

    @Mock
    private TestResultRepository testResultRepository;

    @Mock
    private TestSectionRepository testSectionRepository;

    @Mock
    private TestRepository testRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private TestResultServiceImpl testResultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTestResultByTest() {
        // Mock data
        com.sep490.g49.shibadekiru.entity.Test test = new com.sep490.g49.shibadekiru.entity.Test();
        List<TestSection> testSections = new ArrayList<>();
        when(testSectionRepository.findTestSectionsByTest(test)).thenReturn(testSections);
        when(testResultRepository.findTestResultsByTestSection(any(TestSection.class))).thenReturn(new ArrayList<>());

        // Test
        List<TestResult> result = testResultService.getTestResultByTest(test);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getTestResultById() {
        // Mock data
        Long testResultId = 1L;
        TestResult testResult = new TestResult();
        when(testResultRepository.findById(testResultId)).thenReturn(Optional.of(testResult));

        // Test
        TestResult result = testResultService.getTestResultById(testResultId);

        // Assertions
        assertNotNull(result);
        assertEquals(testResult, result);
    }

    @Test
    void createTestResult() {
        // Mock data
        TestResult testResultRequest = new TestResult();
        when(testResultRepository.save(any(TestResult.class))).thenReturn(testResultRequest);

        // Test
        TestResult createdTestResult = testResultService.createTestResult(testResultRequest);

        // Assertions
        assertNotNull(createdTestResult);
        assertEquals(testResultRequest, createdTestResult);
        assertNotNull(createdTestResult.getDoneTime());
    }

//    @Test
//    void updateTestResult() {
//        // Mock data
//        TestResult testResultRequest = new TestResult();
//        TestResult existingTestResult = new TestResult();
//        when(testResultRepository.findTestResultsByStudentAndTestSection(any(Student.class), any(TestSection.class)))
//                .thenReturn(existingTestResult);
//        when(testResultRepository.save(any(TestResult.class))).thenReturn(testResultRequest);
//
//        // Test
//        TestResult updatedTestResult = testResultService.updateTestResult(testResultRequest);
//
//        // Assertions
//        assertNotNull(updatedTestResult);
//        assertEquals(testResultRequest, updatedTestResult);
//        assertNotNull(updatedTestResult.getDoneTime());
//    }

    @Test
    void deleteTestResult() {
        // Mock data
        Long testResultId = 1L;
        TestResult testResult = new TestResult();
        when(testResultRepository.findById(testResultId)).thenReturn(Optional.of(testResult));

        // Test
        assertDoesNotThrow(() -> testResultService.deleteTestResult(testResultId));
    }

//    @Test
//    void getTestResultByTestAndStudent() {
//        // Mock data
//        Long testId = 1L;
//        Long studentId = 2L;
//        Student student = new Student();
//        com.sep490.g49.shibadekiru.entity.Test test = new com.sep490.g49.shibadekiru.entity.Test();
//        List<TestSection> testSections = new ArrayList<>();
//        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
//        when(testRepository.findTestByTestId(testId)).thenReturn(test);
//        when(testSectionRepository.findTestSectionsByTest(test)).thenReturn(testSections);
//        when(testResultRepository.findTestResultsByStudentAndTestSection(any(Student.class), any(TestSection.class)))
//                .thenReturn(new TestResult());
//
//        // Test
//        List<TestResult> result = testResultService.getTestResultByTestAndStudent(testId, studentId);
//
//        // Assertions
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//    }

    @Test
    void checkTestResultExist() {
        // Mock data
        Student student = new Student();
        TestSection testSection = new TestSection();
        when(testResultRepository.findTestResultsByStudentAndTestSection(student, testSection)).thenReturn(new TestResult());

        // Test
        boolean result = testResultService.checkTestResultExist(student, testSection);

        // Assertions
        assertTrue(result);
    }
}
