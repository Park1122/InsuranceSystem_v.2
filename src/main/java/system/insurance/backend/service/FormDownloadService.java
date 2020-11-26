package system.insurance.backend.service;

import java.io.File;
import java.io.IOException;

public interface FormDownloadService {
    File downloadForm(String fileName) throws IOException;
}
