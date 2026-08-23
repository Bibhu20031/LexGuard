package com.lexguard.lexguardbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClauseSegmentationServiceTest {

    private ClauseSegmentationService clauseSegmentationService;

    @BeforeEach
    void setUp() {
        clauseSegmentationService = new ClauseSegmentationService();
    }

    @Test
    void shouldSplitNumberedClauses() {

        String text = """
                1. Rent

                The tenant shall pay Rs. 20,000 every month.

                2. Security Deposit

                The tenant shall provide a deposit of Rs. 60,000.

                3. Termination

                Either party may terminate this agreement.
                """;

        List<String> clauses =
                clauseSegmentationService.segmentClauses(text);

        assertEquals(3, clauses.size());

        assertTrue(clauses.get(0).startsWith("1. Rent"));
        assertTrue(clauses.get(1).startsWith("2. Security Deposit"));
        assertTrue(clauses.get(2).startsWith("3. Termination"));
    }

    @Test
    void shouldHandleSubClauses() {

        String text = """
                1. Rent

                The tenant shall pay rent.

                1.1 Payment Date

                Rent must be paid on the first day of every month.

                1.2 Late Payment

                A late fee may be charged.

                2. Termination

                Either party may terminate the agreement.
                """;

        List<String> clauses =
                clauseSegmentationService.segmentClauses(text);

        assertEquals(4, clauses.size());

        assertTrue(clauses.get(0).startsWith("1. Rent"));
        assertTrue(clauses.get(1).startsWith("1.1 Payment Date"));
        assertTrue(clauses.get(2).startsWith("1.2 Late Payment"));
        assertTrue(clauses.get(3).startsWith("2. Termination"));
    }

    @Test
    void shouldNotSplitNumberInsideParagraph() {

        String text = """
                1. Rent

                The rent must be paid on the 1. day of every month.

                2. Deposit

                The tenant must pay the security deposit.
                """;

        List<String> clauses =
                clauseSegmentationService.segmentClauses(text);

        assertEquals(2, clauses.size());

        assertTrue(
                clauses.get(0).contains(
                        "The rent must be paid on the 1. day"
                )
        );
    }

    @Test
    void shouldReturnEmptyListForEmptyText() {

        List<String> clauses =
                clauseSegmentationService.segmentClauses("");

        assertTrue(clauses.isEmpty());
    }

    @Test
    void shouldReturnEmptyListForNullText() {

        List<String> clauses =
                clauseSegmentationService.segmentClauses(null);

        assertTrue(clauses.isEmpty());
    }

    @Test
    void shouldRemoveExcessiveWhitespace() {

        String text = """
                1. Rent



                The tenant shall pay rent.



                2. Deposit



                The tenant shall provide a deposit.
                """;

        List<String> clauses =
                clauseSegmentationService.segmentClauses(text);

        assertEquals(2, clauses.size());

        assertFalse(clauses.get(0).startsWith(" "));
        assertFalse(clauses.get(0).endsWith(" "));

        assertFalse(clauses.get(1).startsWith(" "));
        assertFalse(clauses.get(1).endsWith(" "));
    }
}