package TemplateMethod;

import models.Snippet;
import java.util.List;

public interface SimilarMatchesHandler {
    void handleSimilarMatches(List<Snippet> similarMatches);
}

