package kz.kcell.file.manager.controller;

import kz.kcell.file.manager.data.FileData;
import kz.kcell.file.manager.service.storage.StorageService;
import kz.kcell.file.manager.service.storage.exception.StorageFileNotFoundException;
import kz.kcell.file.manager.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author amotov
 */
@Controller
public class FileUploadController {

    private final StorageService readonlyStorageService;
    private final StorageService writableStorageService;

    @Autowired
    public FileUploadController(
            StorageService readonlyStorageService,
            StorageService writableStorageService) {
        this.readonlyStorageService = readonlyStorageService;
        this.writableStorageService = writableStorageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("readonlyFiles", readonlyStorageService
                .loadAll()
                .map(path ->
                        new FileData(
                                MvcUriComponentsBuilder
                                        .fromMethodName(FileUploadController.class, "serveReadonlyFile", path.getFileName().toString())
                                        .build().toString(),
                        path.getFileName().toString(),
                        FileUtils.readableFileSize(path.toFile().length())))
                .collect(Collectors.toList()));
        model.addAttribute("uploadedFiles", writableStorageService
                .loadAll()
                .map(path ->
                        new FileData(
                                MvcUriComponentsBuilder
                                        .fromMethodName(FileUploadController.class, "serveUploadedFile", path.getFileName().toString())
                                        .build().toString(),
                                path.getFileName().toString(),
                                FileUtils.readableFileSize(path.toFile().length())))
                .collect(Collectors.toList()));
        return "upload-form";
    }

    @GetMapping("/files/readonly/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveReadonlyFile(@PathVariable String filename) {

        Resource file = readonlyStorageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @GetMapping("/files/uploaded/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveUploadedFile(@PathVariable String filename) {

        Resource file = writableStorageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        writableStorageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
