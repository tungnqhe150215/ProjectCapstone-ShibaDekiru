import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.impl.ExerciseServiceImpl;
import com.sep490.g49.shibadekiru.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExerciseServiceImplTest {

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExercisePartByClasswork() {
        // Mock data
        ClassWork classWork = new ClassWork();
        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();
        List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);

        // Mock behavior
        when(exerciseRepository.findExercisesByClassWork(classWork)).thenReturn(exerciseList);

        // Call the method
        List<Exercise> result = exerciseService.getExercisePartByClasswork(classWork);

        // Assertions
        assertEquals(exerciseList.size(), result.size());

        // Verify interactions
        verify(exerciseRepository, times(1)).findExercisesByClassWork(classWork);
    }

    @Test
    void getExerciseById() {
        // Mock data
        Long exerciseId = 1L;
        Exercise exercise = new Exercise();

        // Mock behavior
        when(exerciseRepository.findByExerciseId(exerciseId)).thenReturn(exercise);

        // Call the method
        Exercise result = exerciseService.getExerciseById(exerciseId);

        // Assertions
        assertEquals(exercise, result);

        // Verify interactions
        verify(exerciseRepository, times(1)).findByExerciseId(exerciseId);
    }

    @Test
    void createExercise() {
        // Mock data
        Exercise exerciseRequest = new Exercise();
        Exercise exercise = new Exercise();

        // Mock behavior
        when(exerciseRepository.save(exerciseRequest)).thenReturn(exercise);

        // Call the method
        Exercise result = exerciseService.createExercise(exerciseRequest);

        // Assertions
        assertEquals(exercise, result);

        // Verify interactions
        verify(exerciseRepository, times(1)).save(exerciseRequest);
    }

    @Test
    void updateExercise() {
        // Mock data
        Long exerciseId = 1L;
        Exercise exerciseRequest = new Exercise();
        Exercise existingExercise = new Exercise();

        // Mock behavior
        when(exerciseRepository.findByExerciseId(exerciseId)).thenReturn(existingExercise);
        when(exerciseRepository.save(existingExercise)).thenReturn(existingExercise);

        // Call the method
        Exercise result = exerciseService.updateExercise(exerciseId, exerciseRequest);

        // Assertions
        assertEquals(existingExercise, result);

        // Verify interactions
        verify(exerciseRepository, times(1)).findByExerciseId(exerciseId);
        verify(exerciseRepository, times(1)).save(existingExercise);
    }

    @Test
    void deleteExercise() {
        // Mock data
        Long exerciseId = 1L;
        Exercise exercise = new Exercise();

        // Mock behavior
        when(exerciseRepository.findByExerciseId(exerciseId)).thenReturn(exercise);

        // Call the method
        exerciseService.deleteExercise(exerciseId);

        // Verify interactions
        verify(exerciseRepository, times(1)).findByExerciseId(exerciseId);
        verify(exerciseRepository, times(1)).delete(exercise);
    }
}
