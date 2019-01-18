package vertice.edsbe.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vertice.edsbe.web.constant.Constants;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;


    @Size(min = Constants.STRING_MIN, max =  Constants.STRING_MAX, message
            = "firstName must be between "+Constants.STRING_MIN+" and "+ Constants.STRING_MAX+" characters")
    @NotEmpty(message = "firstName can't be blank")
    private String firstName;

    @Size(min = Constants.STRING_MIN, max =  Constants.STRING_MAX, message
            = "lastName must be between "+Constants.STRING_MIN+" and "+ Constants.STRING_MAX+" characters")
    @NotEmpty(message = "lastName can't be blank")
    private String lastName;

    @Size(min = Constants.STRING_MIN, max = 101, message
            = "fullName must be between "+Constants.STRING_MIN+" and 100 characters")
    @NotEmpty(message = "fullName can't be blank")
    private String fullName;
    private int age;

    @Email(message = "email must be email")
    @Size(min = Constants.STRING_MIN, max =  Constants.STRING_MAX, message
            = "email must be between "+Constants.STRING_MIN+" and "+ Constants.STRING_MAX+" characters")
    @NotEmpty(message = "email can't be blank")
    private String email;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String newPassword;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String confirmPassword;



//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @Transient
    private String token;

    @Column(updatable = false, nullable = false)
    private String salt;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @Transient
    private Date tokenExpire;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @Transient
//    @JsonIgnore
//    @JsonProperty(value = "password")
    private String password;

//    @CreationTimestamp
//    @UpdateTimestamp
//    support type
//    java.time.LocalDate (since Hibernate 5.2.3)
//    java.time.LocalDateTime (since Hibernate 5.2.3)
//    java.util.Date
//    java.util.Calendar
//    java.sql.Date
//    java.sql.Time
//    java.sql.Timestamp

//    @PrePersist
//    void createdAt() {
//        this.createdAt = this.updatedAt = new Date();
//    }
//
//    @PreUpdate
//    void updatedAt() {
//        this.updatedAt = new Date();
//    }

}
