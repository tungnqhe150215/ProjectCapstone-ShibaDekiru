package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Hiragana;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.HiraganaRepository;
import com.sep490.g49.shibadekiru.service.IHiraganaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HiraganaServiceImpl implements IHiraganaService {

    HiraganaRepository hiraganaRepository;
    @Override
    public List<Hiragana> getAllHiragana() {
        return hiraganaRepository.findAll();
    }

    @Override
    public Hiragana getHiraganaById(long hiraganaId) {
        Hiragana hiragana = hiraganaRepository.findById(hiraganaId).orElse(null);

        if (hiragana == null) {
            throw new ResourceNotFoundException("Hiragana not found with id: " + hiraganaId);
        }
        return hiragana;
    }

    @Override
    public Hiragana createHiragana(Hiragana hiragana) {

        String hiraganaCharacter = hiragana.getHiraganaCharacter();
        String romanji= hiragana.getRomanji();

        Hiragana hiragana1 = new Hiragana();
        hiragana1.setHiraganaCharacter(hiraganaCharacter);
        hiragana1.setRomanji(romanji);

        return hiraganaRepository.save(hiragana1);
    }

    @Override
    public Hiragana updateHiragana(Long hiraganaId, Hiragana hiraganaUpdate) {

        Optional<Hiragana> hiraganaOptional = hiraganaRepository.findById(hiraganaId);

        if(hiraganaOptional.isPresent()) {
            Hiragana hiragana = hiraganaOptional.get();

            hiragana.setHiraganaCharacter(hiraganaUpdate.getHiraganaCharacter());
            hiragana.setRomanji(hiraganaUpdate.getRomanji());

            return hiraganaRepository.save(hiragana);
        } else {
            throw new ResourceNotFoundException("Hiragana not found");
        }
    }

    @Override
    public void deleteHiragana(Long hiraganaId) {
        Hiragana hiragana = hiraganaRepository.findById(hiraganaId).orElseThrow(() -> new ResourceNotFoundException("Kanji not exist with id:" + hiraganaId));
        hiraganaRepository.delete(hiragana);
    }
}
