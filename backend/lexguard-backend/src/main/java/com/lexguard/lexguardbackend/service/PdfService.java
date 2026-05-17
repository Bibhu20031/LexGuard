package com.lexguard.lexguardbackend.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfService {

    public String extractText(MultipartFile file) {

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {

            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            return pdfTextStripper.getText(document);

        } catch (IOException e) {
            throw new RuntimeException("Failed to extract PDF text");
        }
    }
}