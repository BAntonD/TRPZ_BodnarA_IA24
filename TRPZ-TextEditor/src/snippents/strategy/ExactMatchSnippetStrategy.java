package snippents.strategy;

import models.Snippet;
import repository.SnippetRepository;

public class ExactMatchSnippetStrategy implements SnippetStrategy {
    @Override
    public Object execute(String trigger, String content, SnippetRepository repository) {
        Snippet snippet = repository.findByTrigger(trigger);
        return snippet.getContent();
    }
}


