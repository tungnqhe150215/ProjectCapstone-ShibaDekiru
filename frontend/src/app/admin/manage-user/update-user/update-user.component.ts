import { Component, Inject, OnInit } from '@angular/core';
import { UserAccount } from 'src/app/core/models/user-account';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user.service';
import { NotificationService } from 'src/app/core/services/notification.service';


@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit{
  userAccount: UserAccount = new UserAccount();
  id!:number;
  constructor(
    // public dialogRef: MatDialogRef<UpdateUserComponent>,
    private userAccountService: UserService,
    private router:Router,
    // @Inject(MAT_DIALOG_DATA) public data: number,
    private route: ActivatedRoute, 
    private nofiService: NotificationService
  ){}
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.userAccountService.getUserByID(this.id).subscribe(
      data =>{
        this.userAccount = data;
      }, error => console.log(error)
    );
  }


  onSubmit(){
    this.userAccountService.updateUser(this.id, this.userAccount).subscribe(
      data =>{
        this.goToUserList();
        this.nofiService.openSnackBar('Update user successful','OK');
      },error => console.log(error)
    );
  }

  goToUserList(){
    this.router.navigate(['admin/user-account']);
  }
  // updateUser(){
  //   this.userAccountService.updateUser(this.id, this.userAccount).subscribe(
  //     data =>{
  //       console.log(data);
  //       // this.dialogRef.close();
  //     }
  //   )
  // }
}
