package com.example.fakebook.service;

import com.example.fakebook.dto.MarketingDto;
import com.example.fakebook.entities.Blog;
import com.example.fakebook.entities.Marketing;
import com.example.fakebook.repositories.MarketingRepository;
import com.example.fakebook.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketingService {
    final MarketingRepository marketingRepository;
    final MessageResourceService messageResourceService;

    public Page<Marketing> findAll(Pageable pageable) {
        return marketingRepository.findAll(pageable);
    }

    public Optional<Marketing> findById(long id) {
        return marketingRepository.findById(id);
    }

    public Marketing save(Marketing blog) {
        return marketingRepository.save(blog);
    }

    public Marketing create(MarketingDto marketingDto, long adminId) {
        try {
            Marketing marketing = new Marketing();
            BeanUtils.copyProperties(marketingDto, marketing);
            marketing.setCreatedAt(LocalDateTime.now());
            marketing.setCreatedBy(adminId);
            marketing.setStatus(Enums.MarketingStatus.ACTIVE);

            return marketingRepository.save(marketing);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("create.error"));

        }
    }

    public Marketing update(MarketingDto marketingDto, long adminID) {
        try {
            Optional<Marketing> marketingOptional = marketingRepository.findById(marketingDto.getId());
            if (!marketingOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            Marketing marketing = marketingOptional.get();

            BeanUtils.copyProperties(marketingDto, marketing);
            marketing.setUpdatedAt(LocalDateTime.now());
            marketing.setUpdatedBy(adminID);
            return marketingRepository.save(marketing);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("update.error"));
        }
    }

    public void deleteById(long id, long adminID) {
        try {
            Optional<Marketing> optionalMarketing = marketingRepository.findById(id);
            if (!optionalMarketing.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        messageResourceService.getMessage("id.not.found"));
            }
            optionalMarketing.get().setStatus(Enums.MarketingStatus.DELETED);
            optionalMarketing.get().setDeletedAt(LocalDateTime.now());
            optionalMarketing.get().setDeletedBy(adminID);
            marketingRepository.save(optionalMarketing.get());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    messageResourceService.getMessage("cancel.error"));
        }
    }

    public Page<Marketing> findAllByStatus(Enums.MarketingStatus status, Pageable pageable) {
        return marketingRepository.findAllByStatus(status, pageable);
    }

    public Optional<Marketing> findByIdAndStatus(long id, Enums.MarketingStatus status) {
        return marketingRepository.findByIdAndStatus(id, status);
    }

    public Page<Marketing> findAllByUserId(Long id, Pageable pageable) {
        return marketingRepository.findAllByUser_id(id, pageable);
    }

    public Page<Marketing> findAllByUserIdAndStatus(Long id, Pageable pageable) {
        return marketingRepository.findAllByUser_idAndStatus(id, Enums.MarketingStatus.ACTIVE, pageable);
    }
}
