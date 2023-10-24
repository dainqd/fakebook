package com.example.fakebook.restapi;

import com.example.fakebook.dto.MarketingDto;
import com.example.fakebook.entities.Marketing;
import com.example.fakebook.service.MarketingService;
import com.example.fakebook.service.MessageResourceService;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/marketing")
@RequiredArgsConstructor
public class MarketingApi {
    final MessageResourceService messageResourceService;
    final MarketingService marketingService;

    @GetMapping("")
    public Page<MarketingDto> getList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return marketingService.findAllByStatus(Enums.MarketingStatus.ACTIVE, pageable).map(MarketingDto::new);
    }

    @GetMapping("/user/{id}")
    public Page<MarketingDto> getAllByUser(@PathVariable(name = "id") Long id,
                                           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return marketingService.findAllByUserId(id, pageable).map(MarketingDto::new);
    }

    @GetMapping("{id}")
    public MarketingDto getDetail(@PathVariable(name = "id") Long id) {
        Optional<Marketing> optionalMarketing;
        optionalMarketing = marketingService.findByIdAndStatus(id, Enums.MarketingStatus.ACTIVE);
        if (!optionalMarketing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        Marketing blog = optionalMarketing.get();
        return new MarketingDto(blog);
    }


    @PostMapping("")
    public MarketingDto create(@RequestBody MarketingDto marketingDto) {
        return new MarketingDto(marketingService.create(marketingDto, marketingDto.getUser().getId()));
    }

    @PutMapping("")
    public String update(@RequestBody MarketingDto request) {
        marketingService.update(request, request.getUser().getId());
        return messageResourceService.getMessage("update.success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        Optional<Marketing> optionalMarketing;
        optionalMarketing = marketingService.findByIdAndStatus(id, Enums.MarketingStatus.ACTIVE);
        if (!optionalMarketing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    messageResourceService.getMessage("account.not.found"));
        }
        marketingService.deleteById(id, optionalMarketing.get().getUser().getId());
        return new ResponseEntity<>(messageResourceService.getMessage("delete.success"), HttpStatus.OK);
    }
}
