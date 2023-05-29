package com.wavjaby;

import com.wavjaby.json.JsonArray;
import com.wavjaby.json.JsonObject;
import com.wavjaby.svgbuilder.*;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class SvgBuilder {

    private static final JsonObject languageColor = new JsonObject(SvgBuilder.class.getClassLoader().getResourceAsStream("languageColors.json"));

    public static String languagesToListFormSvg(List<Map.Entry<String, Long>> languages, final int count) {
        final int imageWidth = 300;
        final int imageHeight = 80 + Math.min(count, languages.size()) * 40;
        final int delayTime = 450;
        final int progressHeight = 10;
        final int progressWidth = 200;
        final String title = "Most Used Languages";
        final String progressBgColor = "#ccc";
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");

        List<Map.Entry<String, Long>> languagesList = languages.stream().limit(count).collect(Collectors.toList());
        long total = languagesList.stream().mapToLong(Map.Entry::getValue).reduce(0L, Long::sum);
        String svgStart = "<svg width=\"%width%\" height=\"%height%\" viewBox=\"0 0 %width% %height%\" xmlns=\"http://www.w3.org/2000/svg\" role=\"img\">";
        String style = "<style>" +
                ".card-body,.card-title{font-family:'Segoe UI',Ubuntu,Sans-Serif,monospace}" +
                ".card-title{font-size:18px;font-weight:700;fill:#61dafb;animation:.8s ease-in-out forwards fadeInAnimation}" +
                ".lang-name,.lang-use{font-size:14px;fill:#ffffff}" +
                ".fade-in{opacity:0;animation:.3s ease-in-out forwards fadeInAnimation}" +
                ".lang-progress{animation:.6s ease-in-out forwards growWidthAnimation}" +
                "@keyframes growWidthAnimation{from{width:0}to{width:100%}}" +
                "@keyframes fadeInAnimation{from{opacity:0}to{opacity:1}}" +
                "</style>";
//                "@keyframes slideInAnimation{from{width:0}to{width:calc(100% - 100px)}}" +
//                "@keyframes scaleInAnimation{from{transform:translate(-5px,5px) scale(0)}to{transform:translate(-5px,5px) scale(1)}}" +

        StringBuilder out = new StringBuilder();
        out.append(svgStart.replace("%width%", String.valueOf(imageWidth))
                .replace("%height%", String.valueOf(imageHeight)));
        out.append(style);
        // Add title
        out.append("<g class=\"card-title\" transform=\"translate(25, 35)\">" +
                "<text>" + title + "</text>" +
                "</g>");
        // Add card
        out.append("<g transform=\"translate(25, 55)\" class=\"card-body\">");
        int y = 0;
        int progressRound = progressHeight / 2 + 1;
        int i = 0;
        int showDelayTime = delayTime;
        for (Map.Entry<String, Long> lang : languagesList) {
            if (++i > count) break;
            String progressColor = languageColor.getString(lang.getKey());
            if (progressColor == null) progressColor = "#00FF00";

            double percent = (double) (lang.getValue() * 100) / total;
            double percentW = (double) (lang.getValue() * 97) / total + 3;
            int progressDelay = showDelayTime + 300;
            out.append("<g class=\"fade-in\" transform=\"translate(0, " + y + ")\" style=\"animation-delay:" + showDelayTime + "ms\">");
            out.append("<text x=\"2\" y=\"15\" class=\"lang-name\">" + lang.getKey() + "</text>");
            out.append("<text x=\"210\" y=\"33\" class=\"lang-use\">" + decimalFormat.format(percent) + "%</text>");
            // Progress bar
            out.append("<svg width=\"" + progressWidth + "\" height=\"" + progressHeight + "\" x=\"0\" y=\"22\">");
            // Progress bar background
            out.append("<rect x=\"0.5\" width=\"" + (progressWidth - 1) + "\" height=\"100%\" rx=\"" + progressRound + "\" fill=\"" + progressBgColor + "\"/>");
            // Progress bar foreground cut out
            out.append("<defs><clipPath id=\"background-clip\"><rect width=\"100%\" height=\"100%\" rx=\"" + progressRound + "\"/></clipPath></defs>");
            // Progress bar foreground
            out.append("<svg width=\"" + (percentW + (double) progressHeight / progressWidth * 100) + "%\">");
            out.append("<rect x=\"" + (-progressHeight) + "\" height=\"100%\" rx=\"" + progressRound + "\" fill=\"" + progressColor + "\" style=\"animation-delay:" + progressDelay + "ms;\" clip-path=\"url(#background-clip)\" class=\"lang-progress\"/>");

            out.append("</svg>");
            out.append("</svg>");
            out.append("</g>");
            y += 40;
            showDelayTime += 150;
        }
        out.append("</g>");
        out.append("</svg>");

        return out.toString();
    }

    public static String languagesToPieChartSvg(List<Map.Entry<String, Long>> languages, final int count) {
        final int imageWidth = 300;
        int imageHeight = 350;
        final String title = "Most Used Languages";
        final int pieChartDiameter = 250;
        final String pieBgColor = "#eee";
        final int showChartDelay = 500;
        final int showChartTime = 1000;
        final int langDotRadius = 5;
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");

        languages = languages.stream().limit(count).collect(Collectors.toList());
        final long total = languages.stream().mapToLong(Map.Entry::getValue).reduce(0L, Long::sum);
        String svgStart = "<svg width=\"%width%\" height=\"%height%\" viewBox=\"0 0 %width% %height%\" xmlns=\"http://www.w3.org/2000/svg\" role=\"img\">";
        String styles = "<style>" +
                ".card-title, .card-body {" +
                "    font-family: 'Segoe UI', Ubuntu, Sans-Serif, monospace;" +
                "}" +
                ".card-title {" +
                "    font-size: 18px;" +
                "    font-weight: bold;" +
                "    fill: #61dafb;" +
                "    animation: 800ms ease-in-out forwards fadeInAnimation;" +
                "}" +
                ".lang-name, .lang-use {" +
                "    font-size: 14px;" +
                "    fill: #ffffff;" +
                "    paint-order: stroke;" +
                "    stroke: #000;" +
                "    stroke-width: 2px;" +
                "    stroke-linecap: round;" +
                "    stroke-linejoin: round;" +
                "}" +
                ".fade-in {" +
                "    opacity: 0;" +
                "    animation: 300ms ease-in-out forwards fadeInAnimation;" +
                "}" +
                ".lang-pie {" +
                "    animation: forwards growPie" +
                "}" +
                "@keyframes growPie {" +
                "    to {" +
                "        stroke-dashoffset: 0" +
                "    }" +
                "}" +
                "@keyframes fadeInAnimation {" +
                "    from {" +
                "        opacity: 0" +
                "    }" +
                "    to {" +
                "        opacity: 1" +
                "    }" +
                "}" +
                "</style>";

        StringBuilder out = new StringBuilder();
        StringBuilder body = new StringBuilder();
        body.append(styles);
        // Add title
        body.append("<g class=\"card-title\" transform=\"translate(25, 35)\">" +
                "<text>" + title + "</text>" +
                "</g>");

        // Add card
        float lineDiameter = pieChartDiameter * 0.5f;
        float lineRadius = lineDiameter * 0.5f;
        double perimeter = lineDiameter * Math.PI;
        body.append("<g transform=\"translate(25, 55)\" class=\"card-body\">");
        // Pie bg
        body.append("<circle class=\"fade-in\" r=\"" + (lineDiameter - 0.5f) + "\" cx=\"" + lineDiameter + "\" cy=\"" + lineDiameter + "\" fill=\"" + pieBgColor + "\"  style=\"animation-delay:" + showChartDelay + "ms\"/>");

        StringBuilder textBuilder = new StringBuilder();
        int pieShowDelay = showChartDelay;
        double percentTotal = 0;
        final int textPanelStartY = pieChartDiameter + 14;
        int textPanelOffsetX = 0, textPanelOffsetY = textPanelStartY;
        int textPanelSplit = -1;
        int textPanelLineCount = 0;
        int i = 0;
        for (Map.Entry<String, Long> lang : languages) {
            String progressColor = languageColor.getString(lang.getKey());
            if (progressColor == null) progressColor = "#00FF00";

            double percent = (double) lang.getValue() / total;
            double percentPixel = (perimeter * percent) + 0.5f;
            double endPixel = (perimeter * percentTotal) + percentPixel;
            int showDuration = (int) (speedFunction(percentTotal) * showChartTime * percent);

            // Limit to not over 360deg
            if (endPixel > perimeter)
                percentPixel -= endPixel - perimeter;

            body.append("<circle r=\"" + lineRadius + "\" cx=\"" + -lineDiameter + "\" cy=\"" + lineDiameter + "\"" +
                    " style=\"animation-delay:" + pieShowDelay + "ms;animation-duration:" + showDuration + "ms" +
                    (i == 0 ? ";animation-timing-function:ease-in\"" : ";animation-timing-function:ease-out\"") +
                    " stroke=\"" + progressColor + "\"" +
                    " stroke-width=\"" + lineDiameter + "\"" +
                    " stroke-dasharray=\"" + percentPixel + " " + perimeter + "\"" +
                    " stroke-dashoffset=\"" + percentPixel + "\"" +
                    " transform=\"rotate(" + (360 - percentTotal * 360 - 90) + "," + lineDiameter + "," + lineDiameter + ") scale(-1,1)\"" +
                    " class=\"lang-pie\"" +
                    " fill=\"none\"" +
                    "/>");

            int textDelay = pieShowDelay + showDuration / 2;
            double textAngle = (percentTotal + percent * 0.5 - 0.25) * 2 * Math.PI;
            String lanName = lang.getKey();
            String lanUse = decimalFormat.format(percent * 100);
            float textX, textY;
            if (percent < 0.1) {
                // Move to next co column
                if (textPanelSplit != -1) {
                    if (i == textPanelSplit) {
                        textPanelOffsetX = (int) lineDiameter;
                        textPanelOffsetY = textPanelStartY;
                    } else
                        textPanelOffsetY += 14;
                }
                textX = textPanelOffsetX + langDotRadius * 2 + 5;
                textY = textPanelOffsetY;
                textBuilder.append("<circle r=\"" + langDotRadius + "\" cx=\"" + (textPanelOffsetX + langDotRadius) + "\" cy=\"" + (textPanelOffsetY - langDotRadius) + "\"" +
                        " class=\"fade-in\" style=\"animation-delay:" + textDelay + "ms\"" +
                        " fill=\"" + progressColor + "\"" +
                        "/>");
                textBuilder.append("<text x=\"" + textX + "\" y=\"" + textY + "\" class=\"lang-name fade-in\" style=\"animation-delay:" + textDelay + "ms\">" + lanName + " " + lanUse + "%</text>");
                if (textPanelSplit == -1) {
                    textPanelLineCount = (int) Math.ceil((languages.size() - i) / 2f);
                    textPanelSplit = i + textPanelLineCount;
                }
            } else {
                int textWidth = Math.max(lanName.length(), lanUse.length()) * 4;
                textX = (float) (lineRadius * Math.cos(-textAngle) + lineDiameter) - textWidth;
                textY = (float) (lineRadius * Math.sin(-textAngle) + lineDiameter);
                textBuilder.append("<text x=\"" + textX + "\" y=\"" + textY + "\" class=\"lang-name fade-in\" style=\"animation-delay:" + textDelay + "ms\">" + lanName + "</text>");
                textBuilder.append("<text x=\"" + textX + "\" y=\"" + (textY + 14) + "\" class=\"lang-use fade-in\" style=\"animation-delay:" + textDelay + "ms\">" + lanUse + "%</text>");
            }

            pieShowDelay += showDuration;
            percentTotal += percent;
            i++;
        }
        body.append(textBuilder);
        body.append("</g>");

        imageHeight = 80 + pieChartDiameter + textPanelLineCount * 14;
        out.append(svgStart.replace("%width%", String.valueOf(imageWidth))
                .replace("%height%", String.valueOf(imageHeight)));
        out.append(body);
        out.append("</svg>");
        return out.toString();
    }

    private static double speedFunction(double x) {
        return (Math.cos(Math.PI * x * 2) * 0.6 + 1.4) / 2;
    }

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

    private static FeedData findNearestFeed(Vector2int nowPos, List<FeedData> feeds) {
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

    public static String contributionCalendarToSnakeSvg(JsonObject contributionCalendar) {
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
                "animation: forwards growPie") ;
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
        JsonArray weeks = contributionCalendar.getArray("weeks");
        JsonArray colors = contributionCalendar.getArray("colors");

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
        SvgGroup movingTile = new SvgGroup();
        cardBody.appendChild(staticTile);
        cardBody.appendChild(movingTile);
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
                    tile.setBackgroundColor(defaultColor);
                    tile.setClass("fade-in").setStyle(new AttrStyle().addStyle("animation-delay", tileDelay + "ms"));
                    staticTile.appendChild(tile);
                } else {
                    tile.setBackgroundColor(color);
                    tile.setClass("feed" + feedDataList.size());
                    feedDataList.add(new FeedData(tileDelay, tile, new Vector2int(i, j), new Vector2int(columnX, j * gap + marginTop)));
                    movingTile.appendChild(tile);
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
        // To End
        Vector2int endLoc = new Vector2int(0, 0);
        int direction = endLoc.x > nowLoc.x ? 1 : -1;
        for (int i = nowLoc.x; direction == 1 ? (i < endLoc.x) : (i > endLoc.x); i += direction)
            path.add(new Vector2int(i, nowLoc.y));
        direction = endLoc.y > nowLoc.y ? 1 : -1;
        for (int i = nowLoc.y; direction == 1 ? (i < endLoc.y) : (i > endLoc.y); i += direction)
            path.add(new Vector2int(endLoc.x, i));
        path.add(endLoc);


        // Calculate animation
        StringBuilder movement = new StringBuilder();
        int totalStep = path.size() + feeds.size();
        int stepDelay = 10;
        int stepDuration = 10;
        int showDelay = 2000;
        int showDuration = 500;
        int duration = totalStep * stepDelay + showDelay;
        int startX = 30, startY = 14;
        // Set start position
        int x = startX, y = startY;
        movement.append("from{x:").append(x).append(";y:").append(y).append(";opacity:0}");
        movement.append(((float) (showDelay - showDuration) / duration) * 100).append("%{opacity:0}");
        movement.append(((float) showDelay / duration) * 100).append("%{opacity:1}");
        List<StringBuilder> feedStyleBuilders = new ArrayList<>();
        for (int i = 0; i < feeds.size(); i++) {
            FeedData feed = feeds.get(i);

            feed.tile.setStyle(new AttrStyle()
                            .addStyle("animation",
                                    duration + "ms " + (stepDelay * i) + "ms ease-in-out infinite forwards feed" + i + "Animation," +
                                            duration + "ms " + feed.tileShowDelay + "ms ease-in-out infinite forwards feedFadeInAnimation")
                            .addStyle("opacity","0")
//                    .addStyle("animation", duration + "ms ease-in-out infinite forwards feed" + i + "Animation")
            );
            StringBuilder builder = new StringBuilder();
            feedStyleBuilders.add(builder);

//            builder.append("from{opacity:0}");
//            builder.append(((float) feed.tileShowDelay / duration) * 100).append("%{opacity:0}");
//            builder.append(((float) (feed.tileShowDelay + 800) / duration) * 100).append("%{opacity:1}");
            builder.append(((float) (feed.feedDelay * stepDelay) / duration) * 100).append("%{x:").append(feed.realLocalLoc.x).append(";y:").append(feed.realLocalLoc.y).append("}");
        }
        // Write movement
        float startMoveDelay = (float) showDelay / duration;
        int lastX, lastY, nextX = 0, nextY = 0;
        for (int i = 0; i < path.size(); i++) {
            float total = (float) stepDelay / duration * i + startMoveDelay;

            String nextPosStr = null;
            if (i == 0 || i + 1 == path.size()) {
                Vector2int nowPos = path.get(i);
                x = startX + nowPos.x * gap;
                y = startY + nowPos.y * gap;
                nextPosStr = "%{x:" + x + ";y:" + y + "}";
            }
            if (i + 1 < path.size()) {
                Vector2int nextPos = path.get(i + 1);
                lastX = x;
                lastY = y;
                x = nextX;
                y = nextY;
                nextX = startX + nextPos.x * gap;
                nextY = startY + nextPos.y * gap;

                if (i > 0) {
                    if (lastX == x && x == nextX)
                        nextPosStr = "%{y:" + y + "}";
                    else if (lastY == y && y == nextY)
                        nextPosStr = "%{x:" + x + "}";
                    else
                        nextPosStr = "%{x:" + x + ";y:" + y + "}";
                }
            }

            movement.append((total + (float) stepDuration / duration) * 100).append(nextPosStr);
            movement.append((total + (float) stepDelay / duration) * 100).append(nextPosStr);

            // Feeds
            for (int j = 0; j < feeds.size(); j++) {
                FeedData feed = feeds.get(j);
                if (feed.feedDelay > i) continue;
                StringBuilder builder = feedStyleBuilders.get(j);
//                float feedTotal = (float) stepDelay / duration * (i + j + 1) + startMoveDelay;
                builder.append((total + (float) stepDuration / duration) * 100).append(nextPosStr);
                builder.append((total + (float) stepDelay / duration) * 100).append(nextPosStr);
            }
        }
        // Set final position
        movement.append("to{x:").append(x).append(";y:").append(y).append("}");
        for (int i = 0; i < feeds.size(); i++) {
            StringBuilder builder = feedStyleBuilders.get(i);
            builder.append("to{x:").append(x).append(";y:").append(y).append("}");
            style.addStyle("@keyframes feed" + i + "Animation", builder.toString());
        }
        style.addStyle("@keyframes feedFadeInAnimation", "from{x:0,y:0,opacity:0}" + (((float) 800 / duration) * 100) + "%{opacity:1}99.99%{opacity:1}to{opacity:0}");


        style.addStyle(".move-object", "animation: " + duration + "ms ease-in-out infinite forwards moveAnimation;opacity:0");
        style.addStyle("@keyframes moveAnimation", movement.toString());
        cardBody.appendChild(new SvgRect(startX, startY, size, size, "#fff").setBorderRadius(2)
                .setClass("move-object"));

        Svg svg = new Svg(800, 300).setBackgroundColor("#0E1117");
        svg.appendChild(style);
        svg.appendChild(cardTitle);
        svg.appendChild(cardBody);
        return svg.toString();
    }
}
