package open.api.coc.clans.clean.presentation.laboratory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public record LaboratoryCreateRequest(

    @Schema(description = "연구소 이름")
    @NotEmpty(message = "연구소 이름을 입력해주세요.")
    @JsonProperty(value = "name")
    String name,

    @Schema(description = "연구소 링크")
    @URL(message = "URL 형식을 확인해주세요.")
    @NotEmpty(message = "연구소 링크 URL을 입력해주세요.")
    @JsonProperty(value = "link_url")
    String linkUrl

) {

}
