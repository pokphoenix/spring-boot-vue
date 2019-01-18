package vertice.edsbe.web.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vertice.edsbe.web.model.Bank;

import vertice.edsbe.web.model.Messages;
import vertice.edsbe.web.model.PageVO;
import vertice.edsbe.web.model.User;
import vertice.edsbe.web.model.UserVO;
import vertice.edsbe.web.repository.BankRepo;
import vertice.edsbe.web.repository.MessageRepo;
import vertice.edsbe.web.repository.UserRepo;
import vertice.edsbe.web.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    BankRepo bankRepo;

    @Autowired
    UserRepo userRepo;



    @RequestMapping("/")
    public String index() {
        //has to be without blank spaces
        return "forward:index.html";
        //return "poktest"; //   return url  ex. localhost/poktest ;
    }

    //--- for generate pattern id   (this ex. =  C-...)
    @RequestMapping("bank")
    @ResponseBody   //--- add this for return body
    public Bank bank() {
        Bank bank = new Bank();
        bank.setText(RandomStringUtils.randomAlphanumeric(10));  // this require commons-lang3 dependency
        bankRepo.save(bank);
        return bank;
    }

    //--- pagination JPA
    @RequestMapping(value = {"/test", "/test/{id}"})
    @ResponseBody
    public Page<User> test(@PathVariable Optional<String> id ) {
        Integer page = 0;
        if (id.isPresent()) {
            page = Integer.parseInt(id.get());
        }
        System.out.println("page : "+page);
        Pageable firstPageWithTwoElements = PageRequest.of(page, 2);
        Page<User> allProducts = userRepo.findAll(firstPageWithTwoElements);
        return allProducts;
    }

   /* @RequestMapping(value = {"/page/{name}", "/page/{name}/{id}"})
    @ResponseBody
    public List<User> page(@PathVariable("name") String name ,@PathVariable("id") Optional<String> id ) {
        Integer page = 0;
        if (id.isPresent()) {
            page = Integer.parseInt(id.get());
        }
        System.out.println("page : "+page);
        System.out.println("name : "+name);
        Pageable firstPageWithTwoElements = PageRequest.of(page, 2);
        List<User> allProducts = bankRepo.findAllByFirstName(name);

        return allProducts;
    }*/




}
