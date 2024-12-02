package repository;

import models.Bookmark;

import java.util.*;

import java.util.HashMap;
import java.util.Map;

public class BookmarkRepository {
    private final Map<Integer, Bookmark> bookmarks = new HashMap<>();

    // Додає нову закладку
    public void addBookmark(Bookmark bookmark) {
        bookmarks.put(bookmark.getId(), bookmark);
    }

    // Видаляє закладку за ID
    public boolean removeBookmark(int bookmarkId) {
        return bookmarks.remove(bookmarkId) != null;
    }

    // Знаходить закладку за ID
    public Bookmark findBookmark(int bookmarkId) {
        return bookmarks.get(bookmarkId);
    }
}


