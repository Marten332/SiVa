package ee.sk.pdfvalidatortest;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BaselineProfileTests extends PdfValidatorSoapTests {

    @Test // this file is actually invalid
    public void baselineProfileBDocumentShouldFail() {
        String httpBody = post(validationRequestFor(readFile("hellopades-pades-b-sha256-auth.pdf"))).
                andReturn().body().asString();

        assertEquals(
                "The signature format is not allowed by the validation policy constraint!",
                findErrorById("BBB_VCI_ISFC_ANS_1", detailedReport(httpBody)));
        assertEquals(0, validSignatures(simpleReport(httpBody)));

    }

    @Ignore
    @Test // need non-plugtest test file
    public void baselineProfileTDocumentShouldFail() {
        String httpBody = post(validationRequestFor(readFile("some_file.pdf"))).
                andReturn().body().asString();

        assertEquals(
                "The signature format is not allowed by the validation policy constraint!",
                findErrorById("BBB_VCI_ISFC_ANS_1", detailedReport(httpBody)));
        assertEquals(0, validSignatures(simpleReport(httpBody)));
    }

    @Test
    public void baselineProfileLTDocumentShouldPass() {
        String httpBody = post(validationRequestFor(readFile("hellopades-pades-lt-sha256-sign.pdf"))).
                andReturn().body().asString();

        assertEquals(1, validSignatures(simpleReport(httpBody)));

    }

    @Ignore
    @Test // need non-plugtest test file
    public void baselineProfileLTADocumentShouldPass() {
        String httpBody = post(validationRequestFor(readFile("some_file.pdf"))).
                andReturn().body().asString();

        assertEquals(1, validSignatures(simpleReport(httpBody)));
    }

    @Test
    public void documentWithBaselineProfilesBAndLTASignaturesShouldPass() {
        String httpBody = post(validationRequestFor(readFile("hellopades-lt-b.pdf"))).
                andReturn().body().asString();

        assertEquals(1, validSignatures(simpleReport(httpBody)));
    }

    @Test
    public void documentMessageDigestAttributeValueDoesNotMatchCalculatedValue() {
        String httpBody = post(validationRequestFor(readFile("hellopades-lt1-lt2-wrongDigestValue.pdf"))).
                andReturn().body().asString();
        System.out.print(httpBody);
        assertEquals(1, validSignatures(simpleReport(httpBody)));
    }

    @Test
    public void documentSignedWithMultipleSignersSerialSignature() {
        String httpBody = post(validationRequestFor(readFile("hellopades-lt1-lt2-Serial.pdf"))).
                andReturn().body().asString();
        System.out.print(httpBody);
        assertEquals(1, validSignatures(simpleReport(httpBody)));
    }

    protected byte[] readFile(String fileName) {
        return readFileFromTestResources("baseline_profile_test_files/", fileName);
    }
}