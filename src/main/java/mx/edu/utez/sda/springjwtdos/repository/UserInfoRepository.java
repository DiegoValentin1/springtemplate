package mx.edu.utez.sda.springjwtdos.repository;

import mx.edu.utez.sda.springjwtdos.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    public Optional<UserInfo> findByUsername(String username);

}
