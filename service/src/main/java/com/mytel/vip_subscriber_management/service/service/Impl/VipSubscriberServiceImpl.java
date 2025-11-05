package com.mytel.vip_subscriber_management.service.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mytel.vip_subscriber_management.common.common.response.Basic;
import com.mytel.vip_subscriber_management.common.common.response.ResponseFactory;
import com.mytel.vip_subscriber_management.common.common.utils.Util;
import com.mytel.vip_subscriber_management.common.constant.ErrorCode;
import com.mytel.vip_subscriber_management.common.constant.VipSubscriberLogActionType;
import com.mytel.vip_subscriber_management.database.dto.SubscriberSearchDto;
import com.mytel.vip_subscriber_management.database.dto.VipSubscriberExcelImportCreateErrorDto;
import com.mytel.vip_subscriber_management.database.dto.VipSubscriberRequest;
import com.mytel.vip_subscriber_management.database.entity.Unit;
import com.mytel.vip_subscriber_management.database.entity.VipSubscriber;
import com.mytel.vip_subscriber_management.database.entity.VipSubscriberImportTemp;
import com.mytel.vip_subscriber_management.database.repository.VipSubscriberImportTempRepo;
import com.mytel.vip_subscriber_management.database.repository.VipSubscriberRepo;
import com.mytel.vip_subscriber_management.service.service.VipSubscriberLogService;
import com.mytel.vip_subscriber_management.service.service.VipSubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VipSubscriberServiceImpl implements VipSubscriberService {

    private final VipSubscriberRepo vipSubscriberRepo;
    private final ResponseFactory responseFactory;
    private final VipSubscriberLogService vipSubscriberLogService;
    private final Util util;
    private final VipSubscriberImportTempRepo vipSubscriberImportTempRepo;

    public VipSubscriberServiceImpl(VipSubscriberRepo vipSubscriberRepo, ResponseFactory responseFactory, VipSubscriberLogService vipSubscriberLogService, Util util, VipSubscriberImportTempRepo vipSubscriberImportTempRepo) {
        this.vipSubscriberRepo = vipSubscriberRepo;
        this.responseFactory = responseFactory;
        this.vipSubscriberLogService = vipSubscriberLogService;
        this.util = util;
        this.vipSubscriberImportTempRepo = vipSubscriberImportTempRepo;
    }

    @Override
    public ResponseEntity<Basic> createVipSubscriber(VipSubscriberRequest vipSubscriberRequest)
    {
        try{

            log.info("Creating Vip Subscriber ................");

            if(!util.isMytelNumber(vipSubscriberRequest.getSubscriberNo()))
            {
                return responseFactory.buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ErrorCode.INTERNAL_ERROR,
                        ErrorCode.FAIL,
                        "[Failed] The phone number is not Mytel number."
                );
            }

            VipSubscriber vipSubscriber=vipSubscriberRequestToVipSubscriber(vipSubscriberRequest);
            VipSubscriber vipSubscriberSaved=vipSubscriberRepo.save(vipSubscriber);

            log.info("[Succeed] Creating Vip Subscriber");
            vipSubscriberLogService.createLog(vipSubscriberSaved);
            return responseFactory.buildSuccess(
                    HttpStatus.OK,
                    vipSubscriberSaved,
                    ErrorCode.SUCCESS,
                    "[Succeed] Creating Vip Subscriber"
            );

        }

        catch (DataIntegrityViolationException e) {
            log.error("[Failed] Duplicate Subscriber number.Subscriber already existed : {}", vipSubscriberRequest.getSubscriberNo());
            vipSubscriberLogService.errorLog(null,vipSubscriberRequest.getSubscriberNo(),
                    VipSubscriberLogActionType.CREATE.name(),
                    "Duplicate Subscriber number.Subscriber already existed");
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "[Failed] Duplicate Subscriber number.Subscriber already existed"
            );
        }

        catch (Exception e) {
            log.error("[Failed] Error occurred while creating Vip Subscriber: {}", e.getMessage(), e);
            vipSubscriberLogService.errorLog(null,vipSubscriberRequest.getSubscriberNo(),
                    VipSubscriberLogActionType.CREATE.name(),
                    e.getMessage());
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "[Failed] Error occurred while creating Vip Subscriber"
            );
        }


    }


    @Override
    public ResponseEntity<Basic> updateVipSubscriber(String vipSubscriberId, VipSubscriberRequest vipSubscriberRequest) {
        try {
            log.info("Updating Vip Subscriber with id: {}", vipSubscriberId);

            Optional<VipSubscriber> vipSubscriberOptional = vipSubscriberRepo.findByIdAndNotDeleted(vipSubscriberId);

            if (!vipSubscriberOptional.isPresent()) {
                log.warn("Vip Subscriber with id {} not found or deleted", vipSubscriberId);
                return responseFactory.buildError(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND,
                        ErrorCode.FAIL,
                        "Vip Subscriber with id "+vipSubscriberId+" not found or deleted"
                );
            }

            VipSubscriber vipSubscriber = vipSubscriberOptional.get();

            vipSubscriber.setBranchName(vipSubscriberRequest.getBranchName());
            vipSubscriber.setVipPackageId(vipSubscriberRequest.getVipPackageId());
            vipSubscriber.setProposalDocumentNo(vipSubscriberRequest.getProposalDocumentNo());

            VipSubscriber vipSubscriberUpdated = vipSubscriberRepo.save(vipSubscriber);

            log.info("[Succeed] Updated Vip Subscriber No: {}", vipSubscriber.getSubscriberNo());
            vipSubscriberLogService.updateLog(vipSubscriberUpdated);

            return responseFactory.buildSuccess(
                    HttpStatus.OK,
                    vipSubscriberUpdated,
                    ErrorCode.SUCCESS,
                    "[Succeed] Updated Vip Subscriber"
            );

        } catch (Exception e) {
            log.error("[Failed] Error occurred while updating Vip Subscriber: {}", e.getMessage(), e);
            vipSubscriberLogService.errorLog(vipSubscriberId,vipSubscriberRequest.getSubscriberNo(),
                    VipSubscriberLogActionType.UPDATE.name(),
                    e.getMessage());
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "[Failed] Error occurred while updating Vip Subscriber"
            );
        }
    }




    @Override
    public ResponseEntity<Basic> getVipSubscriber(String vipSubscriberId) {
        try {
            log.info("Fetching Vip Subscriber with id: {}", vipSubscriberId);

            Optional<VipSubscriber> vipSubscriberOptional = vipSubscriberRepo.findByIdAndNotDeleted(vipSubscriberId);

            if (!vipSubscriberOptional.isPresent()) {
                log.warn("No active Vip Subscribers with this id found");
                return responseFactory.buildError(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND,
                        ErrorCode.FAIL,
                        "Active Vip Subscribers with id " +vipSubscriberId+ " not found"
                );
            }

            VipSubscriber vipSubscriber = vipSubscriberOptional.get();

            log.info("[Succeed] Fetched Vip Subscriber id: {}", vipSubscriberId);
            return responseFactory.buildSuccess(
                    HttpStatus.OK,
                    vipSubscriber,
                    ErrorCode.SUCCESS,
                    "[Succeed] Fetched Vip Subscriber id"
            );

        } catch (Exception e) {
            log.error("[Failed] Error occurred while fetching Vip Subscriber: {}", e.getMessage(), e);
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "[Failed] Error occurred while fetching Vip Subscriber"
            );
        }
    }




    @Override
    public ResponseEntity<Basic> searchVipSubscribers(SubscriberSearchDto subscriberSearchDto, int page, int size) {
        try {

            log.info("Searching active Vip Subscribers with keywords : {} and page: {}, size: {}",subscriberSearchDto, page, size);

            Pageable pageable = PageRequest.of(page, size);

            LocalDateTime startDateTime = (subscriberSearchDto.getFromDate() != null) ? subscriberSearchDto.getFromDate().atStartOfDay() : null;
            LocalDateTime endDateTime = (subscriberSearchDto.getToDate() != null) ? subscriberSearchDto.getToDate().atTime(23, 59, 59) : null;

            Page<VipSubscriber> vipSubscribers = vipSubscriberRepo.findActiveSubscriberWithFilterCombined(subscriberSearchDto.getKeyword()
                    ,subscriberSearchDto.getSubscriberNumber()
                    ,subscriberSearchDto.getUnit()
                    ,subscriberSearchDto.getDocumentNumber()
                    ,startDateTime
                    ,endDateTime
                    ,pageable);

            if (vipSubscribers.isEmpty()) {
                log.warn("No active Vip Subscribers found");
                return responseFactory.buildError(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND,
                        ErrorCode.FAIL,
                        "No active Vip Subscribers found"
                );
            }

            log.info("[Succeed] Fetched {} active Vip Subscribers", vipSubscribers.getTotalElements());
            return responseFactory.buildSuccess(
                    HttpStatus.OK,
                    vipSubscribers,
                    ErrorCode.SUCCESS,
                    "[Succeed] Fetched {} active Vip Subscribers"
            );

        } catch (Exception e) {
            log.error("[Failed] Error occurred while searching Vip Subscribers: {}", e.getMessage(), e);
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "[Failed] Error occurred while searching Vip Subscribers"
            );
        }
    }



    @Override
    public ResponseEntity<Basic> deleteVipSubscriber(String vipSubscriberId) {
        try {
            log.info("Deleting (soft) Vip Subscriber with id: {}", vipSubscriberId);

            Optional<VipSubscriber> vipSubscriberOptional = vipSubscriberRepo.findByIdAndNotDeleted(vipSubscriberId);

            if (!vipSubscriberOptional.isPresent()) {
                log.warn("Vip Subscriber with id {} not found or already deleted", vipSubscriberId);
                return responseFactory.buildError(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND,
                        ErrorCode.FAIL,
                        "Vip Subscriber with id "+vipSubscriberId+" not found or already deleted"
                );
            }

            VipSubscriber vipSubscriber = vipSubscriberOptional.get();
            vipSubscriber.setDeleted(true);
            vipSubscriberRepo.save(vipSubscriber);

            log.info("[Succeed] Soft deleted Vip Subscriber id: {}", vipSubscriberId);
            vipSubscriberLogService.deleteLog(vipSubscriberId);

            return responseFactory.buildSuccess(
                    HttpStatus.OK,
                    ErrorCode.SUCCESS,
                    ErrorCode.SUCCESS,
                    "Successfully Deleted !"
            );

        } catch (Exception e) {
            log.error("[Failed] Error occurred while soft deleting Vip Subscriber: {}", e.getMessage(), e);
            vipSubscriberLogService.errorLog(vipSubscriberId,null,
                    VipSubscriberLogActionType.DELETE.name(),
                    e.getMessage());
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "[Failed] Error occurred while soft deleting Vip Subscriber"
            );
        }
    }



    private VipSubscriber vipSubscriberRequestToVipSubscriber(VipSubscriberRequest vipSubscriberRequest)
    {
        VipSubscriber vipSubscriber=new VipSubscriber();
        vipSubscriber.setSubscriberNo(util.normalizeIsdn(vipSubscriberRequest.getSubscriberNo()));
        vipSubscriber.setVipPackageId(vipSubscriberRequest.getVipPackageId());
        vipSubscriber.setBranchName(vipSubscriberRequest.getBranchName());
        vipSubscriber.setProposalDocumentNo(vipSubscriberRequest.getProposalDocumentNo());
        vipSubscriber.setRegistrationDate(LocalDateTime.now());
        vipSubscriber.setDeleted(false);
        log.info("Saved Vip Subscriber No : {}",vipSubscriber.getSubscriberNo());


        return vipSubscriber;
    }




    @Override
    public ResponseEntity<?> validateImportSubscriber(MultipartFile file) {
        // Increase the maximum allowable byte array size for Apache POI
        org.apache.poi.util.IOUtils.setByteArrayMaxOverride(200_000_000);

        //List<VipSubscriber> vipSubscribers = new ArrayList<>();
        List<VipSubscriber> validList = new ArrayList<>();
        List<VipSubscriberExcelImportCreateErrorDto> errorList = new ArrayList<>();
        Set<String> seenGlobal = new HashSet<>();


        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Check if the sheet is empty or invalid
            if (sheet == null || sheet.getPhysicalNumberOfRows() <= 1) {
                return responseFactory.buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ErrorCode.INTERNAL_ERROR,
                        ErrorCode.FAIL,
                        "The uploaded file is empty or invalid."
                );
            }

            Iterator<Row> rows = sheet.iterator();

            // Validate header row
            if (!rows.hasNext()) {
                return responseFactory.buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ErrorCode.INTERNAL_ERROR,
                        ErrorCode.FAIL,
                        "The uploaded file is empty or invalid."
                );
            }

            Row headerRow = rows.next();
            if (!isValidHeaderRow(headerRow)) {
                return responseFactory.buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ErrorCode.INTERNAL_ERROR,
                        ErrorCode.FAIL,
                        "The Excel file does not match the expected format."
                );
            }


            // Fetch all existing subscriber numbers from DB
            Set<String> existingNumbers = new HashSet<>(vipSubscriberRepo.findAllSubscriberNumbers());

            // Process data rows
            while (rows.hasNext()) {
                Row row = rows.next();
                if (row.getPhysicalNumberOfCells() != 4) {
                    errorList.add(new VipSubscriberExcelImportCreateErrorDto(
                            null,
                            "Row format is invalid. Each row must have exactly 4 columns."
                    ));
                    continue;
                }


                String vipPackageId = getCellValueAsString(row.getCell(0));
                String subscriberNo = getCellValueAsString(row.getCell(1));
                String branchName = getCellValueAsString(row.getCell(2));
                String proposalDocumentNo = getCellValueAsString(row.getCell(3));


                try {


                    if (checkValidSubscriberNumber(subscriberNo, errorList, seenGlobal,existingNumbers)) {
                        VipSubscriberRequest vipSubscriberRequest=new VipSubscriberRequest(vipPackageId,subscriberNo,branchName,proposalDocumentNo);
                        VipSubscriber vipSubscriberToSave=vipSubscriberRequestToVipSubscriber(vipSubscriberRequest);

                        //vipSubscribers.add(vipSubscriberSaved);
                        validList.add(vipSubscriberToSave);
                    }


                    /*// Process in batches (for example, every 500 records)
                    if (vipSubscribers.size() >= 500) {
                        saveBatchVipSubscriber(vipSubscribers,500);  // Save batch
                        vipSubscribers.clear();  // Clear the list after saving
                    }*/
                } catch (Exception e) {
                    errorList.add(new VipSubscriberExcelImportCreateErrorDto(subscriberNo,
                            "Error processing row: " + e.getMessage()));
                }
            }

            /*// Save any remaining records
            if (!vipSubscribers.isEmpty()) {
                saveBatchVipSubscriber(vipSubscribers, 500);
            }


            if(!errorList.isEmpty()) {
                return exportVipSubscriberErrorList(errorList);
            }*/

            String token = UUID.randomUUID().toString();

            // Save validList to temporary table
            for (VipSubscriber v : validList) {
                vipSubscriberImportTempRepo.save(new VipSubscriberImportTemp(token, v.getVipPackageId(),
                        v.getSubscriberNo(), v.getBranchName(), v.getProposalDocumentNo(),v.getRegistrationDate(),v.getExpiryDate(),v.getUnitId()));
            }

            Map<String, Object> result = new HashMap<>();
            result.put("validationToken", token);
            result.put("validCount", validList.size());
            result.put("errorList", errorList.size());

            return responseFactory.buildSuccess(HttpStatus.OK,result, ErrorCode.SUCCESS, "Validated");


            /*return responseFactory.buildSuccess(
                    HttpStatus.OK,
                    "Completed",
                    ErrorCode.SUCCESS,
                    "File imported successfully."
            );*/

        } catch (Exception e) {
            log.error("[Failed] Error processing import file", e);
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "Error while processing the file: " + e.getMessage()
            );
        }
    }


    @Override
    public ResponseEntity<?> saveValidatedList(String token) {
        try {
            List<VipSubscriberImportTemp> tempList = vipSubscriberImportTempRepo.findByToken(token);

            if (tempList.isEmpty()) {
                return responseFactory.buildError(HttpStatus.BAD_REQUEST, ErrorCode.FAIL, ErrorCode.INTERNAL_ERROR,
                        "Invalid or expired token.");
            }

            List<VipSubscriber> finalList = tempList.stream()
                    .map(t -> {
                        VipSubscriber v = new VipSubscriber();
                        v.setVipPackageId(t.getVipPackageId());
                        v.setSubscriberNo(t.getSubscriberNo());
                        v.setBranchName(t.getBranchName());
                        v.setProposalDocumentNo(t.getProposalDocumentNo());
                        v.setRegistrationDate(t.getRegistrationDate());
                        v.setExpiryDate(t.getExpiryDate());
                        v.setDeleted(false);
                        Unit unit = new Unit();
                        unit.setId(v.getUnitId());
                        v.setUnit(unit);
                        return v;
                    })
                    .collect(Collectors.toList());

            saveBatchVipSubscriber(finalList, 500);
            vipSubscriberImportTempRepo.deleteByToken(token); // cleanup

            return responseFactory.buildSuccess(HttpStatus.OK, "Completed", ErrorCode.SUCCESS, "Data saved successfully.");

        } catch (Exception e) {
            log.error("[Failed] Error saving validated data", e);
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "Error while saving data: " + e.getMessage()
            );
        }
    }



    private boolean checkValidSubscriberNumber(
            String subscriberNumber,
            List<VipSubscriberExcelImportCreateErrorDto> errorList,
            Set<String> seenGlobal,
            Set<String> existingNumbers
    ) {
        if (subscriberNumber == null || subscriberNumber.isEmpty()) {
            errorList.add(new VipSubscriberExcelImportCreateErrorDto(subscriberNumber, "Phone number is missing"));
        } else if (!util.isMytelNumber(util.toIsdn(subscriberNumber))) {
            errorList.add(new VipSubscriberExcelImportCreateErrorDto(subscriberNumber, "Phone number is not Mytel number"));
        } else if (!seenGlobal.add(subscriberNumber)) { // duplicates in Excel
            errorList.add(new VipSubscriberExcelImportCreateErrorDto(subscriberNumber, "Duplicate phone number in Excel"));
        } else if (existingNumbers.contains(subscriberNumber)) { // duplicates in DB
            errorList.add(new VipSubscriberExcelImportCreateErrorDto(subscriberNumber, "Phone number already exists in database"));
        } else {
            return true; // valid
        }
        return false; // invalid
    }

    private void saveBatchVipSubscriber(List<VipSubscriber> saveList, int batchSize) {
        try {
            int total = saveList.size();
            int current = 0, next;
            while (current < total) {
                next = Math.min(current + batchSize, total);
                long step1 = System.currentTimeMillis();
                List<VipSubscriber> batch = vipSubscriberRepo.saveAll(saveList.subList(current, next));
                long step2 = System.currentTimeMillis();
                log.info("[{}] ms to save batch in current = {}, total = {}", (step2 - step1), current, total);

                // Create logs for each saved entity
                for (VipSubscriber vipSubscriber : batch) {
                    try {
                        vipSubscriberLogService.createLog(vipSubscriber); // call your existing createLog method
                    } catch (JsonProcessingException e) {
                        log.error("Failed to create log for subscriberNo={} : {}", vipSubscriber.getSubscriberNo(), e.getMessage(), e);
                    }
                }

                current = next;
            }
        } catch (Exception ex) {
            log.error("[saveBatch] error ", ex);
            vipSubscriberLogService.errorLog(null,null,VipSubscriberLogActionType.CREATE.name(),ex.getMessage());
            throw new RuntimeException();
        }
    }

    private boolean isValidHeaderRow(Row headerRow) {
        return headerRow.getPhysicalNumberOfCells() == 4 &&
                headerRow.getCell(0).getStringCellValue().equalsIgnoreCase("VIP Package ID") &&
                headerRow.getCell(1).getStringCellValue().equalsIgnoreCase("Subscriber No") &&
                headerRow.getCell(2).getStringCellValue().equalsIgnoreCase("Branch Name") &&
                headerRow.getCell(3).getStringCellValue().equalsIgnoreCase("Proposal Document No");
    }


    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Always store as integer, truncate decimals
                long intValue = (long) cell.getNumericCellValue();
                return String.valueOf(intValue);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return cell.toString();
        }
    }





    @Override
    public ResponseEntity<byte[]> downloadTemplate() throws IOException {
        // Create a new workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("VIP Subscriber Template");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("VIP Package ID");
        headerRow.createCell(1).setCellValue("Subscriber No");
        headerRow.createCell(2).setCellValue("Branch Name");
        headerRow.createCell(3).setCellValue("Proposal Document No");

        // Write the output to a ByteArrayOutputStream
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);

            // Set headers for file download
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=VIP_Subscriber_Template.xlsx");
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Return the Excel file as a byte array
            return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<?> exportData(SubscriberSearchDto subscriberSearchDto,int page,int size) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            log.info("Fetching all active Vip Subscribers to export...");

            //List<VipSubscriber> vipSubscribers = vipSubscriberRepo.findAllActive();

            Pageable pageable = PageRequest.of(page, size);

            LocalDateTime startDateTime = (subscriberSearchDto.getFromDate() != null) ? subscriberSearchDto.getFromDate().atStartOfDay() : null;
            LocalDateTime endDateTime = (subscriberSearchDto.getToDate() != null) ? subscriberSearchDto.getToDate().atTime(23, 59, 59) : null;

            Page<VipSubscriber> vipSubscribers = vipSubscriberRepo.findActiveSubscriberWithFilterCombined(subscriberSearchDto.getKeyword()
                    ,subscriberSearchDto.getSubscriberNumber()
                    ,subscriberSearchDto.getUnit()
                    ,subscriberSearchDto.getDocumentNumber()
                    ,startDateTime
                    ,endDateTime
                    ,pageable);

            /*if (vipSubscribers.isEmpty()) {
                log.warn("No active Vip Subscribers found to export");
                return responseFactory.buildError(
                        HttpStatus.NOT_FOUND,
                        ErrorCode.NOT_FOUND,
                        ErrorCode.FAIL,
                        "No active Vip Subscribers found"
                );
            }*/



            log.info("[Succeed] Fetched {} active Vip Subscribers and exporting as excel file", vipSubscribers.getTotalElements());
            // Create a new workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("All VIP Subscriber");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("VIP Package ID");
            headerRow.createCell(1).setCellValue("Subscriber No");
            headerRow.createCell(2).setCellValue("Branch Name");
            headerRow.createCell(3).setCellValue("Proposal Document No");
            headerRow.createCell(4).setCellValue("Registration Date");
            headerRow.createCell(5).setCellValue("Expiry Date");


            // Row
            int rowNum = 1; // start after header
            for (VipSubscriber subscriber : vipSubscribers) {
                Row row = sheet.createRow(rowNum++);

                // Make sure your VipSubscriber entity has these getter methods
                row.createCell(0).setCellValue(
                        subscriber.getVipPackageId() != null ? subscriber.getVipPackageId() : ""
                );
                row.createCell(1).setCellValue(
                        subscriber.getSubscriberNo() != null ? subscriber.getSubscriberNo() : ""
                );
                row.createCell(2).setCellValue(
                        subscriber.getBranchName() != null ? subscriber.getBranchName() : ""
                );
                row.createCell(3).setCellValue(
                        subscriber.getProposalDocumentNo() != null ? subscriber.getProposalDocumentNo() : ""
                );
                row.createCell(4).setCellValue(
                        subscriber.getRegistrationDate() != null
                                ? subscriber.getRegistrationDate().format(formatter)
                                : ""
                );
                row.createCell(5).setCellValue(
                        subscriber.getExpiryDate() != null
                                ? subscriber.getExpiryDate().format(formatter)
                                : ""
                );
            }

            //Auto resize
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }


            // Write the output to a ByteArrayOutputStream
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                workbook.write(byteArrayOutputStream);

                // Set headers for file download
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=All_VIP_Subscriber.xlsx");
                headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

                // Return the Excel file as a byte array
                return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);

            }

        } catch (Exception e) {
            log.error("[Failed] Error occurred while fetching or exporting all Vip Subscribers: {}", e.getMessage(), e);
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "[Failed] Error occurred while fetching or exporting all Vip Subscribers"
            );
        }




    }


    private ResponseEntity<?> exportVipSubscriberErrorList(List<VipSubscriberExcelImportCreateErrorDto> errorList) {

        try {
            // Create a new workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Import Errors");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Subscriber No");
            headerRow.createCell(1).setCellValue("Error Description");


            // Row
            int rowNum = 1; // start after header
            for (VipSubscriberExcelImportCreateErrorDto eachError : errorList) {
                Row row = sheet.createRow(rowNum++);

                // Make sure your VipSubscriber entity has these getter methods
                row.createCell(0).setCellValue(
                        eachError.getPhoneNumber() != null ? eachError.getPhoneNumber() : ""
                );
                row.createCell(1).setCellValue(
                        eachError.getError() != null ? eachError.getError() : ""
                );
            }

            //Auto resize
            for (int i = 0; i < 2; i++) {
                sheet.autoSizeColumn(i);
            }

            // Save file temporarily
            String fileName = "VIP_Subscriber_Import_Error_" + System.currentTimeMillis() + ".xlsx";
            Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), fileName);


            // Write the output to a ByteArrayOutputStream
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                workbook.write(byteArrayOutputStream);

                /*// Set headers for file download
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=VIP_Subscriber_Import_Error.xlsx");
                headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");*/

                // Build download URL (assuming your app runs on localhost:8080)
                String downloadUrl = "/vip-subscriber/download/" + fileName;

                Files.write(filePath, byteArrayOutputStream.toByteArray());

               /* // Return the link in JSON
                Map<String, String> response = new HashMap<>();
                response.put("message", "Error list generated successfully.");
                response.put("downloadLink", downloadUrl);

                return ResponseEntity.ok(response);*/
                return responseFactory.buildSuccess(
                        HttpStatus.OK,
                        "Completed",
                        ErrorCode.SUCCESS,
                        "Upload completed. Some records were not added due to duplicates or validation errors. "
                                + "The Result Report is below for download: "
                                + "<a href='" + downloadUrl + "' download>Click here to download</a>"
                );

                // Return the Excel file as a byte array
                //return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        } catch (Exception e) {
            log.error("[Failed] Error occurred while exporting error list: {}", e.getMessage(), e);
            return responseFactory.buildError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_ERROR,
                    ErrorCode.FAIL,
                    "[Failed] Error occurred while exporting error list"
            );
        }




    }


}
