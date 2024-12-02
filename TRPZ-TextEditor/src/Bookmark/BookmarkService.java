package Bookmark;

import models.Settings;
import repository.BookmarkRepository;

public class BookmarkService {
    private BookmarkRepository repository;

    public BookmarkService(BookmarkRepository repository) {
        this.repository = repository;
    }

    public Object processBookmark(String action, int bookmarkId, int pageNumber, int lineNumber, Settings settings) {
        BookmarkStrategy strategy;

        switch (action.toLowerCase()) {
            case "add":
                strategy = new AddBookmarkStrategy(pageNumber, lineNumber, settings);
                return strategy.execute(bookmarkId, repository); // Повертає ID нової закладки
            case "remove":
                strategy = new RemoveBookmarkStrategy();
                return strategy.execute(bookmarkId, repository); // Повертає ID видаленої закладки
            case "navigate":
                strategy = new NavigateToBookmarkStrategy();
                return strategy.execute(bookmarkId, repository); // Повертає об'єкт Page
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
}

