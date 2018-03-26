package com.pl.impaq.pointOfSale.application;

import com.pl.impaq.pointOfSale.domain.*;
import org.assertj.core.data.Index;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PointOfSaleTest {
    private final BarCode EXISTING_CODE = new BarCode("code");
    private final BarCode NOT_EXISTING_CODE = new BarCode("not code");
    private final BarCode INVALID_CODE = new BarCode("");
    private final BarCode EXIT_CODE = new BarCode("exit");
    private final Product SAMPLE_PRODUCT = new Product(EXISTING_CODE, "name", BigDecimal.valueOf(12.35));
    @Mock
    private Display mockedDisplay;
    @Mock
    private CodeScanner mockedScanner;
    @Mock
    private ProductRepository mockedRepository;
    @Mock
    private Printer mockedPrinter;
    @InjectMocks
    private PointOfSale sut = new PointOfSale(mockedDisplay, mockedRepository, mockedScanner, mockedPrinter);

    @Before
    public void setUpRepositoryResponses() {
        when(mockedRepository.getOptional(EXISTING_CODE)).thenReturn(Optional.of(SAMPLE_PRODUCT));
        when(mockedRepository.getOptional(NOT_EXISTING_CODE)).thenReturn(Optional.empty());
    }

    @Test
    public void shouldDisplayCorrectMessageWhenValidCodeIsScanned() {
        when(mockedScanner.getNextCode()).thenReturn(EXISTING_CODE, EXIT_CODE);

        sut.handleTransaction();

        assertFirstDisplayMessageIsEqual("name: 12.35");
    }

    @Test
    public void shouldDisplayCorrectMessageWhenInvalidCodeIsScanned() {
        when(mockedScanner.getNextCode()).thenReturn(NOT_EXISTING_CODE, EXIT_CODE);

        sut.handleTransaction();

        assertFirstDisplayMessageIsEqual("Product not found");
    }

    @Test
    public void shouldDisplayCorrectMessageWhenEmptyCodeIsScanned() {
        when(mockedScanner.getNextCode()).thenReturn(INVALID_CODE, EXIT_CODE);

        sut.handleTransaction();

        assertFirstDisplayMessageIsEqual("Invalid bar code");
    }

    @Test
    public void shouldDisplayTotalWhenTransactionIsFinished() {
        when(mockedScanner.getNextCode()).thenReturn(EXISTING_CODE, EXIT_CODE);

        sut.handleTransaction();

        assertDisplayedMessageOnCorrespondingPlaceIsEqual("Total: 12.35", 1);
    }

    @Test
    public void shouldPrintReceiptWhenTransactionIsFinished() {
        when(mockedScanner.getNextCode()).thenReturn(EXISTING_CODE, EXIT_CODE);

        sut.handleTransaction();
    }

    private Receipt createExpectedReceipt() {
        List<ReceiptLine> receiptLines = new LinkedList<>();
        receiptLines.add(new ReceiptLine("name", BigDecimal.valueOf(12.35)));
        return new Receipt(receiptLines);
    }

    private void assertFirstDisplayMessageIsEqual(String message) {
        assertDisplayedMessageOnCorrespondingPlaceIsEqual(message, 0);
    }

    private void assertDisplayedMessageOnCorrespondingPlaceIsEqual(String displayMessage, int place) {
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockedDisplay, Mockito.times(2)).showMessage(messageCaptor.capture());
        assertThat(messageCaptor.getAllValues()).contains(displayMessage, Index.atIndex(place));
    }

}
