package open.api.coc.clans.clean.presentation.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.file.FileService;
import open.api.coc.clans.clean.domain.file.dto.FileUploadCommand;
import open.api.coc.clans.clean.presentation.file.dto.FileUploadRequest;
import open.api.coc.clans.clean.presentation.file.dto.FileUploadResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@Validated
public class FileController {

    private final FileService fileService;

    @Operation(description = "파일 업로드 API", summary = "파일 업로드 API, version: 0.1, date: 25.02.20")
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFile(@Valid @RequestPart(name = "file_info") FileUploadRequest fileInfo,
                                                         @RequestPart(name = "file") MultipartFile file) {

        FileUploadCommand command = FileUploadCommand.create(fileInfo, file);

        return ResponseEntity.ok()
                             .body(fileService.upload(command));
    }

}
