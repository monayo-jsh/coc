package open.api.coc.clans.clean.domain.file.repository;

import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {

    String upload(String uploadType, MultipartFile file);

}
