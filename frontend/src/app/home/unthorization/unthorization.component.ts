import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-unthorization',
  templateUrl: './unthorization.component.html',
  styleUrls: ['./unthorization.component.css']
})
export class UnthorizationComponent implements OnInit{
  constructor(
    private router: Router,
    private route: ActivatedRoute
  ){}
  ngOnInit(): void {
    
  }
  BacktoHome(){
    this.router.navigate(['home']);
  }

}
