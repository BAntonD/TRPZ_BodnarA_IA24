package repository;

import models.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRepository {
    private List<Page> pages = new ArrayList<>();

    public void save(Page page) {
        pages.add(page);
    }

    public Page findByPageNumber(int pageNumber) {
        return pages.stream()
                .filter(page -> page.getPageNumber() == pageNumber)
                .findFirst()
                .orElse(null);
    }
}
