package com.mithril.chatapp.userservice.Repository;
import com.mithril.chatapp.userservice.Entity.Users;
import com.mithril.chatapp.userservice.enums.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByPhoneNumberAndCountryCode(Long phoneNumber, CountryCode code);
}
