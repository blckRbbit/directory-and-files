package com.gmail.leonidov.lex.porter.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "files", schema = "dev")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "size")
    private String size;

    @Column(name = "path_to")
    String path;

    @ManyToOne
    @JoinColumn(name = "directory_id")
    private Directory directory;
}
