package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.kanjiDto.KanjiDTOResponse;
import com.sep490.g49.shibadekiru.entity.Kanji;
import com.sep490.g49.shibadekiru.repository.KanjiRepository;
import com.sep490.g49.shibadekiru.service.IKanjiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KanjiServiceImpl implements IKanjiService {

    KanjiRepository kanjiRepository;
    @Override
    public List<KanjiDTOResponse> getAllKanji() {
        //Lấy tất cả Kanji từ database ở dạng Entity
        List<Kanji> kanjiList = kanjiRepository.findAll();

        //Vì hiển thị cho use xem ở dạng DTO nên phải map ngược lại từ Entity => DTO
        List<KanjiDTOResponse> kanjiDTOResponseList = new ArrayList<>();
        for (Kanji kanji : kanjiList) {
            KanjiDTOResponse kanjiDTOResponse = KanjiDTOResponse.builder()
                    .kanjiId(kanji.getKanjiId())
                    .characterKanji(kanji.getCharacterKanji())
                    .onReading(kanji.getOnReading())
                    .kunReading(kanji.getKunReading())
                    .chineseMean(kanji.getChineseMean())
                    .example(kanji.getExample())
                    .lesson(kanji.getLesson())
                    .build();
            //chuyển từ Entity => DTO
            kanjiDTOResponseList.add(kanjiDTOResponse);
        }
        return kanjiDTOResponseList;
    }
}
