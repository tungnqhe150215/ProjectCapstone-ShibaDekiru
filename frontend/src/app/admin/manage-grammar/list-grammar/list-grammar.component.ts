import { Component, OnInit } from '@angular/core';
import { Grammar } from 'src/app/core/models/grammar';
import { GrammarService } from '../grammar.service';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-list-grammar',
  templateUrl: './list-grammar.component.html',
  styleUrls: ['./list-grammar.component.css']
})
export class ListGrammarComponent implements OnInit {
  'grammars': Grammar[];
  filterText: string = '';
  p: number = 1;
  constructor(private grammarService: GrammarService, private router: Router,private nofiService: NotificationService) {}
  ngOnInit(): void {
    this.getGrammars();
  }
  private getGrammars() {
    this.grammarService.getGrammarsList().subscribe((data) => {
      this.grammars = data;
    });
  }
  grammarDetails(id: number) {
    this.router.navigate(['/admin/grammar-details', id]);
  }
  updateGrammar(id: number) {
    this.router.navigate(['/admin/update-grammar', id]);
  }
  deleteGrammar(id: number) {
    this.grammarService.deleteGrammar(id).subscribe((data) => {
      console.log(data);
      this.getGrammars();
      this.nofiService.openSnackBar('Xóa ngữ pháp thành công');
    });
  }
  key: string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }
}
