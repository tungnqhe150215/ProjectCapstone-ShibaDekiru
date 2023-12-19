package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.*;
import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.*;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IUserAccountService;
import com.sep490.g49.shibadekiru.util.MailServiceProvider;
import com.sep490.g49.shibadekiru.util.RandomStringGeneratorService;
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

    @Autowired
    private MailServiceProvider mailServiceProvider;

    @Autowired
    private RandomStringGeneratorService randomStringGeneratorService;

    @Override
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    @Override
    public void createUserAccount(UserAccountRegisterDto userAccount) {
        Long roleId = userAccount.getRoleId();

        Optional<Role> roleOptional = roleRepository.findById(roleId);
        Optional<UserAccount> existingEmail = userAccountRepository.findByEmail(userAccount.getEmail());
        UserAccount existingMemberId = userAccountRepository.findByMemberId(userAccount.getMemberId());

        if (existingEmail.isPresent() || existingMemberId != null) {
            throw new IllegalStateException("Email already or memberId be exists.");
        }

        else if (roleOptional.isPresent()) {

            Role role = roleOptional.get();

            UserAccount userAccount1 = new UserAccount();

            userAccount1.setNickName(userAccount.getNickName());

            userAccount1.setMemberId(userAccount.getMemberId());
            userAccount1.setUserName(userAccount.getNickName());
            userAccount1.setIsCreatedByAdmin(true);


            String passWordEncode = randomStringGeneratorService.randomAlphaNumeric(8);

            String passWord = passwordEncoder.encode(passWordEncode);

            userAccount1.setPassword(passWord);

            userAccount1.setEmail(userAccount.getEmail());
            userAccount1.setResetCode(null);
            userAccount1.setIsActive(userAccount.getIsActive());
            userAccount1.setIsBanned(userAccount.getIsBanned());
            userAccount1.setRole(role);

            UserAccount savedUser = userAccountRepository.save(userAccount1);


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
                        Lectures lectures = new Lectures();
                        lectures.setFirstName(userAccount.getFirstName());
                        lectures.setLastName(userAccount.getLastName());
                        lectures.setEmail(userAccount.getEmail());
                        lectures.setUserAccount(savedUser);
                        lecturesService.createLecturerFromUserAccount(lectures);
                    }
                    break;
                }
            }

            sendEmail(userAccount1.getEmail(), userAccount1.getEmail(), passWordEncode);

        } else {
            throw new ResourceNotFoundException("User Account can't be added.");
        }
    }


    private void sendEmail(String recipientEmail, String email, String password) {

        String subject = "Đây là tài khoản đăng nhập của bạn";
        String content = "<p>Xin chào,</p>"
                + "<p>Đây là tài khoản đăng nhập của bạn</p>"
                + "<p>Xin vui lòng không chia sẻ cho bất kỳ ai.</p>"
                + "<p>Tài khoản: "+ email + "</p>"
                + "<p>Mật khẩu: "+ password +"</p>"
                + "<br>";
        mailServiceProvider.sendEmail(recipientEmail, subject, content);
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
            userAccount1.setIsActive(true);
            userAccount1.setRole(userAccount.getRole());

            return userAccountRepository.save(userAccount1);
        } else {
            throw new ResourceNotFoundException("User account not found with id: " + userAccountId);
        }
    }


    @Override
    public UserAccount updateUserAccountByIsCreatedByAdmin(Long userAccountId, UserAccount userAccount) {
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
            userAccount1.setIsActive(true);
            userAccount1.setIsCreatedByAdmin(true);
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
                throw new RuntimeException("Mật khẩu hiện tại không chính xác");
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

    public Student updateProfileStudent(Student request, Principal connectedUser) {
        if (connectedUser instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
            if (authenticationToken.getPrincipal() instanceof UserAccount) {
                UserAccount user = (UserAccount) authenticationToken.getPrincipal();

                if (user != null) {
                    user.setEmail(user.getEmail());


                    System.out.println("MemberId: " + user.getMemberId());
                    System.out.println("Role: " + user.getRole());

                    user = userAccountRepository.findByMemberId(user.getMemberId());
                    Student student = studentService.getByUserAccount(user);
                    System.out.println("Ảnh trong student: " + student.getAvatar());
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

                        if (!request.getAvatar().equals(student.getAvatar())) {
                            System.out.println("Khi request khác student");
                            googleDriveService.deleteFile(request.getAvatar());
                            student.setAvatar(student.getAvatar());
                        }

                        else if (request.getAvatar().equals("")) {
                            student.setAvatar("");
                        }

                        else {
                            System.out.println("Vẫn giữ nguyên link ảnh.");
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



                        if (request.getPhone() != null) {
                            String phoneNumber = request.getPhone();
                            System.out.println("Phone : " + phoneNumber);
                            if (phoneNumber.matches("^(((\\+|)84)|0)+(3|5|7|8|9|1[2|6|8|9])+([0-9]{8})$")) {
                                if (!phoneNumber.equals(student.getPhone()) && studentRepository.existsByPhone(phoneNumber)) {
                                    throw new IllegalStateException("Số điện thoại mới đã tồn tại trong cơ sở dữ liệu.");
                                } else {
                                    student.setPhone(phoneNumber);
                                }
                            } else {
                                throw new RuntimeException("Vui lòng nhập số điện thoại hợp lệ ở Việt Nam.");
                            }
                        }





                        student.setUserAccount(user);
                        return studentRepository.save(student);
                    }
                }
            } else {
                throw new IllegalStateException("Người dùng không phải là UserAccount.");
            }
        } else {
            throw new IllegalStateException("Người dùng chưa đăng nhập hoặc thông tin không hợp lệ.");
        }
        return request;
    }

    public Lectures updateProfileLecture(Lectures request, Principal connectedUser) {
        if (connectedUser instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) connectedUser;
            if (authenticationToken.getPrincipal() instanceof UserAccount) {
                UserAccount user = (UserAccount) authenticationToken.getPrincipal();

                if (user != null) {
                    user.setEmail(user.getEmail());


                    System.out.println("MemberId: " + user.getMemberId());
                    System.out.println("Role: " + user.getRole());

                    user = userAccountRepository.findByMemberId(user.getMemberId());
                    Lectures lectures = lecturesService.getByUserAccount(user);
                    System.out.println("Ảnh trong lecture: " + lectures.getAvatar());
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


                        if (!request.getAvatar().equals(lectures.getAvatar())) {
                            System.out.println("Khi request khác lecture");
                            googleDriveService.deleteFile(request.getAvatar());
                            lectures.setAvatar(lectures.getAvatar());
                        }

                        else if (request.getAvatar().equals("")) {
                            lectures.setAvatar("");
                        }

                        else {

                            System.out.println("Vẫn giữ nguyên link ảnh.");
                            String newFileUrl = googleDriveService.getFileUrl(lectures.getAvatar());
                            if (newFileUrl != null) {
                                // Loại bỏ phần &export=download từ đường dẫn mới
                                newFileUrl = removeExportParameter(newFileUrl);
                                // Cắt chuỗi để chỉ lấy phần ID của file
                                String fileId = cutFileId(newFileUrl);
                                lectures.setAvatar(fileId);
                                System.out.println("Current file id: " + lectures.getAvatar());
                            }
                        }

                        if (request.getGender() != null) {
                            lectures.setGender(request.getGender());
                        }


                        if (request.getPhone() != null) {
                            String phoneNumber = request.getPhone();
                            System.out.println("Phone : " + phoneNumber);
                            if (phoneNumber.matches("^(((\\+|)84)|0)+(3|5|7|8|9|1[2|6|8|9])+([0-9]{8})$")) {
                                if (!phoneNumber.equals(lectures.getPhone()) && lecturersRepository.existsByPhone(phoneNumber)) {
                                    throw new IllegalStateException("Số điện thoại mới đã tồn tại trong cơ sở dữ liệu.");
                                } else {
                                    lectures.setPhone(phoneNumber);
                                }
                            } else {
                                throw new RuntimeException("Vui lòng nhập số điện thoại hợp lệ ở Việt Nam.");
                            }
                        }



                        lectures.setUserAccount(user);
                        return lecturersRepository.save(lectures);
                    }
                }
            } else {
                throw new IllegalStateException("Người dùng không phải là UserAccount.");
            }
        } else {
            throw new IllegalStateException("Người dùng chưa đăng nhập hoặc thông tin không hợp lệ.");
        }
        return request;
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


    public void updateProfileByAvatar(String fileId, Principal connectedUser) {
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

                    if (user.getRole() != null) {
                        RoleType roleType = user.getRole().getRoleType();

                        switch (roleType) {
                            case STUDENT:
                                updateAvatarForStudent(fileId, user);
                                break;
                            case LECTURE:
                                updateAvatarForLecturer(fileId, user);
                                break;
                            default:
                                throw new IllegalStateException("Role không hợp lệ.");
                        }
                    } else {
                        throw new IllegalStateException("Role không tồn tại.");
                    }
                }
            } else {
                throw new IllegalStateException("Người dùng không phải là UserAccount.");
            }
        } else {
            throw new IllegalStateException("Người dùng chưa đăng nhập hoặc thông tin không hợp lệ.");
        }
    }

    private void updateAvatarForStudent(String fileId, UserAccount user) {
        Student student = studentService.getByUserAccount(user);
        if (student != null) {
            if (!student.getAvatar().equals(fileId)) {
                googleDriveService.deleteFile(student.getAvatar());
                System.out.println("Check id file bị xóa trong updateAvatarForStudent: " + student.getAvatar());
                student.setAvatar(fileId);
            }

            student.setUserAccount(user);
            studentRepository.save(student);
        }
    }

    private void updateAvatarForLecturer(String fileId, UserAccount user) {
        Lectures lecturer = lecturesService.getByUserAccount(user);
        if (lecturer != null) {
            if (!lecturer.getAvatar().equals(fileId)) {
                googleDriveService.deleteFile(lecturer.getAvatar());
                System.out.println("Check id file bị xóa trong updateAvatarForLecturer: " + lecturer.getAvatar());
                lecturer.setAvatar(fileId);
            }

            lecturer.setUserAccount(user);
            lecturersRepository.save(lecturer);
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
