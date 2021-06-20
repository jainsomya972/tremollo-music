package com.tremollo.api.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tremollo.api.Entity.FileTemp;
import com.tremollo.api.Repository.FileTempRepository;
import com.tremollo.api.Service.AdminService;
import com.tremollo.api.Utils.S3Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AdminController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/vm/clean")
    public Object cleanVM() throws URISyntaxException {
        List<String> deletedFiles = adminService.cleanVM();
        Map<String,List<String>> result = new HashMap<>();
        result.put("deletedFiles",deletedFiles);
        return result;
    }

    @GetMapping("/admin/s3/clean")
    public Object cleanS3(){
        List<String> deletedFiles = adminService.cleanS3();
        Map<String,List<String>> result = new HashMap<>();
        result.put("deletedFiles",deletedFiles);
        return result;
    }

}
