package snippents.strategy;

import repository.SnippetRepository;

public interface SnippetStrategy {
    Object execute(String trigger, String content, SnippetRepository repository);
}



