package mx.att.idm.dtdriver.sapariba;

import com.novell.nds.dirxml.driver.delimitedtext.Tracer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class SapAribaFileTest {

    static Logger logger = LogManager.getLogger(SapAribaFileTest.class);

    static Tracer tracer;

    @BeforeClass
    public static void buildObjects() {
        tracer = new LocalTracer();
    }

    @Test
    public void readFile_A() throws Exception {
        Instant start = Instant.now();
        SapAribaFile sapAribaFile = new SapAribaFile();
        sapAribaFile.init("{\"charset\":\"UTF-8\",\"delimiter\":\",\",\"delimiterPerms\":\"|\",\"deleteRowsUntil\":\"2\",\"validateHeaders\":\"true\",\"minAttrsHeaders\":\"3\"}", tracer);
        sapAribaFile.groupCSV(Paths.get("C:\\Users\\Jose Luis Angulo\\Documents\\tmp\\sapariba\\driver-permissions\\GroupSharedUserMap_Export.csv").toFile());
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