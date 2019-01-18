package vertice.edsbe.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vertice.edsbe.web.model.Messages;
import vertice.edsbe.web.repository.MessageRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService  {
    @Autowired
    private MessageRepo messageRepo;


    public Messages selectMessage(int id) {
        return messageRepo.find(id);
    }


    public List<Messages> findAll(){
        return messageRepo.findAll();
    }

    public List<Messages> findMessageLike(Messages messages){
        return messageRepo.findMessageLike(messages);
    }

    public Messages saveMessage(Messages messages){
        return messageRepo.save(messages);
    }

    public void delete(Messages messages){
        messageRepo.delete(messages);
    }

}
