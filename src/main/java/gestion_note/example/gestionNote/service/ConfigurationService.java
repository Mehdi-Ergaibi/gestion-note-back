package gestion_note.example.gestionNote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gestion_note.example.gestionNote.model.entity.ConfiguationEnabling;
import gestion_note.example.gestionNote.repositories.ConfigurationRepository;

@Service
public class ConfigurationService {

    @Autowired
    ConfigurationRepository configurationRepository;

    public ConfiguationEnabling getConfiguration() {
        return configurationRepository.findById(1L).orElseGet(() -> { /// katchadd roe laxwal
            ConfiguationEnabling config = new ConfiguationEnabling();
            config.setId(1L);
            config.setNormalExamGradeEntryEnabled(false);
            config.setRattExamGradeEntryEnabled(false);
            config.setStudentGradeDisplayEnabled(false);
            return configurationRepository.save(config);
        });
    }

    public void updateNormalExamGradeEntry(boolean enabled) {
        ConfiguationEnabling config = getConfiguration();
        config.setNormalExamGradeEntryEnabled(enabled);
        configurationRepository.save(config);
    }

    public void updateRattExamGradeEntry(boolean enabled) {
        ConfiguationEnabling config = getConfiguration();
        config.setRattExamGradeEntryEnabled(enabled);
        configurationRepository.save(config);
    }

    public void updateStudentGradeDisplayed(boolean enabled) {
        ConfiguationEnabling config = getConfiguration();
        config.setStudentGradeDisplayEnabled(enabled);
        configurationRepository.save(config);
    }

}
