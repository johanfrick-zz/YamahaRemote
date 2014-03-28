package com.cewan.schedule;

import com.cewan.model.Status;
import com.cewan.remote.Remote;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InputChecker {
    private final static Logger LOGGER = LoggerFactory.getLogger(InputChecker.class);

    @Autowired
    private Remote remote;

    private String previousInput;

    @Value("${check.input.enabled:false}")
    private boolean checkInputEnabled;

    @Scheduled(fixedRateString = "${check.input.interval:10000}")
    public void checkInput() {
        if (checkInputEnabled) {
            Status status = remote.getStatus();
            String input = status.getInput();
            if (input.equals("AV1")) {
                if (StringUtils.isNotBlank(previousInput)) {
                    LOGGER.info("AV1 forbidden. Changing to input {}", previousInput);
                    remote.asyncSelectInput(previousInput);
                }
            } else {
                previousInput = input;
            }
        }
    }
}
