package tn.esprit.workspace_workflow.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.workspace_workflow.service.FileService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@CrossOrigin(origins = "http://localhost:4200")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file){
        fileService.save(file);
        log.info("File saved");
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/get/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName){
        Resource resource = fileService.getFile(fileName);
        if (resource != null){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return ResponseEntity.internalServerError().build();
    }
}