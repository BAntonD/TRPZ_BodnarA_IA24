package snippents.strategy;

import models.Snippet;
import repository.SnippetRepository;

public class DeleteSnippetStrategy implements SnippetStrategy {
    @Override
    public Object execute(String trigger, String content, SnippetRepository repository) {
        Snippet snippet = repository.findByTrigger(trigger);
        repository.deleteByTrigger(trigger);
        return snippet.getId(); // Повертає ID видаленого сніпета
    }
}


