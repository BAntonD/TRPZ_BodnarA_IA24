package Bookmark;

import repository.BookmarkRepository;

public interface BookmarkStrategy {
    Object execute(int bookmarkId, BookmarkRepository repository);
}


