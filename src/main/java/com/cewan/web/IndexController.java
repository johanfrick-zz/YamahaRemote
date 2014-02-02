/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cewan.web;

import com.cewan.service.HttpHelper;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.ParserConfigurationException;

import static java.lang.String.format;

@RestController
public class IndexController {



    @Autowired
    private HttpHelper httpHelper;

    @Value("${host:http://192.168.0.86}")
    private String host;

    @RequestMapping("/")
    public String index() {
        return "Either input or powerToggle";
    }

    @RequestMapping("/input")
    public String input(@RequestParam String source) {
        if (StringUtils.isNotBlank(source)) {
            postToMainZone(POWER_ON);
            postInput(source);
            sleep(4);
            postInput(source);
            return format("Yay, it worked! You have selected input %s", source);
        }
        return "You should supply source. Example '/input?source=HDMI2'";
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void postInput(String source) {
        postToMainZone(format(INPUT_SELECT, source));
    }

    @RequestMapping("/standby")
    public String standby() {
        postToMainZone(STANDBY);
        return "Nighty night, sweet Yamaha...";
    }

    @RequestMapping("/radio")
    public String radio(@RequestParam(required = false) int station) {
        if (station > 0) {
            postToMainZone(POWER_ON);
            sleep(4);
            postInput(NET_RADIO_INPUT);
            post(NET_RADIO_RETURN);
            sleep(2);
            post(format(NET_RADIO_ITEM, 1));
            sleep(2);
            post(format(NET_RADIO_ITEM, station));
            return format("Station %s selected! Enjoy :-)", station);
        }
        return "Send in /radio?station=1 (or 2... or 3... and so on!)";
    }

    private void postToMainZone(String data) {
        post(format(MAIN_ZONE, data));
    }

    private void post(String operation) {
        String wrapped = format(YAMAHA_WRAPPING, operation);
        System.out.println(URL);
        System.out.println(wrapped);
        String response = httpHelper.doPost(host + URL, wrapped);
        System.out.println(response);
    }
}
