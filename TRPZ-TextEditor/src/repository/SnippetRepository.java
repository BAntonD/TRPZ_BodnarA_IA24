package repository;

import models.Snippet;

import java.util.HashMap;
import java.util.Map;

public class SnippetRepository {
    private Map<String, Snippet> snippets = new HashMap<>();

    public Snippet findByTrigger(String trigger) {
        return snippets.get(trigger); // Пошук сніпету за тригером
    }

    public void addSnippet(Snippet snippet) {
        snippets.put(snippet.getTrigger(), snippet); // Додавання сніпету
    }

    public Snippet deleteByTrigger(String trigger) {
        return snippets.remove(trigger); // Видалення сніпету за тригером
    }

    public Map<String, String> findSuggestions(String trigger) {
        Map<String, String> suggestions = new HashMap<>();
        for (String key : snippets.keySet()) {
            if (key.startsWith(trigger)) {
                suggestions.put(key, snippets.get(key).getContent());
            }
        }
        return suggestions;
    }
}



