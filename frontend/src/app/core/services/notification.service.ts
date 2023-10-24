import { validateVerticalPosition } from '@angular/cdk/overlay';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackBar: MatSnackBar) { }

  openSnackBar(message: string, action: string = 'ok'){
    this.snackBar.open(message, action,{
      duration: 1000,
      verticalPosition: 'top',
      horizontalPosition: 'right'
    });
  }
}
