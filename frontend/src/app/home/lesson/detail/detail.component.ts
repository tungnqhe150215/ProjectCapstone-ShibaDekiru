import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { StudentLessonService } from '../student-lesson.service';
import { Book } from 'src/app/core/models/book';
import { Lesson } from 'src/app/core/models/lesson';

@Component({
  selector: 'app-lesson-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css'],
})
export class DetailComponent implements OnInit {

  id!: number;
  lessonN: Lesson = new Lesson;
  lesson: Lesson[] =[];
  book: Book[]= [];
  constructor(
    private studentLessonService: StudentLessonService,
    private router: Router,
    private route:ActivatedRoute,
  ) {}


  ngOnInit(): void {
    this.getLessonByBookID();
    this.getLessonById();
  } 

  getLessonById(){
    // this.id = this.route.snapshot.params['id'];
    const idLesson = this.studentLessonService.getLessonID();
    this.lessonN = new Lesson();
    this.studentLessonService.getLessonById(idLesson)
    .subscribe(
      data =>{
        this.lessonN = data
        console.log(data)
      }
    )
  }
  
  
  getLessonByBookID(){
    // this.id = this.route.snapshot.params['id'];
    const  idBook = this.studentLessonService.getBookId();
    this.lesson = [];
    this.studentLessonService.getLessonByBook(idBook).subscribe({
      next:(res) =>{
        this.lesson = res;
        // this.dataSource = new MatTableDataSource(res);
        // this.dataSource.paginator = this.paginator;
        console.log(res)
      },
    })
  }

  LessonDetail(id:number){
    // const idLesson = this.studentLessonService.getLessonID();
    this.studentLessonService.setLessonID(id);
    this.router.navigate(['./lesson/'+id+'/detail']);
    this.getLessonById();
  }

  ListVocab(id:number){
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['./lesson/'+idLesson+'/vocabulary']);
  }
  ListKanji(id:number){
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['./lesson/'+idLesson+'/kanji']);
  }

  ListGrammar(id:number){
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['./lesson/'+idLesson+'/grammar']);
  }
  ListKaiwa(id:number){
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['./lesson/'+idLesson+'/kaiwa']);
  }
  ListListening(id:number){
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['./lesson/'+idLesson+'/listening']);
  }
  ListReading(id:number){
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['./lesson/'+idLesson+'/reading']);
  }
  ListWriting(id:number){
    const idLesson = this.studentLessonService.getLessonID();
    this.router.navigate(['./lesson/'+idLesson+'/writing']);
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
