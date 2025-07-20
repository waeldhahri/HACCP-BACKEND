package com.example.haccpbackend.others.mail.modulDocuments;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/documents")
public class DocumentController {


    @Autowired
    private DocumentService documentService;

    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Desktop/downloads/";


    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

/*

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Document> uploadDocument(@RequestParam("file") MultipartFile file,
                                                   @RequestParam(value = "keywords", required = false) String keywords) {
        try {
            Document savedDocument = documentService.uploadFile(file, keywords);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDocument);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Optional<Document> document = documentService.getDocumentById(id);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Document>> searchDocuments(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(documentService.searchByKeyword(keyword));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        try {
            boolean deleted = documentService.deleteDocument(id);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/download/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id)  throws IOException {
        Optional<Document> document = documentService.getDocumentById(id);
        if (document.isPresent()) {
            Path path = Paths.get(document.get().getFilePath());
            try {

                // Assure que le dossier de téléchargement existe
                Files.createDirectories(Paths.get(DOWNLOAD_DIR));


                // Chemin du fichier original
                Path originalPath = Paths.get(document.get().getFilePath());

                // Nouveau chemin dans "downloads"
                Path downloadPath = Paths.get(DOWNLOAD_DIR, document.get().getName());

                // Copier le fichier dans "downloads"
                Files.copy(originalPath, downloadPath, StandardCopyOption.REPLACE_EXISTING);









                Resource resource = new UrlResource(path.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(document.get().getFileType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.get().getName() + "\"")
                            .body(resource);
                }
            } catch (MalformedURLException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.notFound().build();
    }






*/




}
