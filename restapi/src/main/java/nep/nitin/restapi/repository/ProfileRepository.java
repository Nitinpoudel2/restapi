package nep.nitin.restapi.repository;

import nep.nitin.restapi.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

}
