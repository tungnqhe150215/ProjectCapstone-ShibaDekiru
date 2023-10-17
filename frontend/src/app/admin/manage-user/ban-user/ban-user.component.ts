import { Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { UserService } from '../user.service';
import { UserAccount } from 'src/app/core/models/user-account';


@Component({
  selector: 'app-ban-user',
  templateUrl: './ban-user.component.html',
  styleUrls: ['./ban-user.component.css']
})
export class BanUserComponent implements OnInit{

  constructor( public dialogRef: MatDialogRef<BanUserComponent>,
    private userAccountService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: number,){

  }

  id!:number;
  user:UserAccount = new UserAccount;
  banAccount(id:number){

  }

  ngOnInit(): void {
   
  }

}
