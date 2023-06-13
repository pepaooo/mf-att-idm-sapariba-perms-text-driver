package mx.att.idm.dtdriver.sapariba;

import com.novell.nds.dirxml.driver.delimitedtext.StatusException;
import com.novell.nds.dirxml.driver.delimitedtext.Tracer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GuessCharset {

    static Logger logger = LogManager.getLogger(GuessCharset.class);


    @Test
    public void guessCharSet_SF_Externos_1() {
        String inputFile = "C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Externos_19022020_0400.csv";
        this.testEnconding(inputFile);
    }


    @Test
    public void guessCharSet_SF_Externos_2() {
        String inputFile = "C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Externos_20022020_0400.csv";
        this.testEnconding(inputFile);
    }

    @Test
    public void guessCharSet_SF_Externos_3() {
        String inputFile = "C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Externos_21022020_0400.csv";
        this.testEnconding(inputFile);
    }

    @Test
    public void guessCharSet_SF_Internos_bof() {
        String inputFile = "C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Internos_09112019_0400.csv";
        this.testEnconding(inputFile);
    }

    @Test
    public void guessCharSet_WP_Empleados_1() {
        String inputFile = "C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Internos_ConCorreo_18022020.csv";
        this.testEnconding(inputFile);
    }

    @Test
    public void guessCharSet_WP_Empleados_2() {
        String inputFile = "C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs" +
                "\\informacion-att\\sf\\validateEncoding\\IE_IDM_Internos_ConCorreo_19022020.csv";
        this.testEnconding(inputFile);
    }

    @Test
    public void guessCharSet_WP_All() {
        String directory = "C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs\\informacion-att\\sf\\" +
                "validateEncoding\\historic\\IE_IDM_InternosExternos_ConCorreo_Al_24Feb20";
        this.testDirectoryEncoding(directory);
    }

    @Test
    public void guessCharSet_SF_All() {
        String directory = "C:\\Users\\novell\\Documents\\microfocus\\projects\\att\\docs\\informacion-att\\sf\\" +
                "validateEncoding\\historic\\IE_IDM_InternosExternos_Al_24Feb20";
        this.testDirectoryEncoding(directory);
    }

    /*
    private void testEnconding(String inputFile){
        try {
            Charset charset = AppendFileName.guessCharset(new FileInputStream(inputFile));
            String guessChartSet = charset.name();
            logger.info("Charset :: " + guessChartSet);

        } catch (IOException e) {
            logger.error(e);
        }
    }
    */

    private void testEnconding(String inputFile) {
        try {
            SapAribaFile sapAribaFile = new SapAribaFile();
            sapAribaFile.init("", new LocalTracer());
            String guessChartSet = sapAribaFile.guessCharset(Paths.get(inputFile).toFile());
            logger.info("Charset :: " + guessChartSet);

        } catch (StatusException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testDirectoryEncoding(String directory) {
        SapAribaFile sapAribaFile = new SapAribaFile();
        try {
            sapAribaFile.init("", new LocalTracer());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {
            walk.filter(Files::isRegularFile).forEach(path -> {
                        logger.info(path.getFileName());
                        String guessChartSet = null;
                        try {
                            guessChartSet = sapAribaFile.guessCharset(path.toFile());
                            //logger.info("Charset :: " + guessChartSet);
                        } catch (StatusException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class LocalTracer implements Tracer {

        @Override
        public void traceMessage(String s) {
            logger.info(s);
        }
    }
}
