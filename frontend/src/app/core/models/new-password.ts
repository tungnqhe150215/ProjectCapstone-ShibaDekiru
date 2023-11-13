export class NewPassword {
    resetCode!:string
    newPassword!:string
    confirmNewPassword!:string

    constructor(newPassword:string, confirmNewPassword:string){
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
}
