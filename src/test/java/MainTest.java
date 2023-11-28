

import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import org.generateqr.Main;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testGenerateQRCode() {
        try {
            File qrCodeFile = Main.generateQRCode("test data", 300, 300);
            assertNotNull(qrCodeFile);
            assertTrue(qrCodeFile.exists());
            assertTrue(qrCodeFile.isFile());
        } catch (WriterException | IOException e) {
            fail("Exception should not be thrown", e);
        }
    }

}
