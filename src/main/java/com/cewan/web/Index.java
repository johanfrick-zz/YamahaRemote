package com.cewan.web;

public class Index {

    /**
     * WHY DOES NOT SPRING-BOOT RENDER JSP??
     * Will keep the ugly solution below for now.
     * Can not use javascript in client since it should support automation apps, such as Tasker.
     */
    public static String getHtml() {
        return "<html>" +
                "<head>" +
                "    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'/>" +
                "    <title>Yamaha Remote</title>" +
                "    <style type='text/css'>" +
                "        body {" +
                "            font-size: 2em;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "%s" +
                "<div>" +
                "    <h3>Links</h3>" +
                "    <ul>" +
                "        <li><a href='input?source=HDMI1'>HDMI1</a></li>" +
                "        <li><a href='input?source=HDMI2'>HDMI2</a></li>" +
                "        <li><a href='standby'>Standby</a></li>" +
                "        <li><a href='volume?op=MUTE'>mute</a></li>" +
                "        <li><a href='volume?op=UP'>Volume up</a></li>" +
                "        <li><a href='volume?op=DOWN'>Volume down</a></li>" +
                "        <li><a href='volume?op=SET&dB=30'>30dB</a></li>" +
                "        <li><a href='volume?op=SET&dB=40'>40dB</a></li>" +
                "        <li><a href='volume?op=SET&dB=50'>50dB</a></li>" +
                "        <li><a href='radio?station=1'>radio 1</a></li>" +
                "        <li><a href='radio?station=2'>radio 2</a></li>" +
                "        <li><a href='radio?station=3'>radio 3</a></li>" +
                "        <li><a href='radio?station=4'>radio 4</a></li>" +
                "        <li><a href='radio?station=5'>radio 5</a></li>" +
                "        <li><a href='radio?station=6'>radio 6</a></li>" +
                "        <li><a href='radio?station=7'>radio 7</a></li>" +
                "        <li><a href='radio?station=8'>radio 8</a></li>" +
                "        <li><a href='radio?station=9'>radio 9</a></li>" +
                "    </ul>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
