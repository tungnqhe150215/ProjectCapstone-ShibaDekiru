package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.*;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
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

    @Autowired
    private GoogleDriveService googleDriveService;

    @Override
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    @Override
    public void createUserAccount(UserAccountRegisterDto userAccount) {
        Long roleId = userAccount.getRoleId();

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        Optional<UserAccount> existingUser = userAccountRepository.findByEmailOrMemberId(userAccount.getEmail(), userAccount.getMemberId());

        if (existingUser.isPresent()) {
            throw new IllegalStateException("Email already or member be exists.");
        }

        else if (roleOptional.isPresent()) {

            Role role = roleOptional.get();

            UserAccount userAccount1 = new UserAccount();

            userAccount1.setNickName(userAccount.getNickName());
            userAccount1.setMemberId(userAccount.getMemberId());
            userAccount1.setUserName(userAccount.getNickName());

            String passWordEncode = passwordEncoder.encode(userAccount.getPassword());
            userAccount1.setPassword(passWordEncode);

            userAccount1.setEmail(userAccount.getEmail());
            userAccount1.setResetCode(null);
            userAccount1.setIsActive(userAccount.getIsActive());
            userAccount1.setIsBanned(userAccount.getIsBanned());
            userAccount1.setRole(role);

            UserAccount savedUser = userAccountRepository.save(userAccount1);

            //return userAccountRepository.save(userAccount1);

            for (RoleType roleType : RoleType.values()) {
                if (roleType.getId() == role.getRoleId()) {
                    if (roleType == RoleType.STUDENT) {
                        Student student = new Student();
                        student.setFirstName(userAccount.getFirstName());
                        student.setLastName(userAccount.getLastName());
                        student.setEmail(userAccount.getEmail());
                        student.setUserAccount(savedUser);
                        studentService.createStudentFromUserAccount(student);
                    } else if (roleType == RoleType.LECTURE) {
                        LecturesDto lectures = new LecturesDto();
                        lectures.setFirstName(userAccount.getFirstName());
                        lectures.setLastName(userAccount.getLastName());
                        lectures.setEmail(userAccount.getEmail());
                        lectures.setMemberId(userAccount.getMemberId());
                        lecturesService.createLecturerFromUserAccount(lectures);
                    }
                    break;
                }
            }

        } else {
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

            String passWordEncode = passwordEncoder.encode(userAccount.getPassword());

            userAccount1.setPassword(passWordEncode);
            userAccount1.setEmail(userAccount.getEmail());
            userAccount1.setResetCode(userAccount.getResetCode());
            userAccount1.setIsBanned(userAccount.getIsBanned());
            userAccount1.setIsActive(true);
            userAccount1.setRole(userAccount.getRole());

            return userAccountRepository.save(userAccount1);
        } else {
            throw new ResourceNotFoundException("User account not found with id: " + userAccountId);
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
            throw new ResourceNotFoundException("User account not found with id: " + userAccountId);
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

    @Override
    public UserAccount getUserAccountByEmail(String email) {
        Optional<UserAccount> userAccountOptional = userAccountRepository.findByEmail(email);
        UserAccount userAccount = null;
        if (userAccountOptional.isPresent()) {
            userAccount = userAccountOptional.get();

        } else {
            throw new ResourceNotFoundException("User Account not found any email with: " + email);
        }
        return userAccount;
    }

    public void changePassword(ChangePasswordDto request, Principal connectedUser) {
        if (!(connectedUser instanceof UsernamePasswordAuthenticationToken authenticationToken)) {
            throw new IllegalStateException("Người dùng chưa đăng nhập hoặc thông tin không hợp lệ.");
        }

        if (authenticationToken.getPrincipal() instanceof UserAccount) {
            var user = (UserAccount) authenticationToken.getPrincipal();


            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new IllegalStateException("Mật khẩu hiện tại không chính xác");
            }


            if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
                throw new IllegalStateException("Mật khẩu mới không trùng khớp");
            }


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

                        if (request.getAvatar().length() > 0) {
                            googleDriveService.deleteFile(student.getAvatar());
                            System.out.println("Check id file bị xóa: " + student.getAvatar());
                            updateProfileStudentByAvatar(request.getAvatar(), connectedUser);
                            student.setAvatar(request.getAvatar());
                        }
                        else {
                            String newFileUrl = googleDriveService.getFileUrl(student.getAvatar());
                            if (newFileUrl != null) {
                                // Loại bỏ phần &export=download từ đường dẫn mới
                                newFileUrl = removeExportParameter(newFileUrl);
                                // Cắt chuỗi để chỉ lấy phần ID của file
                                String fileId = cutFileId(newFileUrl);
                                student.setAvatar(fileId);
                                System.out.println("Current file id: " + student.getAvatar());
                            }
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

    private String removeExportParameter(String url) {
        int exportIndex = url.indexOf("&export=download");
        if (exportIndex != -1) {
            return url.substring(0, exportIndex);
        }
        return url;
    }

    private String cutFileId(String url) {
        int idIndex = url.lastIndexOf('=') + 1;
        return url.substring(idIndex);
    }

    public void updateProfileStudentByAvatar(String fileId, Principal connectedUser) {
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

                        student.setAvatar(fileId);
                        System.out.println("File ảnh : " + fileId);

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

    public UserAccount getByResetCode(String restCode) {
        return userAccountRepository.findByResetCode(restCode);
    }

    public void updatePassword(UserAccount userAccount, String newPassword) {
        String encodePassword = passwordEncoder.encode(newPassword);
        userAccount.setPassword(encodePassword);
        userAccountRepository.save(userAccount);
    }

}
