package Bookmark;

import repository.BookmarkRepository;

public class RemoveBookmarkStrategy implements BookmarkStrategy {
    @Override
    public Integer execute(int bookmarkId, BookmarkRepository repository) {
        if (repository.removeBookmark(bookmarkId)) {
            return bookmarkId; // Повертаємо ID видаленої закладки
        }
        return null; // Закладка не знайдена
    }
}



