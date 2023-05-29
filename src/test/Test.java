import com.wavjaby.SvgBuilder;
import com.wavjaby.json.JsonArray;
import com.wavjaby.json.JsonObject;
import com.wavjaby.svgbuilder.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import java.util.*;

public class Test {

    Test() {

        List<Map.Entry<String, Long>> languages = new ArrayList<>(new LinkedHashMap<String, Long>() {{
            put("Java", 699888L);
            put("JavaScript", 479496L);
            put("CSS", 53702L);
            put("C", 13239L);
            put("HTML", 13081L);
            put("AutoHotkey", 1598L);
            put("Batchfile", 393L);
        }}.entrySet());

        String result;

        result = SvgBuilder.languagesToListFormSvg(languages, 10);
        try {
            FileWriter fileWriter = new FileWriter("out1.svg");
            fileWriter.write(result);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//
//        result = SvgBuilder.languagesToPieChartSvg(languages, 10);
//        try {
//            FileWriter fileWriter = new FileWriter("out2.svg");
//            fileWriter.write(result);
//            fileWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void main(String[] args) {
        new Test();
    }
}
