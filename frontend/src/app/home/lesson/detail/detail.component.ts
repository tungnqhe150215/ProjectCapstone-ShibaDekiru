import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { StudentLessonService } from '../student-lesson.service';
import { Book } from 'src/app/core/models/book';
import { Lesson } from 'src/app/core/models/lesson';
import { StorageService } from '../../auth/user-login/storage.service';

@Component({
  selector: 'app-lesson-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css'],
})
export class DetailComponent implements OnInit {

  id!: number;
  idB!:number;
  lessonN: Lesson = new Lesson;
  lesson: Lesson[] = [];
  book: Book[] = [];
  isLoggedIn = false;
  Lbook: Book = new Book;
  constructor(
    private studentLessonService: StudentLessonService,
    private router: Router,
    private route: ActivatedRoute,
    private storageService: StorageService,
  ) { }


  currentUser: any;

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
    this.isLoggedIn = this.storageService.isLoggedIn();
    this.getLessonByBookID();
    this.getLessonById();
    this.getBookById();
  }

  getLessonById() {
    this.id = this.route.snapshot.params['idL'];
    if(this.studentLessonService.getLessonID()){
      this.id = this.studentLessonService.getLessonID();
    }
    // const idLesson = this.studentLessonService.getLessonID();
    this.lessonN = new Lesson();
    console.log(this.id);
    this.studentLessonService.getLessonById(this.id)
      .subscribe(
        data => {
          this.lessonN = data
          console.log(data)
        }
      )
  }

  LessonDetail(id:number, idL:number) {
    this.id = idL;
    this.studentLessonService.setLessonID(idL);
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/detail']);
    this.getLessonById();
  }


  getLessonByBookID() {
    this.idB = this.route.snapshot.params['id'];
    const idBook = this.studentLessonService.getBookId();
    this.lesson = [];
    this.studentLessonService.getLessonByBook(this.idB).subscribe({
      next: (res) => {
        this.lesson = res;
        console.log(res)
      },
    })
  }

  getBookById() {
    this.Lbook = new Book();
    this.idB = this.route.snapshot.params['id'];
    this.studentLessonService.getBookById(this.idB).subscribe(res => {
      this.Lbook = res
    })
  }



  ListVocab(id:number, idL:number) {
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/vocabulary']);
  }
  ListKanji(id:number, idL:number) {
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/kanji']);
  }

  ListGrammar(id:number, idL:number) {
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/grammar']);
  }
  ListKaiwa(id:number, idL:number) {
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/kaiwa']);
  }
  ListListening(id:number, idL:number) {
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/listening']);
  }
  ListReading(id:number, idL:number) {
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/reading']);
  }
  ListWriting(id:number, idL:number) {
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['book/'+id+'/lesson/'+idL+'/writing']);
  }


  sections = [
    // {
    //   img: 'https://www.vnjpclub.com/images/icon/tuvung.png',
    //   name: 'Phần 1: Từ vựng'
    // },
    {
      img: 'https://www.vnjpclub.com/images/icon/nguphap.png',
      name: 'Phần 2: Ngữ pháp'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/luyendoc.png',
      name: 'Phần 3: Luyện đọc'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/hoithoai.png',
      name: 'Phần 4: Hội thoại'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/luyennghe.png',
      name: 'Phần 5: Luyện nghe'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/baitap.png',
      name: 'Phần 6: Bài tập'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/hantu.png',
      name: 'Phần 7: Hán tự'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/dochieu.png',
      name: 'Phần 8: Đọc hiểu'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/kiemtra.png',
      name: 'Phần 9: Kiểm tra'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/thamkhao.png',
      name: 'Phần 10: Tham khảo'
    },
  ]


  // lessons = [
  //   {
  //     img: 'https://www.vnjpclub.com/images/icon/tuvung.png',
  //     name: '1'
  //   },
  //   {
  //     img: 'https://www.vnjpclub.com/images/icon/nguphap.png',
  //     name: '2'
  //   },
  //   {
  //     img: 'https://www.vnjpclub.com/images/icon/luyendoc.png',
  //     name: '3'
  //   },
  //   {
  //     img: 'https://www.vnjpclub.com/images/icon/hoithoai.png',
  //     name: '4'
  //   },
  //   {
  //     img: 'https://www.vnjpclub.com/images/icon/luyennghe.png',
  //     name: '5'
  //   },
  //   {
  //     img: 'https://www.vnjpclub.com/images/icon/baitap.png',
  //     name: '6'
  //   },
  //   {
  //     img: 'https://www.vnjpclub.com/images/icon/luyennghe.png',
  //     name: '7'
  //   },
  //   {
  //     img: 'https://www.vnjpclub.com/images/icon/baitap.png',
  //     name: '8'
  //   },
  // ]


}
