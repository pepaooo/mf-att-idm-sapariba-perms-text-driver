package mx.att.idm.dtdriver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.novell.nds.dirxml.driver.delimitedtext.*;
import com.opencsv.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Agrega el nombre del archivo de entrada en cada registro del archivo
 * Referencias: https://stackoverflow.com/questions/499010/java-how-to-determine-the-correct-charset-encoding-of-a-stream
 */
public class AppendFileName implements PreProcessor {

    //** Variables
    private static final String STR_BEGIN = "begin";
    private Tracer tracer = null;
    private char delimiter = ',';
    private String charSet = "UTF-8";
    private String tagName = "fileName";
    private String tagPosition = STR_BEGIN;
    private boolean validateHeader = false;
    private long minAttrsHeader = 0;

    /**
     * Init from PreProcessor
     *
     * @param arg0
     * @param tracer
     * @throws StatusException
     * @throws Exception
     */
    @Override
    public void init(String arg0, Tracer tracer) throws StatusException, Exception {
        this.tracer = tracer;
        tracer.traceMessage("----AppendFileName Init----");
        try {
            if (arg0 != null && !arg0.isEmpty()) {
                //create ObjectMapper instance
                ObjectMapper objectMapper = new ObjectMapper();

                //read JSON like DOM Parser
                JsonNode rootNode = objectMapper.readTree(arg0);
                JsonNode idNode = rootNode.path("charset");
                if (idNode.isValueNode()) {
                    this.charSet = idNode.asText();
                }
                idNode = rootNode.path("delimiter");
                if (idNode.isValueNode()) {
                    this.delimiter = idNode.asText().charAt(0);
                }
                idNode = rootNode.path("tagName");
                if (idNode.isValueNode()) {
                    this.tagName = idNode.asText();
                }
                idNode = rootNode.path("tagPosition");
                if (idNode.isValueNode()) {
                    this.tagPosition = idNode.asText();
                }
                idNode = rootNode.path("validateHeader");
                if (idNode.isValueNode()) {
                    this.validateHeader = idNode.asBoolean();
                }
                idNode = rootNode.path("minAttrsHeader");
                if (idNode.isValueNode()) {
                    this.minAttrsHeader = idNode.asLong();
                }
            }
        } catch (IOException e) {
            tracer.traceMessage("JSONExeception: " + e.getLocalizedMessage());
        }
        tracer.traceMessage("----tagName: " + this.tagName);
        tracer.traceMessage("----charSet: " + this.charSet);
        tracer.traceMessage("----delimiter: " + this.delimiter);
        tracer.traceMessage("----tagPosition: " + this.tagPosition);
        tracer.traceMessage("----validateHeader: " + this.validateHeader);
        tracer.traceMessage("----minAttrsHeader: " + this.minAttrsHeader);
    }

    /**
     * nextInputFile from PreProcessor
     *
     * @param inputFile
     * @throws SkipFileException
     * @throws WaitException
     * @throws StatusException
     * @throws Exception
     */
    @Override
    public void nextInputFile(File inputFile) throws SkipFileException, WaitException, StatusException, Exception {
        this.tracer.traceMessage("-----AppendFileName nextInputFile-----");
        if (inputFile.getName().matches("(?i:.*\\.csv$)")) {
            addToCSV(inputFile);
        } else {
            throw new SkipFileException();
        }
    }

