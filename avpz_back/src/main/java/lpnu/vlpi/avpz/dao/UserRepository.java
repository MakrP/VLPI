package lpnu.vlpi.avpz.dao;

import lpnu.vlpi.avpz.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserModel, Long> {
    Optional<UserModel> findByLoginAndPassword(String login, String password);

    Optional<UserModel> findByUid(String userUid);

    @Override
    Page<UserModel> findAll(Pageable pageable);

    @Query(value = "SELECT MAX(CAST(uid AS LONG)) FROM Users", nativeQuery = true)
    Long getMaxUid();

    @Query(value = "SELECT count(u) / ?1 from UserModel u")
    int getTotalPages(long size);
}
