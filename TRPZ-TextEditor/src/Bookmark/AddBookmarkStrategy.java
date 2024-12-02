package Bookmark;

import models.Settings;
import models.Bookmark;
import repository.BookmarkRepository;

public class AddBookmarkStrategy implements BookmarkStrategy {
    private int pageNumber;
    private int lineNumber;
    private Settings settings;

    public AddBookmarkStrategy(int pageNumber, int lineNumber, Settings settings) {
        this.pageNumber = pageNumber;
        this.lineNumber = lineNumber;
        this.settings = settings;
    }

    @Override
    public Integer execute(int bookmarkId, BookmarkRepository repository) {
        Bookmark bookmark = new Bookmark(bookmarkId, settings, pageNumber, lineNumber);
        repository.addBookmark(bookmark);
        return bookmark.getId(); // Повертаємо ID закладки
    }
}



