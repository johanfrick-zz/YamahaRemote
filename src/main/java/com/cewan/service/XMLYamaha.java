package com.cewan.service;

import com.jamesmurty.utils.XMLBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import static java.lang.String.format;

public class XMLYamaha {

    public static final String URL = "/YamahaRemoteControl/ctrl";
    public static final String STANDBY = "<Power_Control><Power>Standby</Power></Power_Control>";
    public static final String YAMAHA_WRAPPING = "<YAMAHA_AV cmd=\"PUT\">%s</YAMAHA_AV>";
    public static final String INPUT_SELECT = "<Input><Input_Sel>%s</Input_Sel></Input>";
    private static final String POWER_ON = "<Power_Control><Power>On</Power></Power_Control>";
    private static final String NET_RADIO_INPUT = "NET RADIO";
    private static final String NET_RADIO_ITEM = "<NET_RADIO><List_Control><Direct_Sel>Line_%s</Direct_Sel></List_Control></NET_RADIO>";
    private static final String NET_RADIO_RETURN = "<NET_RADIO><List_Control><Cursor>Return</Cursor></List_Control></NET_RADIO>";
    private static final String MAIN_ZONE = "<Main_Zone>%s</Main_Zone>";



    private XMLBuilder yamaha() {
        XMLBuilder yamaha_av = null;
        try {
            yamaha_av = XMLBuilder.create("YAMAHA_AV");
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return yamaha_av.a("cmd", "PUT");
    }

    private XMLBuilder mainZone() {
        return yamaha().e("Main_Zone");
    }

    public String powerOn() {
        return asString(power().t("On"));
    }

    private XMLBuilder power() {
        return mainZone().e("Power_Control").e("Power");
    }

    public String input(String source) {
        return asString(mainZone().e("Input").e("Input_Sel").t(source));
    }

    public String standby() {
        return asString(power().t("Standby"));
    }

    public String radioReturn() {
        return asString(radioListControl().e("Cursor").t("Return"));
    }

    private XMLBuilder radioListControl() {
        return yamaha().e("NET_RADIO").e("List_Control");
    }

    public String radioStation(int station) {
        return asString(radioListControl().e("Direct_Sel").t(format("Line_%s", station)));
    }




    private String asString(XMLBuilder xmlBuilder) {
        try {
            return xmlBuilder.asString();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }


}
