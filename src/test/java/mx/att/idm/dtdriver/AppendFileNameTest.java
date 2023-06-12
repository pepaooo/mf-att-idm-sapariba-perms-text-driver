package mx.att.idm.dtdriver;

import com.novell.nds.dirxml.driver.delimitedtext.Tracer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class AppendFileNameTest {

    static Logger logger = LogManager.getLogger(AppendFileNameTest.class);

    static Tracer tracer;

    @BeforeClass
    public static void buildObjects() {
        tracer = new LocalTracer();
    }

    @Test
    public void addToCSV_All_Interno() throws Exception {
        Instant start = Instant.now();
        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs\\" +
                "informacion-att\\sf\\IE_IDM_Internos_All\\IE_IDM_Internos.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void addToCSV_All_Externo() throws Exception {
        Instant start = Instant.now();
        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\IE_IDM_Externos_All\\IE_IDM_Externos.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }


    @Test
    public void validateEncoding_SF_Externos_1() throws Exception {
        Instant start = Instant.now();
        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Externos_19022020_0400.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_SF_Externos_2() throws Exception {
        Instant start = Instant.now();
        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Externos_20022020_0400.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_SF_Externos_3() throws Exception {
        Instant start = Instant.now();
        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\historic\\IE_IDM_InternosExternos_Al_24Feb20\\IE_IDM_Externos_Especial_19022019_0400.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_SF_Empleados_1() throws Exception {
        Instant start = Instant.now();
        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\historic\\IE_IDM_InternosExternos_Al_24Feb20\\IE_IDM_Internos_Especial_19022019_0400.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_SF_Empleados_2() throws Exception {
        Instant start = Instant.now();
        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Internos_20022020_0400.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_SF_Empleados_bof() throws Exception {
        Instant start = Instant.now();
        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Internos_09112019_0400.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_WP_Externos_1() throws Exception {
        Instant start = Instant.now();

        logger.info("DefaultCharSet: {}", Charset.defaultCharset().displayName());

        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Externos_ConCorreo_20022020.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_WP_Externos_2() throws Exception {
        Instant start = Instant.now();

        logger.info("DefaultCharSet: {}", Charset.defaultCharset().displayName());

        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\historic\\IE_IDM_InternosExternos_ConCorreo_Al_24Feb20\\" +
                "IE_IDM_Externos_ConCorreo_05062019.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_WP_Empleados_1() throws Exception {
        Instant start = Instant.now();

        logger.info("DefaultCharSet: {}", Charset.defaultCharset().displayName());

        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Internos_ConCorreo_18022020.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_WP_Empleados_2() throws Exception {
        Instant start = Instant.now();

        logger.info("DefaultCharSet: {}", Charset.defaultCharset().displayName());

        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Internos_ConCorreo_19022020.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    @Test
    public void validateEncoding_WP_Empleados_3() throws Exception {
        Instant start = Instant.now();

        logger.info("DefaultCharSet: {}", Charset.defaultCharset().displayName());

        AppendFileName appendFileName = new AppendFileName();
        appendFileName.init("{\"charset\":\"UTF-8\",\"tagName\":\"input\",\"delimiter\":\",\",\"tagPosition\":\"begin\"}", tracer);
        appendFileName.addToCSV(Paths.get("C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\historic\\IE_IDM_InternosExternos_ConCorreo_Al_24Feb20\\" +
                "IE_IDM_Internos_ConCorreo_10022020.csv").toFile());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.info("Execution time :: " + timeElapsed);
    }

    public static class LocalTracer implements Tracer {

        @Override
        public void traceMessage(String s) {
            logger.info(s);
        }
    }


}