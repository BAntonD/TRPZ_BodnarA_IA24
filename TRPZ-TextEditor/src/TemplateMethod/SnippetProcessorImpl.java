package TemplateMethod;

import javax.swing.*;

public class SnippetProcessorImpl extends SnippetProcessor {
    public SnippetProcessorImpl() {
        super(
                new DefaultExactMatchHandler(),
                new DefaultSimilarMatchesHandler(),
                new DefaultNoMatchHandler()
        );
    }
}



