package nep.nitin.restapi.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nep.nitin.restapi.entity.ProfileEntity;
import nep.nitin.restapi.repository.ProfileRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity profile = profileRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Profile not found for the email" + email));
        log.info("Inside loadUserByUsername()::: printing the profile details{}", profile);

        return new User(profile.getEmail(), profile.getPassword(), new ArrayList<>());
    }
}
