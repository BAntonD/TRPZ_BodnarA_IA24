package snippents;

import models.Snippet;
import repository.SnippetRepository;

import java.util.List;
import java.util.Optional;

public class SnippetService implements SnippetProcessor {
    private SnippetRepository repository;

    public SnippetService(SnippetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object processSnippet(String trigger) {
        // Спроба знайти точний збіг
        Optional<Snippet> exactMatch = repository.findByTrigger(trigger);
        if (exactMatch.isPresent()) {
            return exactMatch.get().getContent(); // Повертаємо текст сніпета
        }

        // Пошук схожих тригерів
        List<Snippet> similarTriggers = repository.findSimilarTriggers(trigger);
        if (!similarTriggers.isEmpty()) {
            return similarTriggers.stream()
                    .map(Snippet::getTrigger)
                    .toList(); // Повертаємо список тригерів
        }

        // Якщо нічого не знайдено
        return null;
    }
}

