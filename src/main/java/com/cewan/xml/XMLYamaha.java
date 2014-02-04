package com.cewan.xml;

import com.cewan.model.Status;
import com.jamesmurty.utils.XMLBuilder;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;

import static com.cewan.xml.XmlParser.parseXml;
import static java.lang.String.format;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;

public class XMLYamaha {

    public static final String URL = "/YamahaRemoteControl/ctrl";
    public static final String PUT = "PUT";
    private static final String GET = "GET";

    private static XMLBuilder yamaha(String method) {
        try {
            return XMLBuilder.create("YAMAHA_AV").a("cmd", method);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static XMLBuilder mainZone(String method) {
        return yamaha(method).e("Main_Zone");
    }

    public static String basicStatus() {
        return asString(mainZone(GET).e("Basic_Status").t("GetParam"));
    }

    public static String radioStatus() {
        return asString(yamaha(GET).e("NET_RADIO").e("List_Info").t("GetParam"));
    }

    public static Status parseBasicStatus(String xml) {
        Status status = new Status();
        status.setPowerOn(parsePower(parseXml(xml, "//Power/text()")));
        status.setVolume(createInteger(parseXml(xml, "//Lvl/Val/text()")));
        status.setInput(parseXml(xml, "//Input_Sel/text()"));
        return status;
    }

    private static boolean parsePower(String powerString) {
        return powerString.equalsIgnoreCase("On");
    }

    public static Status parseRadioStatus(String xml, Status status) {
        status.setRadioMenuLevel(createInteger(parseXml(xml, "//Menu_Layer/text()")));
        return status;
    }

    public static String powerOnXml() {
        return asString(power().t("On"));
    }

    public static String volumeXml(Integer vol) {
        XMLBuilder lvl = mainZone(PUT).e("Volume").e("Lvl");
        lvl.e("Val").t(vol.toString());
        lvl.e("Exp").t("1");
        lvl.e("Unit").t("dB");
        return asString(lvl);
    }

    public static String muteXml() {
        return asString(mainZone(PUT).e("Volume").e("Mute").t("On"));
    }

    private static XMLBuilder power() {
        return mainZone(PUT).e("Power_Control").e("Power");
    }

    public static String input(String source) {
        return asString(mainZone(PUT).e("Input").e("Input_Sel").t(source));
    }

    public static String standby() {
        return asString(power().t("Standby"));
    }

    private static XMLBuilder radioListControl() {
        return yamaha(PUT).e("NET_RADIO").e("List_Control");
    }

    public static String radioListItem(int station) {
        return asString(radioListControl().e("Direct_Sel").t(format("Line_%s", station)));
    }

    private static String asString(XMLBuilder xmlBuilder) {
        try {
            return xmlBuilder.asString();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
