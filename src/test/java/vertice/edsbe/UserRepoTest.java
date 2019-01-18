package vertice.edsbe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import vertice.edsbe.web.model.Messages;
import vertice.edsbe.web.model.User;
import vertice.edsbe.web.repository.UserRepo;
import vertice.edsbe.web.services.MessageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MessageService messageService;


    @Test
    public void testFetchData(){


        User user = userRepo.findByFirstName("pok") ;
        System.out.println("user : "+user);

        Messages message = messageService.selectMessage(1);
        System.out.println("message : "+message);

     /*   Messages message2 = messageRepository.find(1);
        System.out.println("message2 : "+message2);*/




    }

}
