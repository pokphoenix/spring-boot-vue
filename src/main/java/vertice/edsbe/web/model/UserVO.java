package vertice.edsbe.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVO {
    @Valid
    private User user;
    @Valid
    private UserAddress userAddress;
}
