import com.wavjaby.json.JsonArray;
import com.wavjaby.json.JsonObject;
import com.wavjaby.svgbuilder.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Test {
    private static class Vector2int {
        final int x, y;

        Vector2int(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ',' + y + ')';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Vector2int)
                return ((Vector2int) obj).x == this.x && ((Vector2int) obj).y == this.y;
            return false;
        }

        public double distance(Vector2int other) {
            return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
        }
    }

    private static class FeedData {
        final int tileShowDelay;
        final SvgRect tile;
        final Vector2int loc, realLocalLoc;
        int feedDelay;

        private FeedData(int tileShowDelay, SvgRect tile, Vector2int loc, Vector2int realLocalLoc) {
            this.tileShowDelay = tileShowDelay;
            this.tile = tile;
            this.loc = loc;
            this.realLocalLoc = realLocalLoc;
        }
    }

    Test() {
        SvgStyle style = new SvgStyle();
        style.addStyle(".card-title,.card-body",
                "font-family: 'Segoe UI', Ubuntu, Sans-Serif, monospace");
        style.addStyle(".card-title",
                "font-size: 18px;" +
                        "font-weight: bold;" +
                        "fill: #61dafb;" +
                        "animation: 800ms ease-in-out forwards fadeInAnimation");
        style.addStyle(".text-outline",
                "fill: #ffffff;" +
                        "paint-order: stroke;" +
                        "stroke: #000;" +
                        "stroke-width: 2px;" +
                        "stroke-linecap: round;" +
                        "stroke-linejoin: round");
        style.addStyle(".month-year",
                "font-size: 14px");
        style.addStyle(".weak-day",
                "font-size: 12px");
        style.addStyle(".fade-in",
                "opacity: 0;" +
                        "animation: 300ms ease-in-out forwards fadeInAnimation");
        style.addStyle(".lang-pie",
                "animation: forwards growPie");
        style.addStyle("@keyframes growPie",
                "to {stroke-dashoffset:0}");
        style.addStyle("@keyframes fadeInAnimation",
                "from {opacity: 0}" +
                        "to {opacity: 1}");


        final String title = "Contributions";
        SvgGroup cardTitle = new SvgGroup().setClass("card-title").setTransform(new AttrTranslate(25, 35)).appendChild(
                new SvgText(title)
        );
        SvgGroup cardBody = new SvgGroup().setClass("card-body").setTransform(new AttrTranslate(25, 55));
        JsonObject json = new JsonObject("{\"totalContributions\":163,\"weeks\":[{\"firstDay\":\"2022-02-06\",\"contributionDays\":[{\"date\":\"2022-02-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-07\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-10\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-12\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-02-13\",\"contributionDays\":[{\"date\":\"2022-02-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-16\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-17\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-19\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-02-20\",\"contributionDays\":[{\"date\":\"2022-02-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-21\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-26\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-02-27\",\"contributionDays\":[{\"date\":\"2022-02-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-02-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-03\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-04\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-05\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-03-06\",\"contributionDays\":[{\"date\":\"2022-03-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-07\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-10\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-12\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-03-13\",\"contributionDays\":[{\"date\":\"2022-03-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-16\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-17\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-19\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-03-20\",\"contributionDays\":[{\"date\":\"2022-03-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-21\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-26\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-03-27\",\"contributionDays\":[{\"date\":\"2022-03-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-30\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-03-31\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-02\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-04-03\",\"contributionDays\":[{\"date\":\"2022-04-03\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-04\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-05\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-07\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-09\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-04-10\",\"contributionDays\":[{\"date\":\"2022-04-10\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-12\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-16\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-04-17\",\"contributionDays\":[{\"date\":\"2022-04-17\",\"color\":\"#216e39\",\"contributionCount\":8},{\"date\":\"2022-04-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-19\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-21\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-23\",\"color\":\"#216e39\",\"contributionCount\":8}]},{\"firstDay\":\"2022-04-24\",\"contributionDays\":[{\"date\":\"2022-04-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-26\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-04-30\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-05-01\",\"contributionDays\":[{\"date\":\"2022-05-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-03\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-04\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-05\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-06\",\"color\":\"#40c463\",\"contributionCount\":2},{\"date\":\"2022-05-07\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-05-08\",\"contributionDays\":[{\"date\":\"2022-05-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-10\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-12\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-14\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-05-15\",\"contributionDays\":[{\"date\":\"2022-05-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-16\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-17\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-19\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-21\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-05-22\",\"contributionDays\":[{\"date\":\"2022-05-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-26\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-28\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-05-29\",\"contributionDays\":[{\"date\":\"2022-05-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-30\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-05-31\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-03\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-04\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-06-05\",\"contributionDays\":[{\"date\":\"2022-06-05\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-07\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-10\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-11\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-06-12\",\"contributionDays\":[{\"date\":\"2022-06-12\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-16\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-17\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-18\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-06-19\",\"contributionDays\":[{\"date\":\"2022-06-19\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-21\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-25\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-06-26\",\"contributionDays\":[{\"date\":\"2022-06-26\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-06-30\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-02\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-07-03\",\"contributionDays\":[{\"date\":\"2022-07-03\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-04\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-05\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-07\",\"color\":\"#216e39\",\"contributionCount\":7},{\"date\":\"2022-07-08\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-07-09\",\"color\":\"#9be9a8\",\"contributionCount\":1}]},{\"firstDay\":\"2022-07-10\",\"contributionDays\":[{\"date\":\"2022-07-10\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-07-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-12\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-07-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-16\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-07-17\",\"contributionDays\":[{\"date\":\"2022-07-17\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-19\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-21\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-23\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-07-24\",\"contributionDays\":[{\"date\":\"2022-07-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-26\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-07-30\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-07-31\",\"contributionDays\":[{\"date\":\"2022-07-31\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-03\",\"color\":\"#30a14e\",\"contributionCount\":4},{\"date\":\"2022-08-04\",\"color\":\"#40c463\",\"contributionCount\":3},{\"date\":\"2022-08-05\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-06\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-08-07\",\"contributionDays\":[{\"date\":\"2022-08-07\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-10\",\"color\":\"#40c463\",\"contributionCount\":2},{\"date\":\"2022-08-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-12\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-13\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-08-14\",\"contributionDays\":[{\"date\":\"2022-08-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-15\",\"color\":\"#30a14e\",\"contributionCount\":4},{\"date\":\"2022-08-16\",\"color\":\"#30a14e\",\"contributionCount\":5},{\"date\":\"2022-08-17\",\"color\":\"#40c463\",\"contributionCount\":2},{\"date\":\"2022-08-18\",\"color\":\"#40c463\",\"contributionCount\":3},{\"date\":\"2022-08-19\",\"color\":\"#30a14e\",\"contributionCount\":4},{\"date\":\"2022-08-20\",\"color\":\"#40c463\",\"contributionCount\":3}]},{\"firstDay\":\"2022-08-21\",\"contributionDays\":[{\"date\":\"2022-08-21\",\"color\":\"#40c463\",\"contributionCount\":3},{\"date\":\"2022-08-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-26\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-27\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-08-28\",\"contributionDays\":[{\"date\":\"2022-08-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-30\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-08-31\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-03\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-09-04\",\"contributionDays\":[{\"date\":\"2022-09-04\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-05\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-07\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-10\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-09-11\",\"contributionDays\":[{\"date\":\"2022-09-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-12\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-16\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-17\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-09-18\",\"contributionDays\":[{\"date\":\"2022-09-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-19\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-21\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-09-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-24\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-09-25\",\"contributionDays\":[{\"date\":\"2022-09-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-26\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-09-30\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-10-01\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-10-02\",\"contributionDays\":[{\"date\":\"2022-10-02\",\"color\":\"#216e39\",\"contributionCount\":7},{\"date\":\"2022-10-03\",\"color\":\"#40c463\",\"contributionCount\":3},{\"date\":\"2022-10-04\",\"color\":\"#216e39\",\"contributionCount\":6},{\"date\":\"2022-10-05\",\"color\":\"#40c463\",\"contributionCount\":2},{\"date\":\"2022-10-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-10-07\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-10-08\",\"color\":\"#9be9a8\",\"contributionCount\":1}]},{\"firstDay\":\"2022-10-09\",\"contributionDays\":[{\"date\":\"2022-10-09\",\"color\":\"#40c463\",\"contributionCount\":2},{\"date\":\"2022-10-10\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-10-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-10-12\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-10-13\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-10-14\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-10-15\",\"color\":\"#40c463\",\"contributionCount\":2}]},{\"firstDay\":\"2022-10-16\",\"contributionDays\":[{\"date\":\"2022-10-16\",\"color\":\"#30a14e\",\"contributionCount\":4},{\"date\":\"2022-10-17\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-10-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-10-19\",\"color\":\"#40c463\",\"contributionCount\":2},{\"date\":\"2022-10-20\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-10-21\",\"color\":\"#30a14e\",\"contributionCount\":4},{\"date\":\"2022-10-22\",\"color\":\"#30a14e\",\"contributionCount\":5}]},{\"firstDay\":\"2022-10-23\",\"contributionDays\":[{\"date\":\"2022-10-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-10-24\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-10-25\",\"color\":\"#216e39\",\"contributionCount\":8},{\"date\":\"2022-10-26\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-10-27\",\"color\":\"#30a14e\",\"contributionCount\":4},{\"date\":\"2022-10-28\",\"color\":\"#216e39\",\"contributionCount\":7},{\"date\":\"2022-10-29\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-10-30\",\"contributionDays\":[{\"date\":\"2022-10-30\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-10-31\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-03\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-04\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-11-05\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-11-06\",\"contributionDays\":[{\"date\":\"2022-11-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-07\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-08\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-11-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-10\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-12\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-11-13\",\"contributionDays\":[{\"date\":\"2022-11-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-16\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-11-17\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-19\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-11-20\",\"contributionDays\":[{\"date\":\"2022-11-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-21\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-26\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-11-27\",\"contributionDays\":[{\"date\":\"2022-11-27\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-11-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-11-30\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-12-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-03\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-12-04\",\"contributionDays\":[{\"date\":\"2022-12-04\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-05\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-07\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-10\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-12-11\",\"contributionDays\":[{\"date\":\"2022-12-11\",\"color\":\"#30a14e\",\"contributionCount\":4},{\"date\":\"2022-12-12\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-13\",\"color\":\"#40c463\",\"contributionCount\":2},{\"date\":\"2022-12-14\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-15\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-12-16\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-12-17\",\"color\":\"#9be9a8\",\"contributionCount\":1}]},{\"firstDay\":\"2022-12-18\",\"contributionDays\":[{\"date\":\"2022-12-18\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-12-19\",\"color\":\"#40c463\",\"contributionCount\":3},{\"date\":\"2022-12-20\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-21\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-12-22\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-12-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-24\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2022-12-25\",\"contributionDays\":[{\"date\":\"2022-12-25\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-26\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-28\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2022-12-30\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2022-12-31\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2023-01-01\",\"contributionDays\":[{\"date\":\"2023-01-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-03\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2023-01-04\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-05\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2023-01-06\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-07\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2023-01-08\",\"contributionDays\":[{\"date\":\"2023-01-08\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2023-01-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-10\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2023-01-11\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-12\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-13\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-14\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2023-01-15\",\"contributionDays\":[{\"date\":\"2023-01-15\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-16\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-17\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-18\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-19\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-20\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2023-01-21\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2023-01-22\",\"contributionDays\":[{\"date\":\"2023-01-22\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-23\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-24\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-25\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2023-01-26\",\"color\":\"#30a14e\",\"contributionCount\":4},{\"date\":\"2023-01-27\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-28\",\"color\":\"#ebedf0\",\"contributionCount\":0}]},{\"firstDay\":\"2023-01-29\",\"contributionDays\":[{\"date\":\"2023-01-29\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-30\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-01-31\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2023-02-01\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-02-02\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-02-03\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-02-04\",\"color\":\"#9be9a8\",\"contributionCount\":1}]},{\"firstDay\":\"2023-02-05\",\"contributionDays\":[{\"date\":\"2023-02-05\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-02-06\",\"color\":\"#40c463\",\"contributionCount\":2},{\"date\":\"2023-02-07\",\"color\":\"#9be9a8\",\"contributionCount\":1},{\"date\":\"2023-02-08\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-02-09\",\"color\":\"#ebedf0\",\"contributionCount\":0},{\"date\":\"2023-02-10\",\"color\":\"#ebedf0\",\"contributionCount\":0}]}],\"colors\":[\"#9be9a8\",\"#40c463\",\"#30a14e\",\"#216e39\"]}");
        JsonArray weeks = json.getArray("weeks");
        JsonArray colors = json.getArray("colors");

        List<FeedData> feedDataList = new ArrayList<>();
        final String[] months = new DateFormatSymbols(Locale.US).getShortMonths();
        final String[] weekdays = new DateFormatSymbols(Locale.US).getShortWeekdays();
        final String defaultColor = "#161b22";
        final int marginLeft = 30;
        final int marginTop = 14;
        final int size = 10;
        final int gap = size + 3;
        int delay = 450;
        String lastMonth = null;
        SvgGroup staticTile = new SvgGroup();
        cardBody.appendChild(staticTile);
        for (int i = 0; i < weeks.length; i++) {
            int columnX = marginLeft + i * gap;
            JsonObject weekData = weeks.getJson(i);
            JsonArray week = weekData.getArray("contributionDays");

            // Tile
            int tileDelay = delay;
            for (int j = 0; j < week.length; j++) {
                JsonObject day = week.getJson(j);
                String color = day.getString("color");
                SvgRect tile = new SvgRect(columnX, j * gap + marginTop, size, size).setBorderRadius(2);

                if (!colors.content(color)) {
                    tile.setColor(defaultColor);
                    tile.setClass("fade-in").setStyle(new AttrStyle().addStyle("animation-delay", tileDelay + "ms"));
                    staticTile.appendChild(tile);
                } else {
                    tile.setColor(color);
                    tile.setClass("feed" + feedDataList.size());
                    feedDataList.add(new FeedData(tileDelay, tile, new Vector2int(i, j), new Vector2int(marginLeft, j * gap + marginTop)));
                    cardBody.appendChild(tile);
                }
                tileDelay += 100;
            }

            // Text
            if (i == 0) {
                for (int j = 1; j < 6; j += 2) {
                    cardBody.appendChild(new SvgText(weekdays[j + 1]).setClass("fade-in text-outline weak-day").setY(j * gap + size + marginTop)
                            .setStyle(new AttrStyle().addStyle("animation-delay", (delay + j * 50) + "ms")));
                }
            }
            String day = weekData.getString("firstDay") + "T00:00:00Z";
            int month = OffsetDateTime.parse(day).get(ChronoField.MONTH_OF_YEAR) - 1;
            if (!months[month].equals(lastMonth)) {
                lastMonth = months[month];
                cardBody.appendChild(new SvgText(lastMonth).setClass("fade-in text-outline month-year").setX(columnX).setY(-5)
                        .setStyle(new AttrStyle().addStyle("animation-delay", (delay + 50) + "ms")));
            }

            delay += 20;
        }

        // Calculate path
        System.out.println(feedDataList.size());
        List<FeedData> feedLeft = new ArrayList<>(feedDataList);
        List<Vector2int> path = new ArrayList<>();
        List<FeedData> feeds = new ArrayList<>();
        Vector2int nowLoc = new Vector2int(0, 0);
        while (feedLeft.size() > 0) {
            FeedData feed = findNearestFeed(nowLoc, feedLeft);
            assert feed != null;
            Vector2int newLoc = feed.loc;
            feedLeft.remove(feed);
            int direction = newLoc.x > nowLoc.x ? 1 : -1;
            for (int i = nowLoc.x; direction == 1 ? (i < newLoc.x) : (i > newLoc.x); i += direction)
                path.add(new Vector2int(i, nowLoc.y));
            direction = newLoc.y > nowLoc.y ? 1 : -1;
            for (int i = nowLoc.y; direction == 1 ? (i < newLoc.y) : (i > newLoc.y); i += direction)
                path.add(new Vector2int(newLoc.x, i));
            nowLoc = newLoc;
            feed.feedDelay = path.size();
            feeds.add(feed);
        }
        feedLeft = null;
        // To End
        Vector2int endLoc = new Vector2int(0, 0);
        int direction = endLoc.x > nowLoc.x ? 1 : -1;
        for (int i = nowLoc.x; direction == 1 ? (i < endLoc.x) : (i > endLoc.x); i += direction)
            path.add(new Vector2int(i, nowLoc.y));
        direction = endLoc.y > nowLoc.y ? 1 : -1;
        for (int i = nowLoc.y; direction == 1 ? (i < endLoc.y) : (i > endLoc.y); i += direction)
            path.add(new Vector2int(endLoc.x, i));
        path.add(endLoc);

        System.out.println(path.size());
        System.out.println(feeds.size());

        // Calculate animation
        StringBuilder movement = new StringBuilder();
        int totalStep = path.size();
        int stepDelay = 50;
        int stepDuration = 50;
        int showDelay = 2000;
        int showDuration = 500;
        int duration = totalStep * stepDelay + showDelay;
        int startX = 30, startY = 14;

        int x = startX, y = startY;
        movement.append("from{x:" + x + ";y:" + y + "opacity:0}");
        movement.append((((float) (showDelay - showDuration) / duration) * 100) + "%{opacity:0}");
        movement.append((((float) showDelay / duration) * 100) + "%{opacity:1}");
        List<StringBuilder> feedStyleBuilders = new ArrayList<>();
        for (int i = 0; i < feeds.size(); i++) {
            FeedData feed = feeds.get(i);

            feed.tile.setStyle(new AttrStyle()
                    .addStyle("animation", "800ms ease-in-out " + feed.tileShowDelay + "ms forwards fadeInAnimation," +
                            (duration - showDelay - feed.feedDelay * stepDelay) + "ms ease-in-out " +
                            (feed.feedDelay * stepDelay + showDelay + stepDelay * (i + 1)) + "ms forwards feed" + i + "Animation")
                    .addStyle("opacity", "0"));
            feedStyleBuilders.add(new StringBuilder());
        }
        // Write movement
        float startMoveDelay = (float) showDelay / duration;
        for (int i = 0; i < totalStep; i++) {
            float total = (float) stepDelay / duration * i + startMoveDelay;
            Vector2int nextPos = path.get(i);
            x = startX + nextPos.x * gap;
            y = startY + nextPos.y * gap;
            String nextPosStr = "%{x:" + x + ";y:" + y + "}";
            movement.append(((total + (float) stepDuration / duration) * 100) + nextPosStr);
            movement.append(((total + (float) stepDelay / duration) * 100) + nextPosStr);

            // Feeds
            for (int j = 0; j < feeds.size(); j++) {
                FeedData feed = feeds.get(j);
                int feeDelay = feed.feedDelay * stepDelay;
                if (feeDelay > stepDelay * i) continue;
                StringBuilder builder = feedStyleBuilders.get(j);
                int feedDuration = duration - showDelay - feeDelay;
                float feedTotal = (float) stepDelay / feedDuration * (i - feed.feedDelay);
                String nextFeedPosStr = "%{x:" + x + ";y:" + y + "}";
                builder.append(((feedTotal + (float) stepDuration / feedDuration) * 100) + nextFeedPosStr);
                builder.append(((feedTotal + (float) stepDelay / feedDuration) * 100) + nextFeedPosStr);
            }
        }
        // Set final position
        movement.append("to{x:" + x + ";y:" + y + ";opacity:1}");
        for (int i = 0; i < feeds.size(); i++) {
            StringBuilder builder = feedStyleBuilders.get(i);
            builder.append("to{x:" + x + ";y:" + y + "}");
            style.addStyle("@keyframes feed" + i + "Animation", builder.toString());
        }


        style.addStyle(".move-object", "animation: " + duration + "ms forwards moveAnimation;opacity:0")
                .addStyle("@keyframes moveAnimation", movement.toString());
        cardBody.appendChild(new SvgRect(startX, startY, size, size, "#fff").setBorderRadius(2)
                .setClass("move-object"));

        Svg svg = new Svg(800, 300).setBackgroundColor("#0E1117");
        svg.appendChild(style);
        svg.appendChild(cardTitle);
        svg.appendChild(cardBody);
        try {
            FileWriter fileWriter = new FileWriter("out3.svg");
            fileWriter.write(svg.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        List<Map.Entry<String, Long>> languages = new ArrayList<>(new LinkedHashMap<String, Long>() {{
//            put("Java", 699888L);
//            put("JavaScript", 479496L);
//            put("CSS", 53702L);
//            put("C", 13239L);
//            put("HTML", 13081L);
//            put("AutoHotkey", 1598L);
//            put("Batchfile", 393L);
//        }}.entrySet());
//
//        String result;
//
//        result = SvgBuilder.languagesToListFormSvg(languages, 10);
//        try {
//            FileWriter fileWriter = new FileWriter("out1.svg");
//            fileWriter.write(result);
//            fileWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
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

    private FeedData findNearestFeed(Vector2int nowPos, List<FeedData> feeds) {
        if (feeds.size() == 0)
            return null;
        double distance = -1;
        FeedData nearestFeed = null;
        for (FeedData feed : feeds) {
            double thisDistance = nowPos.distance(feed.loc);
            if (distance == -1 || thisDistance < distance) {
                distance = thisDistance;
                nearestFeed = feed;
            }
        }
        return nearestFeed;
    }

    public static void main(String[] args) {
        new Test();
    }
}
