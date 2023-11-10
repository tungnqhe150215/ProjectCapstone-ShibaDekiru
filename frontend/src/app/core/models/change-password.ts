export class ChangePassword {
    currentPassword !: string
    newPassword !: string
    confirmationPassword !: string

    constructor(currentPassword: string, newPassword: string, confirmationPassword: string ){
        this.currentPassword = currentPassword;
        this.newPassword = newPassword; 
        this.confirmationPassword = confirmationPassword;
    }
}
