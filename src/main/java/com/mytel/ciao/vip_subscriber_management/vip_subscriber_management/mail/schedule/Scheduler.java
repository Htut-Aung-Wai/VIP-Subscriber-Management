package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.mail.schedule;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final EmailService service;

    //@Scheduled(fixedRate = 60000000)
    public void send() {
        service.sendEmail("---@gmail.com",
                "Reminder about Renewal processes",
                "Hello Mr John. This is an email to remind you to kindly check the renewal processes. ");

    }
}
