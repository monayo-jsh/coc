package open.api.coc.clans.clean.presentation.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record FileUploadResponse (

    @Schema(description = "업로드 유형")
    String uploadType,

    @Schema(description = "업로드 경로")
    String uploadPath

) {

    public static FileUploadResponse of(String uploadType, String uploadPath) {
        return new FileUploadResponse(uploadType, uploadPath);
    }

}
