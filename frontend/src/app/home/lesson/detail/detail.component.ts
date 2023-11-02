import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-lesson-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css'],
})
export class DetailComponent implements OnInit {
  constructor() {
  }

  sections = [
    {
      img: 'https://www.vnjpclub.com/images/icon/tuvung.png',
      name: 'Phần 1: Từ vựng'
    },
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

  lessons = [
    {
      img: 'https://www.vnjpclub.com/images/icon/tuvung.png',
      name: '1'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/nguphap.png',
      name: '2'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/luyendoc.png',
      name: '3'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/hoithoai.png',
      name: '4'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/luyennghe.png',
      name: '5'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/baitap.png',
      name: '6'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/luyennghe.png',
      name: '7'
    },
    {
      img: 'https://www.vnjpclub.com/images/icon/baitap.png',
      name: '8'
    },
  ]

  ngOnInit(): void {
  }
}
