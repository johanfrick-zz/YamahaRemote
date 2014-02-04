package com.cewan.remote;

import com.cewan.model.*;
import com.cewan.xml.XMLYamaha;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import static com.cewan.xml.XMLYamaha.*;

@Service
public class Remote {

    @Autowired
    private HttpHelper httpHelper;

    @Value("${host:http://192.168.0.86}")
    private String host;

    private void input(String source) {
        Status status = powerOn();
        while (!status.getInput().equals(source)) {
            post(XMLYamaha.input(source));
            sleep(1);
            status = getStatus();
        }
    }

    private Status powerOn() {
        Status status = getStatus();
        while (status == null || !status.isPowerOn()) {
            post(powerOnXml());
            sleep(1);
            status = getStatus();
        }
        return status;
    }

    private void volume(VolumeOperation op, Integer dB) {
        Status status = getStatus();
        if (status != null) {
            switch (op) {
                case DOWN:
                    int newVol = status.getVolume() - 50;
                    post(volumeXml(newVol));
                    break;
                case UP:
                    newVol = status.getVolume() + 50;
                    post(volumeXml(newVol));
                    break;
                case MUTE:
                    post(muteXml());
                    break;
                case SET:
                    post(volumeXml(-dB * 10));
                    break;
            }
        }
    }

    private void station(int station) {
        input("NET RADIO");
        Status status = getStatus();
        while (status.getRadioMenuLevel() == null) {
            sleep(1);
            status = getStatus();
        }
        if (status.getRadioMenuLevel() == 1) {
            post(radioListItem(1));
            sleep(2);
        }
        post(radioListItem(station));
    }


    public String post(String operation) {
        return httpHelper.doPost(host + URL, operation);
    }


    public void asyncSelectInput(final String source) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                input(source);
                //once more since sometimes av decides to go back to some other source
                sleep(2);
                input(source);
            }
        }).start();
    }

    public void asyncSelectRadio(final int station) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                station(station);
            }
        }).start();
    }


    private Status getStatus() {
        Status status = parseBasicStatus(post(basicStatus()));
        parseRadioStatus(post(radioStatus()), status);
        return status;
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void asyncVolume(final VolumeOperation op, final Integer dB) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                volume(op, dB);
            }
        }).start();

    }
}
