package snippents.strategy;

import models.Snippet;
import repository.SnippetRepository;

public class SnippetService {
    private SnippetRepository repository;

    public SnippetService(SnippetRepository repository) {
        this.repository = repository;
    }

    // Основний метод, що обробляє всі команди
    public Object processCommand(String trigger, String command, String content) {
        switch (command) {
            case "create":
                return processCreateSnippet(trigger, content);  // Створення сніпета
            case "delete":
                return processDeleteSnippet(trigger);  // Видалення сніпета
            case "use":
                return processUseSnippet(trigger);  // Використання сніпета
            default:
                throw new IllegalArgumentException("Unknown command: " + command);  // Якщо команда невідома
        }
    }


// Логіка вибору стратегії для створення сніпета
private Object processCreateSnippet(String trigger, String content) {
    SnippetStrategy strategy;

    // Вибір стратегії
    if (repository.findByTrigger(trigger) == null) {
        strategy = new CreateSnippetStrategy(); // Якщо сніпет не існує
    } else {
        strategy = new SnippetAlreadyExistsStrategy(); // Якщо сніпет вже існує
    }

    // Виконання обраної стратегії
    return strategy.execute(trigger, content, repository);
}



    // Логіка видалення сніпета
    private Object processDeleteSnippet(String trigger) {
        SnippetStrategy strategy;

        // Вибір стратегії
        if (repository.findByTrigger(trigger) == null) {
            strategy = new NotFoundSnippetStrategy(); // Сніпет не знайдено
        } else {
            strategy = new DeleteSnippetStrategy(); // Сніпет знайдено
        }

        // Виконання обраної стратегії
        return strategy.execute(trigger, null, repository);
    }


    // Логіка використання сніпета
    private Object processUseSnippet(String trigger) {
        SnippetStrategy strategy;

        // Вибір стратегії
        if (repository.findByTrigger(trigger) != null) {
            strategy = new ExactMatchSnippetStrategy(); // Точне співпадіння
        } else if (!repository.findSuggestions(trigger).isEmpty()) {
            strategy = new SuggestSnippetStrategy(); // Схожі варіанти
        } else {
            strategy = new NotFoundSnippetStrategy(); // Нічого не знайдено
        }

        // Виконання обраної стратегії
        return strategy.execute(trigger, null, repository);
    }

}





