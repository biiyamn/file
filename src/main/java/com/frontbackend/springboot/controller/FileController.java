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
import org.xml.sax.InputSource;

import javax.annotation.PostConstruct;
import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
    private String name = null;

    @GetMapping("/transform")
    public String transform() throws TransformerException, FileNotFoundException {
        StringWriter writer = new   StringWriter();
        StreamResult streamResult = new StreamResult(writer);
        TransformerFactory factory = javax.xml.transform.TransformerFactory.newInstance();
        //factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        //factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        //factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Transformer transformer = factory.newTransformer();
        transformer.transform(new StreamSource(new FileReader("xxe.xml")),streamResult);
        return writer.toString();


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