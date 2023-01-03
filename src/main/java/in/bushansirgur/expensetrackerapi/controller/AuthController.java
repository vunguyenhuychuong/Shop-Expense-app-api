package in.bushansirgur.expensetrackerapi.controller;

import in.bushansirgur.expensetrackerapi.entity.AuthModel;
import in.bushansirgur.expensetrackerapi.entity.JwtResponse;
import in.bushansirgur.expensetrackerapi.entity.UserModel;
import in.bushansirgur.expensetrackerapi.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import in.bushansirgur.expensetrackerapi.entity.User;
import in.bushansirgur.expensetrackerapi.service.UserService;
import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManagers;
    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {
        authenticate(authModel.getEmail(), authModel.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            Authentication authentication = authenticationManagers.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (DisabledException e){
            throw new Exception("User disabled");
        }catch (BadCredentialsException e) {
            throw new Exception("Bad Credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid  @RequestBody UserModel user) {
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }
}
