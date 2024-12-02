package Bookmark;

import models.Page;
import repository.BookmarkRepository;
import models.Bookmark;

public class NavigateToBookmarkStrategy implements BookmarkStrategy {
    @Override
    public Page execute(int bookmarkId, BookmarkRepository repository) {
        Bookmark bookmark = repository.findBookmark(bookmarkId);
        if (bookmark != null) {
            return new Page(bookmark.getPageNumber()); // Повертаємо сторінку, до якої здійснюється перехід
        }
        return null; // Закладка не знайдена
    }
}



