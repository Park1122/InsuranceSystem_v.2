package system.insurance.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.insurance.backend.dbo.announcement.Announcement;
import system.insurance.backend.dbo.employee.Employee;
import system.insurance.backend.exception.NoEmployeeException;

import java.util.Date;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    List<Announcement> findAllByTitle(String title);
    List<Announcement> findAllByAuthor(Employee author) throws NoEmployeeException;
    List<Announcement> findAllByDate(Date date);
}