    /**
     * Lógica de la clase para archivos CSV
     *
     * @param inputFile
     */
    public void addToCSV(File inputFile) throws StatusException {
        int counter = 0;
        List<String> listRecordOut = null;
        List<String[]> listOfLines = new ArrayList();

        this.tracer.traceMessage("Incoming CSV File : " + inputFile.getName());

        // Obtener el charsetname del archivo
        String guessChartSet = guessCharset(inputFile);
        this.tracer.traceMessage("Charset CSV File :: " + guessChartSet);

        //reader = new CSVReader(new InputStreamReader(new FileInputStream(inputFile), guessChartSet), this.delimiter);
        CSVParser parser = new CSVParserBuilder().withSeparator(this.delimiter).build();
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(inputFile), guessChartSet)).withCSVParser(parser).build();) {
            this.tracer.traceMessage("reader :: " + reader);

            String[] recordIn;
            while ((recordIn = reader.readNext()) != null) {
                int rLength = recordIn.length;
                // Validación del header
                if (this.validateHeader && counter == 0) {
                    if (!(rLength >= this.minAttrsHeader)) {
                        throw new StatusException(StatusException.STATUS_FATAL, "The input file does not comply with the format or minimum attributes");
                    }
                    this.tracer.traceMessage("Validation of minimum attributes in header :: " + rLength);
                    counter++;
                }
                if (this.tagPosition.equalsIgnoreCase(STR_BEGIN)) {
                    if (recordIn[0].matches("(?i:^" + this.tagName + "=.*)")) {
                        String[] recordOut = (String[]) Arrays.copyOf(recordIn, recordIn.length);
                        recordOut[0] = (this.tagName + "=" + inputFile.getName());
                        listOfLines.add(recordOut);
                    } else {
                        listRecordOut = Arrays.stream(recordIn).collect(Collectors.toList());
                        listRecordOut.add(0, this.tagName + "=" + inputFile.getName());
                        listOfLines.add(listRecordOut.toArray(new String[0]));
                    }
                } else {
                    if (recordIn[(rLength - 1)].matches("(?i:^" + this.tagName + "=.*)")) {
                        String[] recordOut = (String[]) Arrays.copyOf(recordIn, recordIn.length);
                        recordOut[(rLength - 1)] = (this.tagName + "=" + inputFile.getName());
                        listOfLines.add(recordOut);
                    } else {
                        listRecordOut = Arrays.stream(recordIn).collect(Collectors.toList());
                        listRecordOut.add(rLength, this.tagName + "=" + inputFile.getName());
                        listOfLines.add(listRecordOut.toArray(new String[0]));
                    }
                }
            }
        } catch (IOException e) {
            this.tracer.traceMessage("Exception: " + e.getLocalizedMessage());
            throw new StatusException(StatusException.STATUS_FATAL, "Se ha presentado la siguiente excepción en la " +
                    "extensión AppendFileName[reader-addToCSV], Exception: " + e.getMessage());
        }


        //writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(inputFile), this.charSet), this.delimiter);
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(inputFile), this.charSet),
                this.delimiter, CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
            writer.writeAll(listOfLines);
            this.tracer.traceMessage("File Modified Successfully with " + listOfLines.size() + " lines");
        } catch (IOException e) {
            this.tracer.traceMessage("Exception: " + e.getLocalizedMessage());
            throw new StatusException(StatusException.STATUS_FATAL, "Se ha presentado la siguiente excepción en la " +
                    "extensión AppendFileName[writer-addToCSV], Exception: " + e.getMessage());
        }

    }

    /**
     * Método para obtener el charset de un archivo
     *
     * @param inputFile
     * @return
     * @throws IOException
     */
    public String guessCharset(File inputFile) throws StatusException {
        String charset = StandardCharsets.UTF_8.name(); //Default chartset
        byte[] fileContent = null;
        FileInputStream fin = null;
        try {

            //create FileInputStream object
            fin = new FileInputStream(inputFile.getPath());

            /*
             * Create byte array large enough to hold the content of the file.
             * Use File.length to determine size of the file in bytes.
             */
            fileContent = new byte[(int) inputFile.length()];

            /*
             * To read content of the file in byte array, use
             * int read(byte[] byteArray) method of java FileInputStream class.
             *
             */
            fin.read(fileContent);

            byte[] data = fileContent;

            CharsetDetector detector = new CharsetDetector();
            detector.setText(data);

            CharsetMatch cm = detector.detect();

            if (cm != null) {
                int confidence = cm.getConfidence();
                //System.out.println("Encoding: " + cm.getName() + " - Confidence: " + confidence + "%");
                this.tracer.traceMessage("Encoding: " + cm.getName() + " - Confidence: " + confidence + "%");
                //Here you have the encode name and the confidence
                //In my case if the confidence is > x I return the encode, else I return the default value
                if (confidence > 0) {
                    charset = cm.getName();
                }
            }
        } catch (IOException e) {
            throw new StatusException(StatusException.STATUS_FATAL, "Se ha presentado la siguiente excepción en la " +
                    "extensión AppendFileName[guessCharset], Exception: " + e.getMessage());
        } finally {
            if (fin != null)
                try {
                    fin.close();
                } catch (IOException e) {
                    throw new StatusException(StatusException.STATUS_FATAL, "Se ha presentado la siguiente excepción en la " +
                            "extensión AppendFileName[guessCharset-close], Exception: " + e.getMessage());
                }
        }
        return charset;
    }

}
