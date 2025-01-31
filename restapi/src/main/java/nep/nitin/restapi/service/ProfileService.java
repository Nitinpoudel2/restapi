package nep.nitin.restapi.service;

import nep.nitin.restapi.dto.ProfileDTO;


public interface ProfileService {
    /**
     * It will save the user details to the database
     * @param profileDTO
     * @return profileDto
     */
    ProfileDTO createProfile(ProfileDTO profileDTO);

}
