package open.api.coc.clans.clean.presentation.file.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FileUploadRequest {

    @NotNull @NotEmpty
    @Schema(description = "업로드 유형")
    @JsonProperty(value = "uploadType", defaultValue = "clan-game", required = true)
    private final String uploadType;

}
