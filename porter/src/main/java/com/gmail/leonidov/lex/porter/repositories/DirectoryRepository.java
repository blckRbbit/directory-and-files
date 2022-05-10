package com.gmail.leonidov.lex.porter.repositories;

import com.gmail.leonidov.lex.porter.entities.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
}
