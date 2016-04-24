package hello.controllers;


import hello.classes.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String name = Utils.md5(bytes);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("public/assets/uploads/" + name)));
                stream.write(bytes);
                stream.close();
                return new ResponseEntity(name, HttpStatus.OK);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        } else {
            throw new Exception("file is empty");
        }
    }
}
