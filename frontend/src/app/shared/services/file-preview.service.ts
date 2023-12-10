import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FilePreviewService {

  private previewSource = new BehaviorSubject<string | ArrayBuffer | null>(null);
  currentPreview = this.previewSource.asObservable();

  changePreview(preview: string | ArrayBuffer | null) {
    this.previewSource.next(preview);
  }
}
