package com.wavjaby;

import com.wavjaby.json.JsonObject;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SvgBuilder {

    private static final JsonObject languageColor = new JsonObject(SvgBuilder.class.getClassLoader().getResourceAsStream("languageColors.json"));

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
}
