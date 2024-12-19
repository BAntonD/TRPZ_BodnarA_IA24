package TemplateMethod;

import models.Snippet;
import repository.CurrentSettingRepository;
import repository.SettingRepository;
import repository.SnippetRepository;

import java.util.List;

public class SnippetService {
    // Ініціалізація репозиторіїв без необхідності передавати їх через конструктор
    private final CurrentSettingRepository currentSettingRepository = new CurrentSettingRepository();
    private final SettingRepository settingRepository = new SettingRepository();
    private final SnippetRepository snippetRepository = new SnippetRepository();

    public String getExactSnippet(String trigger) {
        int currentSettingId = currentSettingRepository.getCurrentSettingId();
        int snippetListId = settingRepository.getSnippetListId(currentSettingId);
        return snippetRepository.getSnippetByTrigger(trigger, snippetListId);
    }

    public List<Snippet> getSimilarSnippets(String trigger) {
        int currentSettingId = currentSettingRepository.getCurrentSettingId();
        return snippetRepository.getSimilarSnippets(trigger, currentSettingId);
    }
}

