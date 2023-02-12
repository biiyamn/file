package com.frontbackend.springboot.controller;

import com.frontbackend.springboot.model.FileData;
import com.frontbackend.springboot.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/files")
public class FileController {

    private final FileService fileService;
    private Map<String, String> args;
    @Autowired
    Environment environment;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    public void init() {
        args = new HashMap<>();
        args.put("a", "&");
    }

    @GetMapping
    public List<FileData> list() {
        return fileService.list();
    }

    @GetMapping("{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
       /* Resource file = fileService.download(filename);
        Path path = file.getFile()
                        .toPath();
        Path path = ;*/
        if (!"file.zip".equals(filename)) {
            this.args.put("b","b");
            throw new RuntimeException("hekloo");
        }

        Resource file = new UrlResource(Paths.get("file.zip")
                .toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.getFile()
                        .toPath()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}