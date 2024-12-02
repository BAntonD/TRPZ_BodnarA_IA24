package snippents.strategy;

import models.Snippet;
import repository.SnippetRepository;

public class CreateSnippetStrategy implements SnippetStrategy {
    @Override
    public Object execute(String trigger, String content, SnippetRepository repository) {
        repository.addSnippet(new Snippet(trigger, content)); // Додаємо новий сніпет з переданим контентом
        return trigger; // Повертаємо тригер нового сніпета
    }
}



