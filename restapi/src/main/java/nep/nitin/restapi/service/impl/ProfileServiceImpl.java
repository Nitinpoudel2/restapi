package nep.nitin.restapi.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nep.nitin.restapi.dto.ProfileDTO;
import nep.nitin.restapi.entity.ProfileEntity;
import nep.nitin.restapi.repository.ProfileRepository;
import nep.nitin.restapi.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor

public class ProfileServiceImpl implements ProfileService {
        private final ProfileRepository profileRepository;
        private final ModelMapper modelMapper;
    /**
     * It will save the user details to database
     * @param profileDTO
     * @return profileDTO
     */

        @Override
        public ProfileDTO createProfile(ProfileDTO profileDTO){
            ProfileEntity profileEntity =   mapToProfileEntity(profileDTO);
            profileEntity.setProfileId(UUID.randomUUID().toString());
           profileEntity = profileRepository.save(profileEntity);
           log.info("Printing the profile entity details {}", profileEntity);
           return mapToProfileDTO(profileEntity);
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
