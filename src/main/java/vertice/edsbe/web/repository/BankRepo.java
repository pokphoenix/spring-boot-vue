package vertice.edsbe.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import vertice.edsbe.web.model.Bank;
import vertice.edsbe.web.model.Messages;
import vertice.edsbe.web.model.PageVO;
import vertice.edsbe.web.model.User;


import java.util.List;

public interface BankRepo extends PagingAndSortingRepository<Bank,Integer> {


}