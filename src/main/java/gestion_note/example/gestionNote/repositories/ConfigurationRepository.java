package gestion_note.example.gestionNote.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestion_note.example.gestionNote.model.entity.ConfiguationEnabling;

@Repository
public interface ConfigurationRepository extends JpaRepository<ConfiguationEnabling, Long> {

}
