package snippents.strategy;

import repository.SnippetRepository;

public class NotFoundSnippetStrategy implements SnippetStrategy {
    @Override
    public Object execute(String trigger, String content, SnippetRepository repository) {
        return new  String[]{};// Повертаємо результат, що вказує на помилку
    }
}

