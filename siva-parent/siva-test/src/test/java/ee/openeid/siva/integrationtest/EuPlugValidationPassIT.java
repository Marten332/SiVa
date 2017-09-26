package ee.openeid.siva.integrationtest;

import org.apache.commons.codec.binary.Base64;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Ignore
public class EuPlugValidationPassIT extends SiVaRestTests {

    @Value("${plugtest.location}")
    private String location;

    @Override
    protected String getTestFilesDirectory() {
        return location;
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-1
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Lithuania adoc-v2.0 signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-LT_MIT-1.asice
     */
    @Test
    public void lithuaniaAsiceAdoc20ValidSignature() {
        post(validationRequestForEu("Signature-A-LT_MIT-1.asice"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-2
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation multiple of Lithuania adoc-v2.0 signatures
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-LT_MIT-2.asice
     */
    @Test
    public void lithuaniaAsiceAdoc20TwoValidSignatures() {
        post(validationRequestForEu("Signature-A-LT_MIT-2.asice"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[1].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[1].signatureFormat", Matchers.is("XAdES-BASELINE-B"))
                .body("signatures[1].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[1].warnings", Matchers.hasSize(0))
                .body("validSignaturesCount", Matchers.is(2))
                .body("signaturesCount", Matchers.is(2))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-3
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Lithuania adoc-v2.0 signature with warning
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-LT_MIT-5.asice"
     */
    @Test //TODO: need to check specifics about this warning, whether it is ok or not
    public void lithuaniaAsiceAdoc20ValidSignatureWithWarning() {
        post(validationRequestForEu("Signature-A-LT_MIT-5.asice"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings[0].content", Matchers.is("The 'issuer-serial' attribute is absent or does not match!"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-4
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Latvian edoc-v2.0 signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-LV_EUSO-1.asice
     */
    @Test
    public void latviaAsiceEdoc20ValidSignature() {
        post(validationRequestForEu("Signature-A-LV_EUSO-1.asice"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-T"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-5
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title:
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-LV_EUSO-2.asice
     */
    @Test //TODO: this file is actually identical to the Signature-A-LV_EUSO-1.asice
    public void A_LV_EUSO_2Valid() {
        post(validationRequestForEu("Signature-A-LV_EUSO-2.asice"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-6
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Polish Asic-s with CAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-PL_KIR-1.asics
     */
    @Test //TODO: the warning message needs to be investigated, seems that the certificate intended usage ALL is not acceptable?
    public void polandAsicsCadesValidSignatureWithWarning() {
        post(validationRequestForEu("Signature-A-PL_KIR-1.asics"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("CAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QES"))
                .body("signatures[0].warnings[0].content", Matchers.is("The certificate is not for eSig at signing time!"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-7
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Polish Asic-s with XAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-PL_KIR-2.asics
     */
    @Test //TODO: Same cert is used as in Signature-A-PL_KIR-1.asics
    public void polandAsicsXadesValidSignatureWithWarning() {
        post(validationRequestForEu("Signature-A-PL_KIR-2.asics"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QES"))
                .body("signatures[0].warnings[0].content", Matchers.is("The certificate is not for eSig at signing time!"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-8
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Slovakia Asic-e with XAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-SK_DIT-3.asice
     */
    @Test
    public void slovakiaAsiceXadesValidSignature() {
        post(validationRequestForEu("Signature-A-SK_DIT-3.asice"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-LTA"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-05-02T09:16:58Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-05-02T09:35:58Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-9
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Slovakia Asic-s with XAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-SK_DIT-7.asics
     */
    @Test
    public void slovakiaAsicsXadesValidSignature() {
        post(validationRequestForEu("Signature-A-SK_DIT-7.asics"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-LTA"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2015-08-19T09:31:40Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2015-09-21T13:27:17Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-10
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title:
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-C-AT_SIT-1.p7m
     */
    @Test
    @Ignore //TODO: Austrian TLS needs to be enabled
    public void austrianCadesValidSignature() {
        post(validationRequestForEu("Signature-C-AT_SIT-1.p7m"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("CAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings[0].content", Matchers.is("The 'issuer-serial' attribute is absent or does not match!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-03-31T14:41:57Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-11
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of German CAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-C-DE_SCI-1.p7m
     */
    @Test //TODO: Warning message needs to be investigated
    public void germanyCadesValidSignatureWithWarning() {
        post(validationRequestForEu("Signature-C-DE_SCI-1.p7m"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("CAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings[0].content", Matchers.is("The 'issuer-serial' attribute is absent or does not match!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-03-31T14:41:57Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-12
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title:
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-C-ES_MIN-1.p7m
     */
    @Test
    @Ignore //TODO: Spain has IP block on CRL access, we are not able to fetch revocation data
    public void spainCadesBValidSignature() {
        post(validationRequestForEu("Signature-C-ES_MIN-1.p7m"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("CAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings[0].content", Matchers.is("The 'issuer-serial' attribute is absent or does not match!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-11T07:30:26Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-13
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title:
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-C-ES_MIN-2.p7m
     */
    @Test
    @Ignore //TODO: Spain has IP block on CRL access, we are not able to fetch revocation data
    public void spainCadesTValidSignature() {
        post(validationRequestForEu("Signature-C-ES_MIN-2.p7m"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("CAdES-BASELINE-T"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings[0].content", Matchers.is("The 'issuer-serial' attribute is absent or does not match!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-11T07:30:27Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-11T07:30:29Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-14
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Italian Cades signatures
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-C-IT_BIT-5.p7m
     */
    @Test //TODO: Needs investigation why is second signatureLevel NA!
    public void italyCadesTwoValidSignatures() {
        post(validationRequestForEu("Signature-C-IT_BIT-5.p7m"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("CAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-22T14:07:35Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("signatures[1].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[1].signatureFormat", Matchers.is("CAdES-BASELINE-B"))
                .body("signatures[1].signatureLevel", Matchers.is("NA"))
                .body("signatures[1].warnings", Matchers.hasSize(0))
                .body("signatures[1].claimedSigningTime", Matchers.is("2016-04-22T14:08:35Z"))
                .body("signatures[1].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(2))
                .body("signaturesCount", Matchers.is(2))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-15
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Poland CAdES B signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-C-PL_ADS-4.p7m
     */
    @Test //TODO: Warning message needs to be investigated, but correlates with signatureLevel
    public void polandCadesValidSignatureWithWarning() {
        post(validationRequestForEu("Signature-C-PL_ADS-4.p7m"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("CAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("ADESIG_QC"))
                .body("signatures[0].warnings[0].content", Matchers.is("The signature/seal is not created by a QSCD!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-08T12:09:38Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-16
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Poland CAdES T signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-C-PL_ADS-7.p7m
     */
    @Test
    public void polandCadesValidSignature() {
        post(validationRequestForEu("Signature-C-PL_ADS-7.p7m"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("CAdES-BASELINE-T"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-08T08:41:09Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-08T08:41:19Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-17
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Belgium PAdES B signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-BE_CONN-1.pdf
     */
    @Test
    public void belgiumPadesValidSignature() {
        post(validationRequestForEu("Signature-P-BE_CONN-1.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("ADESIG_QC"))
                .body("signatures[0].warnings[0].content", Matchers.is("The signature/seal is not created by a QSCD!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-14T13:28:54Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-18
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Belgian PAdES LTA signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-BE_CONN-7.pdf
     */
    @Test //TODO: warnings need to be investigated, especially the type identifier problem
    public void belgiumPadesValidSignatureWithWarnings() {
        post(validationRequestForEu("Signature-P-BE_CONN-7.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-LTA"))
                .body("signatures[0].signatureLevel", Matchers.is("ADESIG_QC"))
                .body("signatures[0].warnings[0].content", Matchers.is("The signature/seal is not created by a QSCD!"))
                .body("signatures[0].warnings[1].content", Matchers.is("The trust service of the timestamp has not expected type identifier!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-14T14:03:00Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-14T14:03:24Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-19
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of German PAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-DE_SCI-2.pdf
     */
    @Test //TODO: warning message needs to be investigated
    public void germanyPadesValidSignatureWithWarning() {
        post(validationRequestForEu("Signature-P-DE_SCI-2.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings[0].content", Matchers.is("The 'issuer-serial' attribute is absent or does not match!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-03-31T14:49:57Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-20
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Italian PAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-IT_MID-1.pdf
     */
    @Test
    public void italyPadesValidSignature() {
        post(validationRequestForEu("Signature-P-IT_MID-1.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-05T08:25:27Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-21
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Lithuanian PAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-LT_MIT-1.pdf
     */
    @Test
    public void lithuaniaPadesValidSignature() {
        post(validationRequestForEu("Signature-P-LT_MIT-1.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-T"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-08T10:16:06Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-08T10:16:20Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-22
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Lithuanian PAdES signature 2
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-LT_MIT-2.pdf
     */
    @Test //TODO: Need to investigate what is difference between this and Signature-P-LT_MIT-1.pdf file beside the signing times?
    public void lithuaniaPadesValidSignature2() {
        post(validationRequestForEu("Signature-P-LT_MIT-2.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-T"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-08T10:14:19Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-08T10:14:45Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-23
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Latvian PAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-LV_EUSO-1.pdf
     */
    @Test
    public void latviaPadesValidSignature() {
        post(validationRequestForEu("Signature-P-LV_EUSO-1.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-LT"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-11T13:33:37Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-11T13:33:49Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-24
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title:
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-LV_EUSO-2.pdf
     */
    @Test //TODO: this file is identical to Signature-P-LV_EUSO-1.pdf
    public void P_LV_EUSO_2Valid() {
        post(validationRequestForEu("Signature-P-LV_EUSO-2.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-LT"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings", Matchers.hasSize(0))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-11T13:33:37Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-11T13:33:49Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-25
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Polish PAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-PL_ADS-6.pdf
     */
    @Test //TODO: warnings need to be investigated
    public void polandPadesValidSignatureWithWarnings() {
        post(validationRequestForEu("Signature-P-PL_ADS-6.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-LT"))
                .body("signatures[0].signatureLevel", Matchers.is("ADESIG_QC"))
                .body("signatures[0].warnings[0].content", Matchers.is("The signature/seal is not created by a QSCD!"))
                .body("signatures[0].warnings[1].content", Matchers.is("The 'issuer-serial' attribute is absent or does not match!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-08T12:56:31Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-08T12:56:42Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-26
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Polish PAdES QES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-P-PL_ADS-8.pdf
     */
    @Test //TODO: warning message needs to be investigated
    public void polandPadesValidQesSignature() {
        post(validationRequestForEu("Signature-P-PL_ADS-8.pdf"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("PAdES-BASELINE-LT"))
                .body("signatures[0].signatureLevel", Matchers.is("QESIG"))
                .body("signatures[0].warnings[0].content", Matchers.is("The 'issuer-serial' attribute is absent or does not match!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-08T08:47:28Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is("2016-04-08T08:47:38Z"))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-27
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title:
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-LT_MIT-1.asice
     */
    @Test
    @Ignore("No TLS")
    public void X_AT_SIT_1Valid() {
        post(validationRequestForEu("Signature-X-AT_SIT-1.xml"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-28
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title:
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-A-LT_MIT-1.asice
     */
    @Test
    @Ignore("No TLS")
    public void X_AT_SIT_21Valid() {
        post(validationRequestForEu("Signature-X-AT_SIT-21.xml"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-29
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title: Validation of Belgian XAdES signature
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-X-BE_CONN-1.xml
     */
    @Test
    public void belgiumXadesValidSignature() {
        post(validationRequestForEu("Signature-X-BE_CONN-1.xml"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("ADESIG_QC"))
                .body("signatures[0].warnings[0].content", Matchers.is("The signature/seal is not created by a QSCD!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-18T11:02:37Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    /**
     * TestCaseID: EuPlug-ValidationPass-30
     *
     * TestType: Automated
     *
     * Requirement:
     *
     * Title:
     *
     * Expected Result: The document should pass the validation
     *
     * File: Signature-X-BE_CONN-21.xml
     */
    @Test //TODO: what is the difference between this and Signature-X-BE_CONN-1.xml beside signing time?
    public void X_BE_CONN_21Valid() {
        post(validationRequestForEu("Signature-X-BE_CONN-21.xml"))
                .then()
                .body("signatures[0].indication", Matchers.is("TOTAL-PASSED"))
                .body("signatures[0].signatureFormat", Matchers.is("XAdES-BASELINE-B"))
                .body("signatures[0].signatureLevel", Matchers.is("ADESIG_QC"))
                .body("signatures[0].warnings[0].content", Matchers.is("The signature/seal is not created by a QSCD!"))
                .body("signatures[0].claimedSigningTime", Matchers.is("2016-04-18T11:03:29Z"))
                .body("signatures[0].info.bestSignatureTime", Matchers.is(""))
                .body("validSignaturesCount", Matchers.is(1))
                .body("signaturesCount", Matchers.is(1))
                .body("validationWarnings", Matchers.hasSize(0));
    }

    private String validationRequestForEu(String file){
        return validationRequestForEuWithPolicy(file, VALID_SIGNATURE_POLICY_3);
    }

    private String validationRequestForEuWithPolicy(String file, String policy){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("document", Base64.encodeBase64String(Files.readAllBytes(Paths.get(getTestFilesDirectory() + file))));
            jsonObject.put("filename", file);
            jsonObject.put("signaturePolicy", policy);
        }catch (IOException e){
            throw new RuntimeException("Error on reading file", e);
        }
        return jsonObject.toString();
    }
}