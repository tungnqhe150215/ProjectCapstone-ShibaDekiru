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


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit{

  public dataSource !: MatTableDataSource<UserAccount>;
  displayedColumns: string[] = ['userAccountId', 'roleId', 'nickName', 'userName','email','memberId', 'isBanned', 'resetCode','action'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  userAccount: UserAccount[] = [];
  ngOnInit(): void {
    this.getUserAccountList();
  }

  constructor(
    private userAccountService: UserService,
     private router: Router,
    private route:ActivatedRoute,
    public dialog: MatDialog
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


  openCreateUserAccountDialog(){
    this.dialog.open(CreateUserComponent, {
      
    }).afterClosed().subscribe(() => this.getUserAccountList())
  }

}
