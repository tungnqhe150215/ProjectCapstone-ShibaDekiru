package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Katakana;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.KatakanaRepository;
import com.sep490.g49.shibadekiru.service.IKatakanaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KatakanaServiceImpl implements IKatakanaService {

    KatakanaRepository katakanaRepository;
    @Override
    public List<Katakana> getAllKatakana() {
        return katakanaRepository.findAll();
    }

    @Override
    public Katakana getKatakanaById(long katakanaId) {
        Katakana katakana = katakanaRepository.findById(katakanaId).orElse(null);

        if(katakana == null) {
            throw new ResourceNotFoundException("Hiragana not found with id: " + katakanaId);
        }
        return katakana;
    }

    @Override
    public Katakana createKatakana(Katakana katakana) {

        String katakanaCharacter = katakana.getKatakanaCharacter();
        String romanji = katakana.getRomanji();

        Katakana katakana1 = new Katakana();
        katakana1.setKatakanaCharacter(katakanaCharacter);
        katakana1.setRomanji(romanji);

        return katakanaRepository.save(katakana1);
    }

    @Override
    public Katakana updateKatakana(Long katakanaId, Katakana katakanaUpdate) {
        Optional<Katakana> katakanaOptional = katakanaRepository.findById(katakanaId);

        if(katakanaOptional.isPresent()) {
            Katakana katakana = katakanaOptional.get();

            katakana.setKatakanaCharacter(katakanaUpdate.getKatakanaCharacter());
            katakana.setRomanji(katakanaUpdate.getRomanji());

            return katakanaRepository.save(katakana);
        } else {
            throw new ResourceNotFoundException("Katakana not found");
        }
    }

    @Override
    public void deleteKatakana(Long katakanaId) {
        Katakana katakana = katakanaRepository.findById(katakanaId).orElseThrow(() -> new ResourceNotFoundException("Kanji not exist with id:" + katakanaId));
        katakanaRepository.delete(katakana);
    }
}
