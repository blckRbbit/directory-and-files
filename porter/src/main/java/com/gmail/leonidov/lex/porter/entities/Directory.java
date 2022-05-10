package com.gmail.leonidov.lex.porter.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "directories", schema = "dev")
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_time")
    private String date;

    @Column(name = "path_to")
    private String path;

    @Column(name = "file_count")
    private Integer fileCount;

    @Column(name = "dir_count")
    private Integer dirCount;

    @Column(name = "size")
    private String size;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "directory")
    private List<File> files;
}
