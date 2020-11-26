package system.insurance.backend.service;

import system.insurance.backend.exception.NoEmployeeException;
import system.insurance.backend.dto.AnnouncementDTO;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    List<AnnouncementDTO> findAll();
    List<AnnouncementDTO> findAllByAuthor(int id) throws NoEmployeeException;
    List<AnnouncementDTO> findAllByDate(String date);
    List<AnnouncementDTO> findAllByTitle(String title);
    Optional<AnnouncementDTO> findContent(int id);
}
