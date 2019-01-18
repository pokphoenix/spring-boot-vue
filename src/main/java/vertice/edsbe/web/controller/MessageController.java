package vertice.edsbe.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vertice.edsbe.web.model.Messages;
import vertice.edsbe.web.services.ApiService;
import vertice.edsbe.web.services.MessageService;
import vertice.edsbe.web.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

//    private final MessageRepo messageRepository;
//
//    @Autowired
//    public MessageController(MessageRepo messageRepository){
//        this.messageRepository = messageRepository;
//    }

    @Autowired
    private MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    ApiService apiService;

    @GetMapping
    public List<Messages> index(){
        return messageService.findAll();
    }

    @GetMapping("{id}")
    public Messages getOne(@PathVariable("id") Messages messages){
        return messages;
    }

    @GetMapping("/search/{text}")
    public ResponseEntity search(@PathVariable("text") String text){
        System.out.println("messages : "+text);
        Messages message = new Messages();
        message.setText(text);
        List<Messages> messages = messageService.findMessageLike(message);
        return apiService.JsonResponse(messages,null);
    }

    @PostMapping
    public Messages create(@RequestBody @Valid  Messages message){
        return messageService.saveMessage(message);
    }

    @PutMapping("{id}")
    public Messages update(
            @PathVariable("id") Messages messageFromDb,
            @RequestBody Messages message){
        BeanUtils.copyProperties(message,messageFromDb,"id");
        return messageService.saveMessage(messageFromDb) ;
    }

    @DeleteMapping("{id}")
    public  void delete(@PathVariable("id") Messages messages){
        messageService.delete(messages);
    }


    @GetMapping("/pok")
    public String poktest(){
        return "poktest22";
    }

//    private int counter = 4 ;
//
//    public List<Map<String,String>> messages = new ArrayList<Map<String,String>>(){{
//        add(new HashMap<String, String>() {{ put("id","1"); put("text","pok1"); }}   );
//        add(new HashMap<String, String>() {{ put("id","2"); put("text","pok2"); }}   );
//        add(new HashMap<String, String>() {{ put("id","3"); put("text","pok3"); }}   );
//    }};

//    @GetMapping
//    public List<Map<String,String>> list(){
//        return messages;
//    }

//    @GetMapping("{id}")
//    public Map<String,String> getOne(@PathVariable String id){
//        return getMessage(id);
//    }

//    @GetMapping("{id}")
//    public Messages getOne(@PathVariable int id){
//        System.out.println("id :"+id);
//        return messageService.selectMessage(id);
//    }

//    private Map<String, String> getMessage(@PathVariable String id) {
//       return messages.stream()
//                .filter(message -> message.get("id").equals(id))
//                .findFirst()
//                .orElseThrow(NotFoundException::new);
//
//    }



//    @PostMapping
//    public  Map<String,String> create(@RequestBody Map<String,String> message){
//        message.put("id", String.valueOf(counter++));
//        messages.add(message);
//        return message;
//    }
//
//    @PutMapping("{id}")
//    public  Map<String,String> update(@PathVariable String id,@RequestBody Map<String,String> message){
//        //message.forEach((k,v) -> System.out.println("k : "+k+" , v : "+v));
//        Map<String, String> messageFromDb = getMessage(id);
//        messageFromDb.putAll(message);
//        messageFromDb.put("id",id);
//        return messageFromDb ;
//    }
//
//    @DeleteMapping("{id}")
//    public  void delete(@PathVariable String id){
//        Map<String, String> message = getMessage(id);
//        messages.remove(message);
//
//    }

}

