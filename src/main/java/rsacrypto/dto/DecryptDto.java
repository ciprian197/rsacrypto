package rsacrypto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * @author ciprian.mosincat on 27.10.2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecryptDto {

    @NotEmpty
    @Pattern(regexp = "[A-Z_]*", message = "The plaintext must contain only lowercase letters or blank space!")
    private String plaintextd;

    private String decryptedText;

}
