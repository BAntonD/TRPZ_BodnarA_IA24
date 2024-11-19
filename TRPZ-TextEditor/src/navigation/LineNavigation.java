package navigation;

import models.Page;
import repository.PageRepository;

public class LineNavigation implements NavigationProcessor {
    private PageRepository repository;

    public LineNavigation(PageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object navigate(int pageNumber, Integer lineNumber) {
        Page page = repository.findByPageNumber(pageNumber);
        if (page != null) {
            String line = page.getLine(lineNumber);
            if (line != null) {
                return line; // Повертає текст рядка
            }
        }
        return null; // Якщо сторінку або рядок не знайдено
    }
}


