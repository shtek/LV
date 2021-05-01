package com.roman.lv.mullvad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.roman.lv.EmailService;


@Component
@Qualifier("mockedEmailService")
public class MockedEmailService implements EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MockedEmailService.class);

    @Override
    public void sendSimpleMessage(String url, String[] emailAddress) {
        LOGGER.info("Pretending to send an email to {} for item {}", emailAddress, url);
    }
}
