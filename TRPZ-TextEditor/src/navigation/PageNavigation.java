package navigation;

import models.Page;
import repository.PageRepository;

public class PageNavigation implements NavigationProcessor {
    private PageRepository repository;

    public PageNavigation(PageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object navigate(int pageNumber, Integer lineNumber) {
        Page page = repository.findByPageNumber(pageNumber);
        if (page != null) {
            return page; // Повертає сторінку, якщо знайдено
        }
        return null; // Якщо сторінку не знайдено
    }
}

