package open.api.coc.clans.clean.domain.file;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.file.dto.FileUploadCommand;
import open.api.coc.clans.clean.domain.file.repository.FileRepository;
import open.api.coc.clans.clean.presentation.file.dto.FileUploadResponse;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public FileUploadResponse upload(FileUploadCommand command) throws CustomRuntimeException {
        String uploadPath = fileRepository.upload(command.uploadType(), command.uploadFile());

        return FileUploadResponse.of(command.uploadType(), uploadPath);
    }

    public Resource download(String downloadType, String fileName) {
        return fileRepository.download(downloadType, fileName);
    }
}
