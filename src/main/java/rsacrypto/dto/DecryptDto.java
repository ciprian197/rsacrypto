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
    @Pattern(regexp = "[A-Z ]*", message = "The alphabet must contain only uppercase letters or blank space!")
    private String alphabetd;

    @NotEmpty
    @Pattern(regexp = "[A-Z ]*", message = "The keyword must contain only uppercase letters or blank space!")
    private String  keywordd;

    @NotEmpty
    @Pattern(regexp = "[a-z ]*", message = "The plaintext must contain only lowercase letters or blank space!")
    private String plaintextd;

    private String decryptedText;

}
