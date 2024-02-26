package com.example.pastebin.service.serviceImpl;

import com.example.pastebin.dto.PasteRequestDTO;
import com.example.pastebin.dto.PasteResponseDTO;
import com.example.pastebin.dto.PasteUrlResponseDTO;
import com.example.pastebin.entity.PasteEntity;
import com.example.pastebin.entity.TypePaste;
import com.example.pastebin.exception.PasteNotFoundException;
import com.example.pastebin.exception.UserNotFoundException;
import com.example.pastebin.repository.PasteRepository;
import com.example.pastebin.repository.UserRepository;
import com.example.pastebin.service.PasteService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Value("${app.host}")
    private String host;


    @Override
    public List<PasteResponseDTO> getAllPublicPaste(String title) {
        //pasteRepository.deletePasteEntityByExpirationTimeBefore(LocalDateTime.now());
        return pasteRepository.findAll().stream().map(this::toDTO).filter(p->p.getTypePaste() == TypePaste.PUBLIC && (title == null || p.getTitle().equals(title)))
                .collect(Collectors.toList());
    }

    @Override
    public List<PasteResponseDTO> getAllUserPaste(Integer userId) {
        //pasteRepository.deletePasteEntityByExpirationTimeBefore(LocalDateTime.now());
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found",String.format("User with id %d was not found",userId))).getPastes()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PasteResponseDTO getOnePaste(String hash) {
        //pasteRepository.deletePasteEntityByExpirationTimeBefore(LocalDateTime.now());
        return toDTO(pasteRepository.findById(hash).orElseThrow(()-> new PasteNotFoundException("Paste not found",String.format("A paste with the hash of %s was not found",hash))));
    }

    @Override
    public PasteUrlResponseDTO createPaste(PasteRequestDTO pasteDTO,Integer userId) {
        PasteEntity pasteEntity = toEntity(pasteDTO,userId);
        pasteRepository.save(pasteEntity);
        return PasteUrlResponseDTO.builder()
                .url(host + pasteEntity.getHash()).build();
    }

    @Autowired
    public PasteServiceImpl(PasteRepository pasteRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.pasteRepository = pasteRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    private PasteEntity toEntity(PasteRequestDTO pasteRequestDTO,Integer id){
        PasteEntity pasteEntity = modelMapper.map(pasteRequestDTO,PasteEntity.class);
        pasteEntity.setExpirationTime(LocalDateTime.now().plus(pasteRequestDTO.getLifetime()));
        pasteEntity.setUser(userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found",String.format("User with id %d was not found",id))));
        pasteEntity.setHash(DigestUtils.sha256Hex(pasteEntity.getTitle()+pasteEntity.getContent()).substring(0,32));
        return pasteEntity;
    }
    private PasteResponseDTO toDTO(PasteEntity pasteEntity){
        PasteResponseDTO pasteResponseDTO = modelMapper.map(pasteEntity,PasteResponseDTO.class);
        pasteResponseDTO.setLifetimeMinutes(Duration.between(LocalDateTime.now(),pasteEntity.getExpirationTime()).toMinutes());
        pasteResponseDTO.setUrl(host + pasteEntity.getHash());
        return pasteResponseDTO;
    }

}
