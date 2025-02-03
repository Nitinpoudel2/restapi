package nep.nitin.restapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import nep.nitin.restapi.dto.ProfileDTO;
import nep.nitin.restapi.io.AuthRequest;
import nep.nitin.restapi.io.AuthResponse;
import nep.nitin.restapi.io.ProfileRequest;
import nep.nitin.restapi.io.ProfileResponse;
import nep.nitin.restapi.service.CustomUserDetailService;
import nep.nitin.restapi.service.ProfileService;
import nep.nitin.restapi.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor

public class AuthController {

    private final ModelMapper modelMapper ;
    private final ProfileService profileService ;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService userDetailService;

    /**
     * API endpoint to register a new user
     * @param profileRequest
     * @return profile Response
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        log.info("API /register is called {}", profileRequest);
        ProfileDTO profileDTO = mapToProfileDTO(profileRequest);
        profileDTO = profileService.createProfile(profileDTO);
        log.info("Printing the profile DTO details {}", profileDTO);
        return mapToProfileResponse(profileDTO);

        }
    @PostMapping("/login")
    public AuthResponse authenticateProfile(@RequestBody AuthRequest authRequest) throws Exception {
        log.info("API/login is called{}", authRequest);
        authenticate(authRequest);
        final UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new AuthResponse(token, authRequest.getEmail());
    }

    private void authenticate(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        }catch(DisabledException ex) {
            throw new Exception("Profile disabled");
        }catch(BadCredentialsException ex) {
            throw new Exception("Bad Credentials");
        }
    }

    /**
     * Mapper method to map values from profile request to profile DTO
     * @param profileRequest
     * @return profileDTO
     */


    private ProfileDTO mapToProfileDTO(@Valid ProfileRequest profileRequest) {
        return modelMapper.map(profileRequest, ProfileDTO.class);
    }
    /**
     * Mapper method to map values from profile dto to profile response
     * @param profileDTO
     * @return profileResponse
     */

    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO) {
        return modelMapper.map(profileDTO, ProfileResponse.class);
    }
}
