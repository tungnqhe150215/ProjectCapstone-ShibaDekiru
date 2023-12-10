import {Component, OnInit} from '@angular/core';
import {FilePreviewService} from "../../services/file-preview.service";

@Component({
  selector: 'app-file-preview',
  templateUrl: './file-preview.component.html',
  styleUrls: ['./file-preview.component.css']
})
export class FilePreviewComponent implements OnInit{
  previewUrl: string | ArrayBuffer | null = null;
  isImage: boolean | undefined = false;
  isAudio: boolean | undefined = false;

  constructor(private filePreviewService: FilePreviewService) {}

  ngOnInit() {
    this.filePreviewService.currentPreview.subscribe((preview) => {
      this.previewUrl = preview;
      this.detectFileType();
    });
  }

  private detectFileType() {
    // Simple file type detection based on the URL
    this.isImage = this.previewUrl?.toString().includes('data:image');
    this.isAudio = this.previewUrl?.toString().includes('data:audio');
  }
}
