import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { StudentLessonService } from '../student-lesson.service';
import {MatExpansionModule} from '@angular/material/expansion';
import { MatDialog } from '@angular/material/dialog';
import { Listening } from 'src/app/core/models/listening';
import { Lesson } from 'src/app/core/models/lesson';
import { ListeningQuestion } from 'src/app/core/models/listening-question';
import { Book } from 'src/app/core/models/book';

@Component({
  selector: 'app-list-listening',
  templateUrl: './list-listening.component.html',
  styleUrls: ['./list-listening.component.css']
})
export class ListListeningComponent implements OnInit {

  public dataSource !: MatTableDataSource<Listening>;

  displayedColumns: string[] = ['id', 'name', 'lesson','script'];
  slicedItems: string[][] = []; // Khai báo ở đây
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort1!: MatSort;

  idB!:number;
  listening: Listening[] = [];
  lesson: Lesson = new Lesson;
  listeningQuestion: ListeningQuestion[]=[];
  lessonN: Lesson[] = [];
  Lbook: Book = new Book;
  constructor(
    private router: Router,
    private studentLessonService: StudentLessonService,
    private route: ActivatedRoute,
    public dialog: MatDialog,
  ) { }


  ngOnInit(): void {
    this.getListenByLessonID();
    this.getLessonById();
    this.getLessonByBookID();
    this.getBookById();
  }

  p: number = 1;
  id!:number
  getListenByLessonID() {
    // const idLesson = this.studentLessonService.getLessonID();
    this.id = this.route.snapshot.params['idL'];
    this.listening = [];
    this.studentLessonService.getListeningByLesson(this.id)
    .subscribe({
      next:data =>{
        this.listening = data;
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.paginator = this.paginator;
        this.slicedItems = this.getSlicedItems(this.listening, 'script');
        console.log(data)
      }
    })

  }

  getSlicedItems(data: any[], propertyName: string): string[][] {
    return data.map(item => item[propertyName]
      .split('。')
      .filter((subItem: string) => subItem.trim() !== '')
    );
  }

  getLessonById() {
    this.lesson = new Lesson();
    this.id = this.route.snapshot.params['idL'];
    this.studentLessonService.getLessonById(this.id).subscribe(data => {
      this.lesson = data
    })
  }

  idLs!:number;
  getQuestionListeningByLesson(idLs:number){
    // this.id = this.route.snapshot.params['idL'];
    // const idLesson = this.studentLessonService.getLessonID();
    this.listeningQuestion = [];
    this.studentLessonService.getListeningQuesByListening(idLs)
    .subscribe({
      next:data =>{
        this.listeningQuestion = data;
        // this.dataSource.paginator = this.paginator;
        console.log(data)
      }
    })
  }

  //pagging
  key: string = 'id';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }


  getLessonByBookID() {
    this.idB = this.route.snapshot.params['id'];
    const idBook = this.studentLessonService.getBookId();
    this.lessonN = [];
    this.studentLessonService.getLessonByBook(this.idB).subscribe({
      next: (res) => {
        this.lessonN = res;
        console.log(res)
      },
    })
  }

  LessonDetail(id:number, idL:number) {
    this.id = idL;
    this.studentLessonService.setLessonID(idL);
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/detail']);
    this.getLessonById();
  }

  getBookById()  {
    this.idB = this.route.snapshot.params['id'];
    this.Lbook = new Book();
    this.studentLessonService.getBookById(this.idB).subscribe(res =>{
    this.Lbook = res
   })
  }
  
  // selectedAnswers: String[] = new Array(this.listeningQuestion.length).fill('');

  // checkCorrect(selectedAnswer: String, correctAnswer: String): boolean {
  //   return selectedAnswer === correctAnswer;
  // }

  // selectedAnswers: string[] = new Array(this.listeningQuestion.length).fill('');
  // results: boolean[] = new Array(this.listeningQuestion.length).fill(null);

  // checkAnswer(index: number): void {
  //   const selectedAnswer = this.selectedAnswers[index];
  //   const correctAnswer = this.listeningQuestion[index].correctAnswer;

  //   this.results[index] = selectedAnswer === correctAnswer;
  // }

  // selectedAnswers: string[] = new Array(this.listeningQuestion.length).fill('');
  // results: (boolean | null)[] = new Array(this.listeningQuestion.length).fill(null);
  // isCheckPressed: boolean = false;

  // checkAnswer(index: number): void {
  //   const selectedAnswer = this.selectedAnswers[index];
  //   const correctAnswer = this.listeningQuestion[index].correctAnswer;

  //   this.results[index] = selectedAnswer === correctAnswer;
  //   this.isCheckPressed = true; 
  // }

  // resetAnswer(index: number): void {
  //   this.selectedAnswers[index] = '';
  //   this.results[index] = null;
  //   this.isCheckPressed = false;
  // }

  selectedAnswers: string[] = new Array(this.listeningQuestion.length).fill('');
  results: (boolean | null)[] = new Array(this.listeningQuestion.length).fill(null);
  isCheckPressed: boolean = false;

  checkAllAnswers(): void {
    for (let i = 0; i < this.listeningQuestion.length; i++) {
      const selectedAnswer = this.selectedAnswers[i];
      const correctAnswer = this.listeningQuestion[i].correctAnswer;

      this.results[i] = selectedAnswer === correctAnswer;
    }

    this.isCheckPressed = true;
  }

  resetAllAnswers(): void {
    this.selectedAnswers = new Array(this.listeningQuestion.length).fill('');
    this.results = new Array(this.listeningQuestion.length).fill(null);
    this.isCheckPressed = false;
  }
}
