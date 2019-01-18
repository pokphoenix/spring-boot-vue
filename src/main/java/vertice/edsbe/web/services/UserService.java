package vertice.edsbe.web.services;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vertice.edsbe.web.constant.Constants;
import vertice.edsbe.web.model.PageVO;
import vertice.edsbe.web.model.User;
import vertice.edsbe.web.model.UserVO;
import vertice.edsbe.web.repository.UserAddressRepo;
import vertice.edsbe.web.repository.UserRepo;
import vertice.edsbe.web.utils.functionalUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
@Service
public class UserService {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserAddressRepo userAddressRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApiService apiService;

    private int saltLength =  Constants.SALT_LENGTH ;

    @Transactional
    public UserVO updateUser(UserVO userVO){
        Integer userId = null ;
        if (userVO.getUser()!=null){
            userRepo.save(userVO.getUser());
            userId = userVO.getUser().getId();
        }
        if (userVO.getUserAddress()!=null && userId!=null ){
            userVO.getUserAddress().setUserId(userId);
            userAddressRepo.save(userVO.getUserAddress());
        }else if (userId!=null){
            userVO.setUserAddress(userAddressRepo.findByUserId(userId));
        }
        return userVO;
    }

    public ResponseEntity saveUser(User user,User userFromDb){
        user.setPassword(null); //--- cannot remmove this  because if it not null token and password will auto update
        functionalUtils.copyPropertiesNotNull(user,userFromDb);
        userRepo.save(userFromDb);
        userFromDb.setSalt(null);
        userFromDb.setPassword(null);
        return apiService.JsonResponse(userFromDb,null);
    }

    public ResponseEntity login(User userSend) {
        String email = userSend.getEmail();
        String password = userSend.getPassword();
        User user = userRepo.findByEmail(email);
        System.out.println("[user] : "+user);
        if (ObjectUtils.isEmpty(user)){
            return apiService.JsonResponseError("not found this user",401);
        }
        if(!passwordEncoder.matches(password,user.getPassword())) {
            return apiService.JsonResponseError("password mismatch",401);
        }
        refreshToken(user);
        return apiService.JsonResponse(user,null);
    }

    public ResponseEntity changePass(User user, User userFromDb){
        if(!passwordEncoder.matches(user.getPassword(),userFromDb.getPassword())) {
            return apiService.JsonResponseError("password not match in database",401);
        }

        if (user.getPassword().equals(user.getNewPassword())){
            return apiService.JsonResponseError("new password much be change",401);
        }

        if (!user.getConfirmPassword().equals(user.getNewPassword())){
            return apiService.JsonResponseError("new password and confirm password not match",401);
        }

        String newPasswordBcrypt = passwordEncoder.encode(user.getNewPassword());
        userFromDb.setPassword(newPasswordBcrypt);
        refreshToken(userFromDb);
        userFromDb.setSalt(null);
        userFromDb.setPassword(null);
        return apiService.JsonResponse(userFromDb,null);
//        userFromDb.setTokenExpire(generateTokenExpire());
//        userRepo.save(userFromDb);
    }

    public ResponseEntity signUp(User user){
        String passwordBcrypt = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordBcrypt);
        String salt = BCrypt.gensalt(saltLength);
        user.setSalt(salt);
        //String salt = RandomStringUtils.randomAlphanumeric(saltLength);
        refreshToken(user);
        System.out.println("user id : "+user.getId());
        user.setSalt(null);
        user.setPassword(null);
        return apiService.JsonResponse(user,null);
    }

    public User getUser(Integer id){
        return userRepo.getOne(id);
    }

    public User refreshToken(User user){
        String passwordBcrypt = user.getPassword();
        String salt = user.getSalt();

        System.out.println("[pok] token bf : "+user.getToken());
        Date tokenExpire = generateTokenExpire() ;
        user.setTokenExpire(tokenExpire);
        String token = generateToken(passwordBcrypt,salt) ;
//      System.out.println("[pok] passwordBcrypt : "+passwordBcrypt);
//      System.out.println("[pok] passwordBcrypt : "+passwordBcrypt.length());
//      System.out.println("[pok] salt : "+salt);
//      System.out.println("[pok] salt len : "+salt.length());
        System.out.println("[pok] token : "+token);
//      System.out.println("[pok] token : "+token.length());
        user.setToken(token);
        userRepo.save(user);

        user.setPassword(null);
        user.setSalt(null);
        return  user;
    }



    public List<User> findUserByNameWithPage(PageVO pageVO){

        String sql = "SELECT " +
                "        D.* , " +
                "        :limit AS dataPerPage " +
                "    FROM\n" +
                "        ( SELECT\n" +
                "            ROW_NUMBER() OVER(\n" +
                "        ORDER BY id asc\n" ;
        System.out.println("sort : "+(pageVO.getSort()!=null));
        if (pageVO.getSort()!=null){
            sql += " , "+pageVO.getSort().getOrder()+" "+pageVO.getSort().getAttr();
        }
        sql +=  "            ) AS rowNum ,\n" +
                "            count(1) OVER () AS totCount ,\n" +
                "            T.* \n" +
                "        FROM\n" +
                "            ( SELECT\n" +
                "                * \n" +
                "            FROM\n" +
                "                users  \n" ;
        System.out.println("[pok] : "+ObjectUtils.isEmpty(pageVO.getQuery()));
        if (!ObjectUtils.isEmpty(pageVO.getQuery())){
            sql += "WHERE first_name like :query " ;
        }
        sql +=  "            ) T ) D \n" +
                "        WHERE\n" +
                "            D.rowNum > \n" +
                "                (:page-1)*:limit\n" +
                "            AND D.rowNum <= :page*:limit" ;
        System.out.println("query : "+sql);

        Query q1 = em.createNativeQuery(sql,User.class) ;
        if (!ObjectUtils.isEmpty(pageVO.getQuery())){
            q1.setParameter("query", "%"+pageVO.getQuery()+"%");
        }

        return q1.setParameter("limit", pageVO.getLimit())
                .setParameter("page", pageVO.getPage())
                .getResultList();

    }


    public String generateToken(String password,String salt) {
        String hashpw = BCrypt.hashpw(password, salt)+"-"+new Timestamp(System.currentTimeMillis()).toString();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(
                hashpw.getBytes(StandardCharsets.UTF_8));
        return functionalUtils.bytesToHex(hash);
    }

    public Boolean checkAuthen(String token){
        User user = authenUser(token);
        System.out.println("[checkAuthen] : "+user);
        Boolean rstl = false;
        if(user!=null){
            rstl = true;
        }
        System.out.println("[checkAuthen] rstl : "+rstl);
        return rstl;
    }

    private User authenUser(String token){
        User user = userRepo.findByToken(token);
        System.out.println("[pok] user "+user);
        System.out.println("[pok] user "+ObjectUtils.isEmpty(user));
        if (!ObjectUtils.isEmpty(user)){
            Date tokenExpire = user.getTokenExpire();
            Date dateNow = new Date();
            System.out.println("[pok] tokenExpire : "+dateNow.after(tokenExpire));
            if (dateNow.after(tokenExpire)) {
                return null;  // authen fail
            }else{
                // wait for manage all return response with token (include all exception)
                //***  add Session
                return user;
            }
        }
        return null;
    }
    
    public Date generateTokenExpire(){
        return DateUtils.addHours(new Date(), Constants.TOKEN_EXPIRE_HOUR);
    }


}
