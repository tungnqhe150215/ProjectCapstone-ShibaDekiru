package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Grammar;

import java.util.Collection;
import java.util.List;

public interface IGrammarService {
    List<Grammar> getAllGrammar();

    Grammar getGrammarById(long grammarId);

    Grammar createGrammar(Grammar grammar);

    Grammar updateGrammar(Long grammarId, Grammar grammarUpdate);

    void deleteGrammar(Long grammarId);
}
