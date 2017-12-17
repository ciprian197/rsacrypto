package rsacrypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rsacrypto.dto.DecryptDto;
import rsacrypto.dto.EncryptDto;

/**
 * @author ciprian.mosincat on 12/17/2017.
 */
@Controller
@RequestMapping("/rsacrypto")
public class RsaController {

    @GetMapping
    public String getRsaPage(final Model model) {
        model.addAttribute("encryptDto", new EncryptDto());
        model.addAttribute("decryptDto", new DecryptDto());
        return "rsacrypto";
    }
}
