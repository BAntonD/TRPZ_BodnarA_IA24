package snippents.command;

import snippents.strategy.SnippetService;

public class CreateSnippetCommand implements Command {
    private SnippetService service;
    private String trigger;
    private String content;
    private Object result;

    public CreateSnippetCommand(SnippetService service, String trigger, String content) {
        this.service = service;
        this.trigger = trigger;
        this.content = content;
    }

    @Override
    public void execute() {
        // Викликається метод сервісу для обробки створення сніпета
        result = service.processCommand(trigger, "create", content);
    }

    @Override
    public Object getResult() {
        return result; // Повертає результат (trigger або повідомлення про помилку)
    }
}

