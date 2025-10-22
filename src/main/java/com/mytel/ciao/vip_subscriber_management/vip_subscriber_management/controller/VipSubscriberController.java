package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.controller;


import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.common.response.Basic;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.VipSubscriberRequest;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.VipSubscriberService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/vip-subscriber")
public class VipSubscriberController {


    private final VipSubscriberService vipSubscriberService;

    public VipSubscriberController(VipSubscriberService vipSubscriberService) {
        this.vipSubscriberService = vipSubscriberService;
    }


    @PostMapping("/create")
    public ResponseEntity<Basic> createVipSubscriber(@Valid  @RequestBody VipSubscriberRequest vipSubscriberRequest) {
        log.info("Received request to create Vip Subscriber");
        return vipSubscriberService.createVipSubscriber(vipSubscriberRequest);
    }


    @PostMapping("/update/{vipSubscriberId}")
    public ResponseEntity<Basic> updateVipSubscriber(@PathVariable String vipSubscriberId,
                                                     @Valid  @RequestBody VipSubscriberRequest vipSubscriberRequest) {
        log.info("Received request to update Vip Subscriber");
        return vipSubscriberService.updateVipSubscriber(vipSubscriberId,vipSubscriberRequest);
    }


    @GetMapping("/get-by-subscriberId/{vipSubscriberId}")
    public ResponseEntity<Basic> getVipSubscriber(@PathVariable String vipSubscriberId) {
        log.info("Received request to fetch Vip Subscriber id: {}", vipSubscriberId);
        return vipSubscriberService.getVipSubscriber(vipSubscriberId);
    }

    @GetMapping("/get-all-vip-subscribers")
    public ResponseEntity<Basic> getAllVipSubscribers() {
        log.info("Received request to fetch all active Vip Subscribers");
        return vipSubscriberService.getAllVipSubscribers();
    }


    @PostMapping("/delete/{vipSubscriberId}")
    public ResponseEntity<Basic> deleteVipSubscriber(@PathVariable String vipSubscriberId) {
        log.info("Received request to soft delete Vip Subscriber id: {}", vipSubscriberId);
        return vipSubscriberService.deleteVipSubscriber(vipSubscriberId);
    }


    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importData(@RequestParam("file") MultipartFile file) {
        log.info("Received imported request to create vip subscriber through File");
        return vipSubscriberService.importData(file);
    }

    @GetMapping("/download-template")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {

        log.info("Received request to export template format through Excel");
        return vipSubscriberService.downloadTemplate();

    }

    @GetMapping("/export")
    public ResponseEntity<?> exportVipSubscribers() {

        log.info("Received request to export vip subscriber through Excel");
        return vipSubscriberService.exportData();

    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(System.getProperty("java.io.tmpdir")).resolve(fileName);
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            log.error("Error while downloading file: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
