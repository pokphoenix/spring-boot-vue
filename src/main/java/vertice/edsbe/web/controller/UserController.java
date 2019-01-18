package vertice.edsbe.web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vertice.edsbe.web.model.Messages;
import vertice.edsbe.web.model.PageVO;
import vertice.edsbe.web.model.User;
import vertice.edsbe.web.model.UserVO;
import vertice.edsbe.web.services.ApiService;
import vertice.edsbe.web.services.UserService;
import vertice.edsbe.web.utils.ApiUtil;
import vertice.edsbe.web.utils.functionalUtils;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ApiService apiService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    // pageVO require false
    // ex.
    // search?page=2&limit=2&sort.order=age&sort.attr=DESC&query=pok
    // or search?query=pok
    @RequestMapping(value = {"/search"})
    @ResponseBody
    public List<User> listUser(@Valid PageVO pageVO) {
        List<User> allProducts = userService.findUserByNameWithPage(pageVO);
        return allProducts;
    }

    @PostMapping("singup")
    @ResponseBody
    public ResponseEntity signUp(@RequestBody @Valid User user ) {
        return userService.signUp(user);
    }


    @PostMapping("login")
    @ResponseBody
    public ResponseEntity login(@RequestBody User user ) {
        return userService.login(user);
    }

    @GetMapping("api/user/{id}")   //this dynamic update column if it not null
    @ResponseBody
    public ResponseEntity getUser( @PathVariable("id") User userFromDb) {
        return apiService.JsonResponse(userFromDb,null);
    }



    @PutMapping("api/user/{id}")   //this dynamic update column if it not null
    @ResponseBody
    public ResponseEntity updateUser( @PathVariable("id") User userFromDb
            , @RequestBody @Valid User user ) {
        return userService.saveUser(user,userFromDb);

    }


    @PutMapping("api/changepass/{id}")   //this dynamic update column if it not null
    @ResponseBody
    public ResponseEntity changePass(@PathVariable("id") User userFromDb
            , @RequestBody User user ) {
        return userService.changePass(user,userFromDb);
    }


    //--- Create And Update Multiple Class
    @RequestMapping(
            value = "user",
            method = { RequestMethod.PUT, RequestMethod.POST }
    )
    @ResponseBody
    public UserVO saveUser(@RequestBody @Valid UserVO userVO ) {
        return userService.updateUser(userVO);
    }

}
