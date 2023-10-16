import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../user.service';
import { UserAccount } from 'src/app/core/models/user-account';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})

export class UserDetailComponent implements OnInit{
  id!:number;
  userAccount:UserAccount = new UserAccount;

  constructor(private route: ActivatedRoute, private userAccountService: UserService){

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.userAccount = new UserAccount();
    this.userAccountService.getUserByID(this.id).subscribe(
      data =>{
        this.userAccount = data
      }
    );
  }

}
