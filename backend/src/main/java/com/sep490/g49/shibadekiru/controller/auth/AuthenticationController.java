package com.sep490.g49.shibadekiru.controller.auth;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Student;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.impl.*;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.StudentRepository;
import com.sep490.g49.shibadekiru.repository.TokenRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import com.sep490.g49.shibadekiru.service.IStudentService;
import com.sep490.g49.shibadekiru.service.IUserAccountService;
import com.sep490.g49.shibadekiru.util.JWTUtilityService;
import com.sep490.g49.shibadekiru.util.MailServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILecturesService iLecturesService;

    @Autowired
    private IStudentService iStudentService;

    @Autowired
    private UserAccountServiceImpl userAccountService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JWTServiceImpl jwtService;

    @Autowired
    private JWTUtilityService jwtUtilityService;

    @Autowired
    private MailServiceProvider mailServiceProvider;

    @Autowired
    private GoogleDriveService googleDriveService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterResponse request
    ) {
        try {
            authenticationService.register(request);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException r) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r.getMessage());
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationDto> authenticate(
            @RequestBody AuthenticationLoginDto request
    ) {
        //return ResponseEntity.ok(authenticationService.authenticate(request));

        AuthenticationDto authResult = authenticationService.authenticate(request);

        String accessToken = authResult.getAccessToken();
        UserAccountDto userAccountDto = authenticationService.getUserInfoByToken(accessToken);

        if (userAccountDto.getIsBanned().equals(false) && userAccountDto.getIsActive().equals(true)) {

            System.out.println("Token: " + accessToken);
            System.out.println("Nick name: " +  userAccountDto.getNickName());
            System.out.println("Member id: " +  userAccountDto.getMemberId());
            System.out.println("Pass word:" + userAccountDto.getPassword());
            System.out.println("Role id: " +  userAccountDto.getRole());
            System.out.println("Member id: " +  userAccountDto.getMemberId());
            System.out.println("US id: " +  userAccountDto.getUserName());
            System.out.println("Is created by Admin: " + userAccountDto.getIsCreatedByAdmin());

            UserAccount userAccount = modelMapper.map(userAccountDto,UserAccount.class);
            if ( userAccount.getRole().getRoleId() == 2L){
                authResult.setUserAccountId(iLecturesService.getByUserAccount(userAccount).getLectureId());
            }
            if (userAccount.getRole().getRoleId() == 3L){
                authResult.setUserAccountId(iStudentService.getByUserAccount(userAccount).getStudentId());
            }
            authResult.setUserId(userAccount.getUserAccountId());
            authResult.setIsCreatedByAdmin(userAccount.getIsCreatedByAdmin());
            authResult.setRole(userAccountDto.getRole());
            authResult.setNickName(userAccountDto.getNickName());
            return ResponseEntity.ok(authResult);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/user-account")
    public ResponseEntity<UserAccountDto> getUserAccountByMemberId(@RequestParam (name = "userId") Long memberId){

        UserAccount userAccountRequest =  userAccountRepository.findById(memberId).orElseThrow();

        UserAccountDto userAccountDto = modelMapper.map(userAccountRequest, UserAccountDto.class);

        return ResponseEntity.ok().body(userAccountDto);

    }

    @GetMapping("/user-account/lecture")
    public ResponseEntity<LecturesDto> getLectureById(@RequestParam (name = "userAccountId") Long userAccountId){

        Lectures lecturesRequest =  lecturersRepository.findById(userAccountId).orElseThrow();

        LecturesDto lecturesDto = modelMapper.map(lecturesRequest, LecturesDto.class);

        return ResponseEntity.ok().body(lecturesDto);

    }

    @GetMapping("/user-account/student/{userAccountId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable (name = "userAccountId") Long userAccountId){

        Student studentRequest =  studentRepository.findById(userAccountId).orElseThrow();
        if (studentRequest.getAvatar().length() > 0 && !studentRequest.getAvatar().equals("")) {
            studentRequest.setAvatar(googleDriveService.getFileUrl(studentRequest.getAvatar()));
        }

        StudentDto studentDto = modelMapper.map(studentRequest, StudentDto.class);

        return ResponseEntity.ok().body(studentDto);

    }

    @GetMapping("/user-account/lecture/{userAccountId}")
    public ResponseEntity<LecturesDto> getLectureImageById(@PathVariable (name = "userAccountId") Long userAccountId){

        Lectures lecturesRequest =  lecturersRepository.findById(userAccountId).orElseThrow();
        if (lecturesRequest.getAvatar().length() > 0 && !lecturesRequest.getAvatar().equals(""))  {
            lecturesRequest.setAvatar(googleDriveService.getFileUrl(lecturesRequest.getAvatar()));
        }

        LecturesDto lecturesDto = modelMapper.map(lecturesRequest, LecturesDto.class);

        return ResponseEntity.ok().body(lecturesDto);

    }


    @PutMapping("/user-account")
    public ResponseEntity<UserAccountDto> updateUserAccountByNickname(@RequestParam (name = "nickName") String nickName, @RequestParam (name = "userId") Long userAccountId){

        UserAccount userAccountRequest =  userAccountRepository.findById(userAccountId).orElseThrow();


        userAccountRequest.setNickName(nickName);


        UserAccount userAccount = userAccountService.updateUserAccount(userAccountId, userAccountRequest);


        UserAccountDto userAccountDto = modelMapper.map(userAccount, UserAccountDto.class);

        return ResponseEntity.ok().body(userAccountDto);
    }

    @GetMapping("/verify/{resetCode}")
    public ResponseEntity<?> registerVerification(@PathVariable (name = "resetCode") String resetCode) throws Exception {

        UserAccount userAccountRequest = userAccountService.getByResetCode(resetCode);

        userAccountRequest.setIsActive(true);

        userAccountService.updateResetCode(null, userAccountRequest.getEmail());

        UserAccount userAccount = userAccountService.updateUserAccount(userAccountRequest.getUserAccountId() , userAccountRequest);


        UserAccountDto userAccountDto = modelMapper.map(userAccount, UserAccountDto.class);


        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordDto> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {

        UserAccount userAccount = userAccountService.getUserAccountByEmail(forgotPasswordDto.getEmail());

        if (userAccount == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            String resetCode = jwtUtilityService.createJWT(userAccount, 10080);
            System.out.println("Reset code : " + resetCode);

            userAccountService.updateResetCode(resetCode, userAccount.getEmail());

            sendEmail(userAccount.getEmail(), resetCode);

            ForgotPasswordDto forgotPasswordResponse = modelMapper.map(userAccount, ForgotPasswordDto.class);

            return ResponseEntity.ok().body(forgotPasswordResponse);
        }

    }

    @GetMapping("/reset-password/{resetCode}")
    public ResponseEntity<String> verifyResetCode(@PathVariable String resetCode) throws Exception {
        UserAccount userAccount = jwtUtilityService.verifyToken(resetCode);

        if (userAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok("Valid reset code");
    }

    @PostMapping("/reset-password/{resetCode}")
    public ResponseEntity<NewPasswordDto> resetPassword(@PathVariable String resetCode, @RequestBody NewPasswordDto newPasswordDto) throws Exception {

        UserAccount verifyToken = jwtUtilityService.verifyToken(resetCode);
        UserAccount userAccount = userAccountService.getByResetCode(resetCode);

        if (verifyToken == null || userAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


        String newPassword = newPasswordDto.getNewPassword();
        String confirmNewPassword = newPasswordDto.getConfirmNewPassword();

        if (!newPassword.equals(confirmNewPassword)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            userAccountService.updateResetCode(null, userAccount.getEmail());
            userAccountService.updatePassword(userAccount, newPassword);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }



    }

    private void sendEmail(String recipientEmail, String resetCode) {

        String resetLink = "http://localhost:4200/reset-password/" + resetCode;

        String subject = "Đây là liên kết để đặt lại mật khẩu của bạn";
        String content = "<p>Xin chào,</p>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>"
                + "<p>Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn:</p>"
                + "<p><a href='"+ resetLink +"'>Thay đổi mật khẩu</a></p>"
                + "<br>"
                + "<p>Bỏ qua email này nếu bạn nhớ mật khẩu của mình, "
                + "hoặc bạn chưa thực hiện yêu cầu.</p>";
        mailServiceProvider.sendEmail(recipientEmail, subject, content);
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
