package mx.edu.utez.sda.springjwtdos.control;

import mx.edu.utez.sda.springjwtdos.entity.AuthRequest;
import mx.edu.utez.sda.springjwtdos.entity.UserInfo;
import mx.edu.utez.sda.springjwtdos.service.JwtService;
import mx.edu.utez.sda.springjwtdos.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/index")
    public String index(){
        return "Servicio Index";
    }


    @PostMapping("/registrame")
    public String registrame(@RequestBody UserInfo userInfo){
        service.guardarUser(userInfo);
        return "Usuario registrado";
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String soloAdmin(){
        return "Este endpoint es solo para admin";
    }

    @GetMapping("/user/test")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public String paraUser(){
        return "Este endpoint es para admin y user";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest){
        try {
            Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()){
                return jwtService.generateToken(authRequest.getUsername());
            }else {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
        }catch (Exception e){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
