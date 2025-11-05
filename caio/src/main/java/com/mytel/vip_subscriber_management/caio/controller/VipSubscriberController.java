package com.mytel.vip_subscriber_management.caio.controller;

import com.mytel.vip_subscriber_management.common.common.response.Basic;
import com.mytel.vip_subscriber_management.database.dto.SubscriberSearchDto;
import com.mytel.vip_subscriber_management.database.dto.VipSubscriberRequest;
import com.mytel.vip_subscriber_management.database.entity.VipSubscriber;
import com.mytel.vip_subscriber_management.service.service.VipSubscriberService;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/caio/vip-subscriber")
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

    @PostMapping("/search-vip-subscriber")
    public ResponseEntity<Basic> searchVipSubscriber(@RequestBody SubscriberSearchDto subscriberSearchDto,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        log.info("Received request to search vip subscribers");
        return vipSubscriberService.searchVipSubscribers(subscriberSearchDto,page,size);
    }


    @PostMapping("/delete/{vipSubscriberId}")
    public ResponseEntity<Basic> deleteVipSubscriber(@PathVariable String vipSubscriberId) {
        log.info("Received request to soft delete Vip Subscriber id: {}", vipSubscriberId);
        return vipSubscriberService.deleteVipSubscriber(vipSubscriberId);
    }


    @PostMapping(value = "/import-validate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> validateImport(@RequestParam("file") MultipartFile file) {
        log.info("Received import valid request to create vip subscriber through File");
        return vipSubscriberService.validateImportSubscriber(file);
    }

    @PostMapping("/import-confirm")
    public ResponseEntity<?> confirmImport(@RequestBody String token) {
        log.info("Received confirmed request to create vip subscriber through File");
        return vipSubscriberService.saveValidatedList(token);
    }

    @GetMapping("/download-template")
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {

        log.info("Received request to export template format through Excel");
        return vipSubscriberService.downloadTemplate();

    }

    @GetMapping("/export")
    public ResponseEntity<?> exportVipSubscribers(@RequestBody SubscriberSearchDto subscriberSearchDto,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {

        log.info("Received request to export vip subscriber through Excel");
        return vipSubscriberService.exportData(subscriberSearchDto,page,size);

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
