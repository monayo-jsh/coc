package open.api.coc.clans.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.lab.LabEntity;
import open.api.coc.clans.database.repository.lab.LabRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lab")
public class LabController {

    private final LabRepository labRepository;

    @GetMapping("/link/all")
    public ResponseEntity<List<LabEntity>> getLinks() {
        return ResponseEntity.ok()
                             .body(labRepository.findAll());
    }

}
