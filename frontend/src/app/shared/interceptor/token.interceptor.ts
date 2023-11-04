import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // Lấy token từ nơi bạn lưu trữ nó (ví dụ: localStorage, sessionStorage)
    const userString = window.sessionStorage.getItem('auth-user');

    if (userString !== null) {
      // Chuyển đổi giá trị của trường 'user' từ chuỗi JSON thành đối tượng JavaScript
      const user = JSON.parse(userString);

      // Kiểm tra xem 'token' có trong 'user' không
      if (user.access_token) {
        // Lấy giá trị của trường 'token'
        const token = user.access_token;
        console.log(`Token: ${token}`);
        if (token) {
          // Sao chép yêu cầu và thêm token vào tiêu đề Authorization
          const modifiedReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${token}`,
            }
          });
          // Chuyển yêu cầu đã được thay đổi
          return next.handle(modifiedReq);
        } else {
          // Nếu không có token, chuyển yêu cầu gốc
          return next.handle(req);
        }
      } else {
        console.log("Trường 'token' không tồn tại trong 'user'.");
      }
    } else {
      console.log("Trường 'user' không tồn tại trong sessionStorage.");
    }
    return next.handle(req);
  }
}
