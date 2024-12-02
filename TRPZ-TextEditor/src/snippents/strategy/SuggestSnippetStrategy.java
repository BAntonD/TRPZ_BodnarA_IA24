package snippents.strategy;

import repository.SnippetRepository;

import java.util.Map;

public class SuggestSnippetStrategy implements SnippetStrategy {
    @Override
    public Object execute(String trigger, String content, SnippetRepository repository) {
        Map<String, String> suggestions = repository.findSuggestions(trigger);
        return suggestions; // Повертає список пропозицій
    }
}




