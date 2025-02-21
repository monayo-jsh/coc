package open.api.coc.clans.clean.domain.file.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileRepository {

    String upload(String uploadType, MultipartFile file);

    Resource download(String downloadType, String fileName);

}
