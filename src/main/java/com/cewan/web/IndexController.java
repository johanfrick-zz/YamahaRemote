package com.cewan.web;

import com.cewan.model.VolumeOperation;
import com.cewan.remote.Remote;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.cewan.xml.XMLYamaha.standby;
import static java.lang.String.format;

@SessionAttributes("status")
@RestController
public class IndexController {

    @Autowired
    private Remote remote;

    @RequestMapping("/")
    public String index() {
        return index("");
    }

    @RequestMapping("/input")
    public String selectInput(@RequestParam String source) {
        String msg;
        if (StringUtils.isNotBlank(source)) {
            remote.asyncSelectInput(source);
            msg = format("Yay, it worked! You have selected input %s", source);
        } else {
            msg = "You should supply source. Example '/input?source=HDMI2'";
        }
        return index(msg);
    }

    private String index(String msg) {
        return format(Index.getHtml(), msg);
    }

    @RequestMapping("/standby")
    public String selectStandby() {
        remote.post(standby());
        return index("Nighty night, sweet Yamaha...");
    }

    @RequestMapping("/volume")
    public String selectStandby(@RequestParam VolumeOperation op, @RequestParam(required = false) Integer dB) {
        remote.asyncVolume(op, dB);
        return index(String.format("Volume operation %s performed!", op));
    }

    @RequestMapping("/radio")
    public String radio(@RequestParam(required = false) int station) {
        if (station > 0) {
            remote.asyncSelectRadio(station);
            String msg = format("Station %s selected! Enjoy :-)", station);
            return index(msg);
        }
        return index("Send in /radio?station=1 (or 2... or 3... and so on!)");
    }
}
