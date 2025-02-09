package com.tamimSoft.fakeStore.controller;

import com.tamimSoft.fakeStore.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BrandControllerTests {


    @Mock
    private BrandService brandService;


    @InjectMocks
    private BrandController brandController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createJournalEntry_SuccessfulCreation_ReturnsCreated() {
//        // Arrange
//
//        doNothing().when(brandController).getAllBrands(anyString(), );
//
//        // Act
//        ResponseEntity<?> response = journalEntryController.createJournalEntry(journalEntryOto);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
