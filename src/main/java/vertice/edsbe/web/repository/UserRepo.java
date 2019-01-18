package vertice.edsbe.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import vertice.edsbe.web.model.User;

public interface UserRepo extends PagingAndSortingRepository<User,Integer> {
    User findByFirstName(String name);
    User findByToken(String token);
    User findByEmail(String email);
    User getOne(Integer id);
}
