package com.gmail.leonidov.lex.porter.controllers;

import com.gmail.leonidov.lex.porter.dtos.DirectoryDto;
import com.gmail.leonidov.lex.porter.dtos.FileDto;
import com.gmail.leonidov.lex.porter.entities.Directory;
import com.gmail.leonidov.lex.porter.services.DirectoryService;
import com.gmail.leonidov.lex.porter.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class PorterController {
    private final DirectoryService directoryService;

    @GetMapping("/")
    public String index(Model model, @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size)  {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
        Page<DirectoryDto> dirPage = directoryService.getPage(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("directories", dirPage);
        int totalPages = dirPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

    @PostMapping
    public String createDirectory(@RequestParam String path) {
        directoryService.create(path);
        return "redirect:/";
    }

    @PostMapping("/files")
    @ModelAttribute("filesFromDirectory")
    public String showFiles(@RequestParam Long id, Model model) {
        model.addAttribute("files", directoryService.getAllFromDirectory(id));
        return "files";
    }
}
