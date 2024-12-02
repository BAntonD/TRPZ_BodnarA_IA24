package snippents.command;

import snippents.strategy.SnippetService;

public class DeleteSnippetCommand implements Command {
    private SnippetService service;
    private String trigger;
    private Object result;

    public DeleteSnippetCommand(SnippetService service, String trigger) {
        this.service = service;
        this.trigger = trigger;
    }

    @Override
    public void execute() {
        // Викликається метод сервісу для обробки видалення сніпета
        result = service.processCommand(trigger, "delete", null);
    }

    @Override
    public Object getResult() {
        return result; // Повертає результат (ID сніпета або "NaN")
    }
}
