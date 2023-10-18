package com.example.fakebook.service;

import com.example.fakebook.repositories.MarketingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketingService {
    final MarketingRepository marketingRepository;
    final MessageResourceService messageResourceService;


}
