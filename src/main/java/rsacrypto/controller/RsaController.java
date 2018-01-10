package rsacrypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import rsacrypto.RSA.RSA;
import rsacrypto.dto.DecryptDto;
import rsacrypto.dto.EncryptDto;

import javax.validation.Valid;

/**
 * @author ciprian.mosincat on 12/17/2017.
 */
@Controller
public class RsaController {

    private final RSA rsa;

    public RsaController(final RSA rsa) {
        this.rsa = rsa;
    }

    @GetMapping("/rsacrypto")
    public String getRsaPage(final Model model) {
        model.addAttribute("encryptDto", new EncryptDto());
        model.addAttribute("decryptDto", new DecryptDto());
        model.addAttribute("publicKey", rsa.getPublicKey().doubleValue());
        model.addAttribute("phi",rsa.getPhi());
        model.addAttribute("p",rsa.getP());
        model.addAttribute("q",rsa.getQ());
        model.addAttribute("n",rsa.getN());
        return "rsacrypto";
    }


    @PostMapping("/encrypt")
    public String postEncrypt(@Valid final EncryptDto encryptDto, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("publicKey", rsa.getPublicKey().doubleValue());
            model.addAttribute("decryptDto", new DecryptDto());
            model.addAttribute("phi",rsa.getPhi());
            model.addAttribute("p",rsa.getP());
            model.addAttribute("q",rsa.getQ());
            model.addAttribute("n",rsa.getN());
            return "rsacrypto";
        }

        encryptDto.setCiphertext(rsa.encrypt(encryptDto.getPlaintext()));
        model.addAttribute("publicKey", rsa.getPublicKey().doubleValue());
        model.addAttribute("encryptDto", encryptDto);
        model.addAttribute("decryptDto", new DecryptDto());
        model.addAttribute("phi",rsa.getPhi());
        model.addAttribute("p",rsa.getP());
        model.addAttribute("q",rsa.getQ());
        model.addAttribute("n",rsa.getN());
        return "rsacrypto";

    }

    @PostMapping("/decrypt")
    public String postDecrypt(@Valid final DecryptDto decryptDto, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("publicKey", rsa.getPublicKey().doubleValue());
            model.addAttribute("encryptDto", new EncryptDto());
            model.addAttribute("phi",rsa.getPhi());
            model.addAttribute("p",rsa.getP());
            model.addAttribute("q",rsa.getQ());
            model.addAttribute("n",rsa.getN());
            return "rsacrypto";
        }
        decryptDto.setDecryptedText(rsa.decrypt(decryptDto.getPlaintextd()).toLowerCase());
        model.addAttribute("publicKey", rsa.getPublicKey().doubleValue());
        model.addAttribute("encryptDto", new EncryptDto());
        model.addAttribute("decryptDto", decryptDto);
        model.addAttribute("phi",rsa.getPhi());
        model.addAttribute("p",rsa.getP());
        model.addAttribute("q",rsa.getQ());
        model.addAttribute("n",rsa.getN());
        return "rsacrypto";
    }

}
