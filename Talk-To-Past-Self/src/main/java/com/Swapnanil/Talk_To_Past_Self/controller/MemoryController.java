package com.Swapnanil.Talk_To_Past_Self.controller;


import com.Swapnanil.Talk_To_Past_Self.dto.MemoryResponse;
import com.Swapnanil.Talk_To_Past_Self.service.MemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/memories")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;



    @GetMapping("/{userId}")
    public List<MemoryResponse> getMemories(
            @PathVariable Long userId
    ) {
        return memoryService.getMemories(userId);
    }
}
