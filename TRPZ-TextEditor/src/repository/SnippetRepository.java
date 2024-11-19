package repository;

import models.Snippet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SnippetRepository {
    private List<Snippet> snippets = new ArrayList<>();

    // CRUD-операції
    public void save(Snippet snippet) {
        snippets.add(snippet);
    }

    public Optional<Snippet> findByTrigger(String trigger) {
        return snippets.stream()
                .filter(snippet -> snippet.getTrigger().equalsIgnoreCase(trigger))
                .findFirst();
    }

    public List<Snippet> findAll() {
        return new ArrayList<>(snippets);
    }

    public List<Snippet> findSimilarTriggers(String prefix) {
        return snippets.stream()
                .filter(snippet -> snippet.getTrigger().startsWith(prefix))
                .toList();
    }

    public void deleteById(int id) {
        snippets.removeIf(snippet -> snippet.getId() == id);
    }
}


