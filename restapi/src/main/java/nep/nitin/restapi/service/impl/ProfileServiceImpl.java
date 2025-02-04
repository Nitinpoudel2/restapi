package nep.nitin.restapi.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nep.nitin.restapi.dto.ProfileDTO;
import nep.nitin.restapi.entity.ProfileEntity;
import nep.nitin.restapi.exceptions.ItemExistsException;
import nep.nitin.restapi.exceptions.ResourceNotFoundException;
import nep.nitin.restapi.io.ErrorObject;
import nep.nitin.restapi.repository.ProfileRepository;
import nep.nitin.restapi.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor

public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder encoder;
    /**
     * It will save the user details to database
     * @param profileDTO
     * @return profileDTO
     */

        @Override
        public ProfileDTO createProfile(ProfileDTO profileDTO){
            if (profileRepository.existsByEmail(profileDTO.getEmail())){
                throw new ItemExistsException("Profile already exists " +profileDTO.getEmail());
            }
            profileDTO.setPassword(encoder.encode(profileDTO.getPassword()));
            ProfileEntity profileEntity =   mapToProfileEntity(profileDTO);
            profileEntity.setProfileId(UUID.randomUUID().toString());
           profileEntity = profileRepository.save(profileEntity);
           log.info("Printing the profile entity details {}", profileEntity);
           return mapToProfileDTO(profileEntity);
        }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ItemExistsException.class)
    public ErrorObject handleItemExistsException(ItemExistsException ex, WebRequest request) {
        log.error("Throwing the ItemExistsException from the GlobalExceptionHandler", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("Data_Exists")
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }
    /**
     * Mapper method to map values from profile entity to profile dto
     * @param profileEntity
     * @return profileDto
     */

    private ProfileDTO mapToProfileDTO(ProfileEntity profileEntity) {
            return modelMapper.map(profileEntity, ProfileDTO.class);
    }
    /**
     * Mapper method to map values from profile dto to profile entity
     * @param profileDTO
     * @return profileEntity
     */
    private ProfileEntity mapToProfileEntity(ProfileDTO profileDTO) {
            return modelMapper.map(profileDTO, ProfileEntity.class);
    }
}
