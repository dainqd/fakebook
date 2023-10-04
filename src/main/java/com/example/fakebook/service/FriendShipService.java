package com.example.fakebook.service;

import com.example.fakebook.repositories.FriendShipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    final FriendShipRepository friendShipRepository;
    final MessageResourceService messageResourceService;


}
