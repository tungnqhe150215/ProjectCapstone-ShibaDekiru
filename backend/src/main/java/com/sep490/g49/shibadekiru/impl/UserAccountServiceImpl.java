package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.*;
import com.sep490.g49.shibadekiru.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTServiceImpl jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private LecturesServiceImpl lecturesService;

    @Override
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        Long roleId = userAccount.getRole().getRoleId();

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isPresent()) {

            Role role = roleOptional.get();

            UserAccount userAccount1 = new UserAccount();

            userAccount1.setNickName(userAccount.getNickName());
            userAccount1.setMemberId(userAccount.getMemberId());
            userAccount1.setUserName(userAccount.getUsername());
            userAccount1.setPassword(userAccount.getPassword());
            userAccount1.setEmail(userAccount.getEmail());
            userAccount1.setResetCode(userAccount.getResetCode());
            userAccount1.setIsBanned(userAccount.getIsBanned());
            userAccount1.setRole(role);

            return userAccountRepository.save(userAccount1);

        }
        else {
            throw new ResourceNotFoundException("User Account can't be added.");
        }
    }

    @Override
    public UserAccount updateUserAccount(Long userAccountId, UserAccount userAccount) {
        Optional<UserAccount> existingUserAccount = userAccountRepository.findById(userAccountId);

        if (existingUserAccount.isPresent()) {

            UserAccount userAccount1 = existingUserAccount.get();

            userAccount1.setNickName(userAccount.getNickName());
            userAccount1.setMemberId(userAccount.getMemberId());
            userAccount1.setUserName(userAccount.getUsername());
            userAccount1.setPassword(userAccount.getPassword());
            userAccount1.setEmail(userAccount.getEmail());
            userAccount1.setResetCode(userAccount.getResetCode());
            userAccount1.setIsBanned(userAccount.getIsBanned());
            userAccount1.setRole(userAccount.getRole());

            return userAccountRepository.save(userAccount1);
        } else {
            throw new ResourceNotFoundException("Lesson not found with id: " + userAccountId);
        }

    }


    @Override
    public void updateIsBanned(Long userAccountId) {
        Optional<UserAccount> existingUserAccount = userAccountRepository.findById(userAccountId);

        if (existingUserAccount.isPresent()) {

            UserAccount userAccount = existingUserAccount.get();
            Boolean currentIsBanned = userAccount.getIsBanned();

            userAccount.setIsBanned(!currentIsBanned);

            userAccountRepository.save(userAccount);
        } else {
            throw new ResourceNotFoundException("Lesson not found with id: " + userAccountId);
        }
    }




    @Override
    public UserAccount getUserAccountById(Long userAccountId) {
        UserAccount userAccount = userAccountRepository.findById(userAccountId).orElse(null);

        if (userAccount == null) {
            throw new ResourceNotFoundException("User account not found with id: " + userAccountId);
        }
        return userAccount;
    }

    public void changePassword(ChangePasswordDto request, Principal connectedUser) {
        if (!(connectedUser instanceof UsernamePasswordAuthenticationToken authenticationToken)) {
            throw new IllegalStateException("Người dùng chưa đăng nhập hoặc thông tin không hợp lệ.");
        }

        if (authenticationToken.getPrincipal() instanceof UserAccount) {
            var user = (UserAccount) authenticationToken.getPrincipal();

            // Kiểm tra mật khẩu hiện tại có chính xác không
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new IllegalStateException("Mật khẩu hiện tại không chính xác");
            }

            // Kiểm tra xác nhận mật khẩu mới có trùng khớp không
            if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
                throw new IllegalStateException("Mật khẩu mới không trùng khớp");
            }

            // Cập nhật mật khẩu mới và lưu lại
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userAccountRepository.save(user);
        } else {
            throw new IllegalStateException("Không thể xác định thông tin người dùng.");
        }
    }

    public UserAccount updateProfile(UserAccountDto request, Principal connectedUser) {
        if (!(connectedUser instanceof UsernamePasswordAuthenticationToken authenticationToken)) {
            throw new IllegalStateException("Người dùng chưa đăng nhập hoặc thông tin không hợp lệ.");
        }

        if (authenticationToken.getPrincipal() instanceof UserAccount) {
            var user = (UserAccount) authenticationToken.getPrincipal();

            user.setUserName(request.getUserName());
            user.setNickName(request.getNickName());
            user.setMemberId(request.getMemberId());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setResetCode(request.getResetCode());
            user.setIsBanned(request.getIsBanned());
            user.setRole(request.getRole());
            return userAccountRepository.save(user);
        } else {
            throw new IllegalStateException("Không thể xác định thông tin người dùng.");
        }
    }

    public void updateProfileStudent(StudentDto request, Principal connectedUser) {
        if (connectedUser instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
            if (authenticationToken.getPrincipal() instanceof UserAccount) {
                UserAccount user = (UserAccount) authenticationToken.getPrincipal();

                if (user != null) {
                    user.setEmail(user.getEmail());
                    userAccountRepository.save(user);

                    System.out.println("MemberId: " + user.getMemberId());
                    System.out.println("Role: " + user.getRole());

                    user = userAccountRepository.findByMemberId(user.getMemberId());
                    Student student = studentService.getByUserAccount(user);

                    if (student != null) {
                        if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
                            student.setFirstName(request.getFirstName());
                        }

                        if (request.getLastName() != null && !request.getLastName().isEmpty()) {
                            student.setLastName(request.getLastName());
                        }

                        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                            student.setEmail(request.getEmail());
                        }

                        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
                            student.setAvatar(request.getAvatar());
                        }

                        if (request.getGender() != null) {
                            student.setGender(request.getGender());
                        }

                        student.setUserAccount(user);
                        studentRepository.save(student);
                    }
                }
            } else {
                throw new IllegalStateException("Người dùng không phải là UserAccount.");
            }
        } else {
            throw new IllegalStateException("Người dùng chưa đăng nhập hoặc thông tin không hợp lệ.");
        }
    }

    public void updateProfileLecture(LecturesDto request, Principal connectedUser) {
        if (connectedUser instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
            if (authenticationToken.getPrincipal() instanceof UserAccount) {
                UserAccount user = (UserAccount) authenticationToken.getPrincipal();

                if (user != null) {
                    user.setEmail(user.getEmail());
                    userAccountRepository.save(user);

                    System.out.println("MemberId: " + user.getMemberId());
                    System.out.println("Role: " + user.getRole());

                    user = userAccountRepository.findByMemberId(user.getMemberId());
                    Lectures lectures = lecturesService.getByUserAccount(user);

                    if (lectures != null) {
                        if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
                            lectures.setFirstName(request.getFirstName());
                        }

                        if (request.getLastName() != null && !request.getLastName().isEmpty()) {
                            lectures.setLastName(request.getLastName());
                        }

                        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                            lectures.setEmail(request.getEmail());
                        }

                        if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
                            lectures.setAvatar(request.getAvatar());
                        }

                        if (request.getPhone() != null) {
                            lectures.setPhone(request.getPhone());
                        }

                        lectures.setUserAccount(user);
                        lecturersRepository.save(lectures);
                    }
                }
            } else {
                throw new IllegalStateException("Người dùng không phải là UserAccount.");
            }
        } else {
            throw new IllegalStateException("Người dùng chưa đăng nhập hoặc thông tin không hợp lệ.");
        }
    }


    public void updateResetCode(String restCode, String email) {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findByEmail(email);

        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();

            userAccount.setResetCode(restCode);
            userAccountRepository.save(userAccount);
        } else {
            throw new ResourceNotFoundException("User Account not found any email with: " + email);
        }

    }

    public UserAccount get(String restCode) {
        return userAccountRepository.findByResetCode(restCode);
    }

    public void updatePassword(UserAccount userAccount, String newPassword) {
        String encodePassword = passwordEncoder.encode(newPassword);

        userAccount.setPassword(encodePassword);
        userAccount.setResetCode(null);

        userAccountRepository.save(userAccount);
    }

}
