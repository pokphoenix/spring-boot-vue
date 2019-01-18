package vertice.edsbe.web.repository;

import org.springframework.data.repository.CrudRepository;
import vertice.edsbe.web.model.UserAddress;

public interface UserAddressRepo extends CrudRepository<UserAddress,Integer> {
    UserAddress findByUserId(Integer userId);
}
