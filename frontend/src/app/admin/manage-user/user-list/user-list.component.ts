import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { UserAccount } from 'src/app/core/models/user-account';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatSort, MatSortModule} from '@angular/material/sort';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { CreateUserComponent } from '../create-user/create-user.component';
import {ActivatedRoute, Route} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { BanUserComponent } from '../ban-user/ban-user.component';
import { NotificationService } from 'src/app/core/services/notification.service';
import { UpdateUserComponent } from '../update-user/update-user.component';


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit{

  public dataSource !: MatTableDataSource<UserAccount>;
  displayedColumns: string[] = ['userAccountId', 'roleId', 'nickName', 'userName','email','memberId', 'isBanned', 'action'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  userAccount: UserAccount[] = [];
  bannedAccount: UserAccount = new UserAccount;

  ngOnInit(): void {
    this.getUserAccountList();
  }

  constructor(
    private userAccountService: UserService,
     private router: Router,
    private route:ActivatedRoute,
    public dialog: MatDialog,
    private nofiService: NotificationService
     ){

  }

  private getUserAccountList(){
    this.userAccountService.getUserAccountList()
    .subscribe( data =>{
      this.userAccount = data;
      this.dataSource = new MatTableDataSource(this.userAccount);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(data);
    })
    console.log(this.userAccount);
  }

   userAccountDetail(id: number){
    this.router.navigate(['/admin/user-account/userAccountDetail',id]);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  banAccount( bannedAccount: UserAccount): void {
    this.userAccountService.banAccountUser(bannedAccount.userAccountId).subscribe(() => {
      // Sau khi cập nhật thành công, cập nhật trạng thái đánh dấu sao trong danh sách
      bannedAccount.isBanned = !bannedAccount.isBanned;
      this.nofiService.openSnackBar('Change status successful','OK');
      // flashcard.favorite = !flashcard.favorite;

    });
  }
  
  // updateUser(id:number){
  //   this.dialog.open(UpdateUserComponent,{

  //   }).afterClosed().subscribe(() => this.getUserAccountList())
  // }
  updateUser(id:number){
    this.router.navigate(['admin/user-account/update-userAccount',id]);
  }


  openCreateUserAccountDialog(){
    this.dialog.open(CreateUserComponent, {
      
    }).afterClosed().subscribe(() => this.getUserAccountList())
  }

}
