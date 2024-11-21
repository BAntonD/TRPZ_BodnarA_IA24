package snippents.command;


import snippents.strategy.SnippetService;

public class UseSnippetCommand implements Command {
    private SnippetService service;
    private String trigger;
    private Object result;

    public UseSnippetCommand(SnippetService service, String trigger) {
        this.service = service;
        this.trigger = trigger;
    }

    @Override
    public void execute() {
        // Викликається метод сервісу для обробки використання сніпета
        result = service.processCommand(trigger, "use", null);
    }

    @Override
    public Object getResult() {
        return result; // Повертає результат (вміст сніпета або пропозиції)
    }
}


