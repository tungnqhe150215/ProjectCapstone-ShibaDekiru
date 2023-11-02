import { Component } from '@angular/core';
import { faTrash} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-class-list',
  templateUrl: './class-list.component.html',
  styleUrls: ['./class-list.component.css','../../home-style.css']
})
export class ClassListComponent {
    trash = faTrash;

}
