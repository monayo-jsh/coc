package open.api.coc.clans.clean.domain.file.dto;

import java.util.List;
import java.util.Locale;
import open.api.coc.clans.clean.presentation.file.dto.FileUploadRequest;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public record FileUploadCommand(

    // 업로드 유형
    String uploadType,

    // 업로드 파일
    MultipartFile uploadFile

) {

    public static FileUploadCommand create(FileUploadRequest fileInfo, MultipartFile file) throws BadRequestException {
        FileUploadCommand instance = new FileUploadCommand(fileInfo.getUploadType(), file);
        instance.validate();
        return instance;
    }

    private void validate() throws BadRequestException {
        validateFile();
    }

    private void validateFile() throws BadRequestException {
        if (!StringUtils.hasText(this.uploadFile.getOriginalFilename())) {
            throw new BadRequestException("파일명을 확인해주세요.");
        }
        if (this.uploadFile.getSize() <= 0) {
            throw new BadRequestException("파일 크기를 확인해주세요");
        }

        validateFileExtension();
    }

    private void validateFileExtension() throws BadRequestException {
        List<String> allowedExtensions = List.of("JPG", "PNG");

        String fileExtension = StringUtils.getFilenameExtension(this.uploadFile.getOriginalFilename());
        assert fileExtension != null;
        if (!allowedExtensions.contains(fileExtension.toUpperCase(Locale.ROOT))) {
            throw new BadRequestException("파일 확장자를 확인해주세요");
        }
    }
}
