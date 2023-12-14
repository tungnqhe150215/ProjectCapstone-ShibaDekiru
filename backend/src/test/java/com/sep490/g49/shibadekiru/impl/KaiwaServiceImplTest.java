import com.sep490.g49.shibadekiru.entity.Kaiwa;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.impl.KaiwaServiceImpl;
import com.sep490.g49.shibadekiru.repository.KaiwaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KaiwaServiceImplTest {

    @Mock
    private KaiwaRepository kaiwaRepository;

    @InjectMocks
    private KaiwaServiceImpl kaiwaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getKaiwaPartByLesson() {
        Lesson lesson = new Lesson();
        Kaiwa kaiwa1 = new Kaiwa();
        Kaiwa kaiwa2 = new Kaiwa();
        List<Kaiwa> kaiwaList = new ArrayList<>();
        kaiwaList.add(kaiwa1);
        kaiwaList.add(kaiwa2);

        when(kaiwaRepository.findKaiwasByLesson(lesson)).thenReturn(kaiwaList);

        List<Kaiwa> result = kaiwaService.getKaiwaPartByLesson(lesson);

        assertEquals(2, result.size());
        verify(kaiwaRepository, times(1)).findKaiwasByLesson(lesson);
    }

    @Test
    void getKaiwaById() {
        Long kaiwaId = 1L;
        Kaiwa kaiwa = new Kaiwa();
        kaiwa.setKaiwaId(kaiwaId);

        when(kaiwaRepository.findKaiwaByKaiwaId(kaiwaId)).thenReturn(kaiwa);

        Kaiwa result = kaiwaService.getKaiwaById(kaiwaId);

        assertNotNull(result);
        assertEquals(kaiwaId, result.getKaiwaId());
        verify(kaiwaRepository, times(1)).findKaiwaByKaiwaId(kaiwaId);
    }

    @Test
    void createKaiwa() {
        Kaiwa kaiwaToCreate = new Kaiwa();
        kaiwaToCreate.setTitle("Test Kaiwa");

        when(kaiwaRepository.save(kaiwaToCreate)).thenReturn(kaiwaToCreate);

        Kaiwa result = kaiwaService.createKaiwa(kaiwaToCreate);

        assertNotNull(result);
        assertEquals("Test Kaiwa", result.getTitle());
        verify(kaiwaRepository, times(1)).save(kaiwaToCreate);
    }

    @Test
    void updateKaiwa() {
        Long kaiwaId = 1L;
        Kaiwa existingKaiwa = new Kaiwa();
        existingKaiwa.setKaiwaId(kaiwaId);

        Kaiwa updatedKaiwa = new Kaiwa();
        updatedKaiwa.setLink("https://updated-link.com");

        when(kaiwaRepository.findKaiwaByKaiwaId(kaiwaId)).thenReturn(existingKaiwa);
        when(kaiwaRepository.save(existingKaiwa)).thenReturn(existingKaiwa);

        Kaiwa result = kaiwaService.updateKaiwa(kaiwaId, updatedKaiwa);

        assertNotNull(result);
        assertEquals("https://updated-link.com", result.getLink());
        verify(kaiwaRepository, times(1)).findKaiwaByKaiwaId(kaiwaId);
        verify(kaiwaRepository, times(1)).save(existingKaiwa);
    }

    @Test
    void deleteKaiwa() {
        Long kaiwaId = 1L;
        Kaiwa kaiwaToDelete = new Kaiwa();
        kaiwaToDelete.setKaiwaId(kaiwaId);

        when(kaiwaRepository.findKaiwaByKaiwaId(kaiwaId)).thenReturn(kaiwaToDelete);

        kaiwaService.deleteKaiwa(kaiwaId);

        verify(kaiwaRepository, times(1)).findKaiwaByKaiwaId(kaiwaId);
        verify(kaiwaRepository, times(1)).delete(kaiwaToDelete);
    }
}
