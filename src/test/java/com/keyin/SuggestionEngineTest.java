package com.keyin;

import static org.junit.jupiter.api.Assertions.*;
import com.keyin.SuggestionEngine;
import com.keyin.SuggestionsDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;


class testSuggestionEngineTest {

    @Test
    void testloadDictionaryData() {
        SuggestionEngine suggestionEngine = new SuggestionEngine();
        try {
            suggestionEngine.loadDictionaryData(Paths.get("src/main/resources/words.txt"));
            Map<String, Integer> wordSuggestionDB = suggestionEngine.getWordSuggestionDB();
            assertFalse(wordSuggestionDB.isEmpty());
        } catch (Exception e) {
            fail("Exception thrown while loading dictionary data: " + e.getMessage()); //test 4 maybe
        }
    }

    @Test
    public void testgenerateSuggestions() throws Exception {
        SuggestionEngine suggestionEngine = new SuggestionEngine();
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));

        String correctSpelling = "sleep";
        String suggestionsForCorrectSpelling = suggestionEngine.generateSuggestions(correctSpelling);
        assertEquals("", suggestionsForCorrectSpelling, "Suggestions should be empty for a correctly spelled word");

        String misspelledWord = "sleel";
        String suggestionsForMisspelledWord = suggestionEngine.generateSuggestions(misspelledWord);
        Assertions.assertTrue(suggestionsForMisspelledWord.contains("sleep"), "Suggestions should contain 'sleep'");

    }

    @Test
    void testsetWordSuggestionDB() {
        SuggestionEngine suggestionEngine = new SuggestionEngine();
        try {
            suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        } catch (Exception e) {
            fail("Exception thrown while loading dictionary data: " + e.getMessage());
        }

        SuggestionsDatabase customDatabase = new SuggestionsDatabase();
        customDatabase.addWord("example", 10);

        suggestionEngine.setWordSuggestionDB(customDatabase);

        assertEquals(customDatabase.getWordMap(), suggestionEngine.getWordSuggestionDB());
    }

}
