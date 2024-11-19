package navigation;

import repository.PageRepository;

public class NavigationService {
    private PageRepository repository;

    public NavigationService(PageRepository repository) {
        this.repository = repository;
    }

    public Object navigate(int pageNumber, Integer lineNumber) {
        NavigationProcessor processor;

        if (lineNumber == null) {
            processor = new PageNavigation(repository);
        } else {
            processor = new LineNavigation(repository);
        }

        return processor.navigate(pageNumber, lineNumber);
    }
}

