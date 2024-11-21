package snippents.strategy;

import repository.SnippetRepository;

public class SnippetAlreadyExistsStrategy implements SnippetStrategy {
    @Override
    public Object execute(String trigger, String content, SnippetRepository repository) {
        return trigger; // Повертає тригер
    }
}


