package vertice.edsbe.web.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vertice.edsbe.web.model.Messages;

import java.util.List;

public interface MessageRepo extends JpaRepository<Messages,Integer> {
   // Messages findById(int id);
    //
    @Query(value = "SELECT * FROM messages WHERE id =:id",nativeQuery = true)
    Messages find(@Param("id") int id);

    @Query(value = "SELECT m.* FROM messages m WHERE m.text like %:#{#messages.text}% ",nativeQuery = true)
    List<Messages> findMessageLike(@Param("messages") Messages messages);

}