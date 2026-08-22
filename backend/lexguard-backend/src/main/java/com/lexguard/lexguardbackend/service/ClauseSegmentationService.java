package com.lexguard.lexguardbackend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClauseSegmentationService {

    /*
     * Matches clause numbering at the beginning of a line.
     * It intentionally requires the numbering to appear at the
     * beginning of a line so that something like "the 1. day"
     * inside a paragraph isn't treated as a new clause.
     *
     * Won't work on a contract that's not written pointwise
     */
    private static final Pattern CLAUSE_PATTERN =
            Pattern.compile("^\\s*\\d+(?:\\.\\d+)*\\.?\\s+.*$", Pattern.MULTILINE);

    public List<String> segmentClauses(String extractedText) {

        List<String> clauses = new ArrayList<>();

        if (extractedText == null || extractedText.isBlank()) {
            return clauses;
        }

        Matcher matcher = CLAUSE_PATTERN.matcher(extractedText);

        int clauseStart = -1;

        while (matcher.find()) {

            if (clauseStart != -1) {
                String clause = cleanClause(
                        extractedText.substring(clauseStart, matcher.start())
                );

                if (!clause.isBlank()) {
                    clauses.add(clause);
                }
            }

            clauseStart = matcher.start();
        }

        /*
         * Add the final clause because there is no next heading
         */
        if (clauseStart != -1) {
            String finalClause = cleanClause(
                    extractedText.substring(clauseStart)
            );

            if (!finalClause.isBlank()) {
                clauses.add(finalClause);
            }
        }

        return clauses;
    }

    private String cleanClause(String clause) {

        return clause
                .replaceAll("[ \\t]+", " ")
                .replaceAll("\\n{3,}", "\n\n")
                .trim();
    }
}