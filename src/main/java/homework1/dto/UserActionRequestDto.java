package homework1.dto;

import homework1.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserActionRequestDto {
    private User currentUser;
    private String emailToAction;
}
