import { Component, Inject, OnInit } from '@angular/core';
import { UserAccount } from 'src/app/core/models/user-account';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import { NotificationService } from 'src/app/core/services/notification.service';


@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatSelectModule]
})
export class CreateUserComponent implements OnInit {


  userAccount: UserAccount = new UserAccount;
  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>,
    private userAccountService: UserService,
    private router:Router,
    @Inject(MAT_DIALOG_DATA) public data: number,
    private nofiService: NotificationService
  ){}
  ngOnInit(): void {
    
  }
  
  createUser(){
    this.userAccountService.createUser(this.userAccount).subscribe(
     data =>{
        console.log(data);
        this.dialogRef.close();
        this.nofiService.openSnackBar("Tạo ngươì dùng thành công!");
      }
    )
  }

  

}
