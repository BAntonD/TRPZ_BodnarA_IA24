package Snippet;

import repository.CurrentSettingRepository;
import repository.SettingRepository;
import repository.SnippetRepository;
import models.Snippet;

import java.util.List;

// Приклад класу SnippetService
public class SnippetService {
    private final SnippetRepository snippetRepository = new SnippetRepository();
    private final CurrentSettingRepository currentSettingRepository = new CurrentSettingRepository();
    private final SettingRepository settingRepository = new SettingRepository();

    public void saveSnippet(String trigger, String content) {
        int currentSettingId = currentSettingRepository.getCurrentSettingId();
        int snippetListId = settingRepository.getSnippetListId(currentSettingId);

        if (snippetRepository.isTriggerExists(trigger, snippetListId)) {
            throw new IllegalArgumentException("Trigger already exists.");
        }

        snippetRepository.saveSnippet(trigger, content, snippetListId);
    }

    public List<Snippet> getAllSnippets() {
        int currentSettingId = currentSettingRepository.getCurrentSettingId();
        int snippetListId = settingRepository.getSnippetListId(currentSettingId);
        return snippetRepository.getAllSnippets(snippetListId);
    }

    public void deleteSnippet(Snippet snippet) {
        int currentSettingId = currentSettingRepository.getCurrentSettingId();
        int snippetListId = settingRepository.getSnippetListId(currentSettingId);
        snippetRepository.deleteSnippet(snippet, snippetListId);
    }

    public boolean isTriggerExists(String trigger) {
        int idSetting = CurrentSettingRepository.getCurrentSettingId();
        int idSnippetList = SettingRepository.getSnippetListId(idSetting);
        return snippetRepository.isTriggerExists(trigger, idSnippetList);
    }

}
