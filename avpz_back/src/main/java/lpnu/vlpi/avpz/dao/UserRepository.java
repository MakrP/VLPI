package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findByLoginAndPassword(String login, String password);

    Optional<UserModel> findByUid(String userUid);
}
