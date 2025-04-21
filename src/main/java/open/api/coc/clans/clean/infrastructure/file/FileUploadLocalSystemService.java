package open.api.coc.clans.clean.infrastructure.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.file.repository.FileRepository;
import open.api.coc.clans.clean.infrastructure.file.config.FileConfig;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.common.exception.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileUploadLocalSystemService implements FileRepository {

    private final FileConfig fileConfig;

    @Override
    public String upload(String uploadType, MultipartFile file) {
        String uploadFileName = generateFileName(uploadType, file);
        Path uploadPath = generateFilePath(uploadType, uploadFileName);

        try {
            // 파일 업로드 진행
            Files.createDirectories(uploadPath.getParent());

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, uploadPath);
            }
        } catch (IOException e) {
            CustomRuntimeException ex = new CustomRuntimeException(ExceptionCode.INTERNAL_ERROR);
            ex.addExtraMessage("파일 업로드 중 실패: " + e.getMessage());
            throw ex;
        }

        return uploadPath.toFile().getPath();
    }

    @Override
    public Resource download(String downloadType, String fileName) {
        try {
            Path downloadPath = generateFilePath(downloadType, fileName);
            Resource resource = new UrlResource(downloadPath.toUri());

            if (!resource.exists()) {
                throw new NotFoundException("파일 없음");
            }

            return resource;
        } catch (MalformedURLException e) {
            CustomRuntimeException customRuntimeException = new CustomRuntimeException(ExceptionCode.INTERNAL_ERROR);
            customRuntimeException.addExtraMessage(e.getMessage());
            throw customRuntimeException;
        }
    }

    private String generateFileName(String uploadType, MultipartFile file) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        String fileName = UUID.randomUUID().toString();
        if ("CLAN-GAME".equals(uploadType.toUpperCase(Locale.ROOT))) {
            fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }

        return fileName + ".png";
    }

    private Path generateFilePath(String uploadType, String fileName) {
        // #{upload-path}/#{uploadType}/file.ext
        return Path.of(fileConfig.getUploadPath(),
                       uploadType.toLowerCase())
                   .resolve(fileName);
    }
}
