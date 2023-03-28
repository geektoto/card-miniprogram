import java.io.File;
import java.util.List;

import org.checkerframework.framework.test.CheckerFrameworkPerDirectoryTest;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test runner that uses the Checker Framework's tooling.
 */
public class CryptoPolicyComplianceTests extends CheckerFrameworkPerDirectoryTest {

    private static final String TEST_DATA_SUBDIR_NAME = "crypto-policy";

    public CryptoPolicyComplianceTests(List<File> testFiles) {
        super(
     