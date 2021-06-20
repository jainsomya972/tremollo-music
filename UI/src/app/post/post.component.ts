import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ContentService } from '../content.service';
import { environment } from "src/environments/environment";
import { UserService } from '../user.service';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';
import {SnackService} from '../snack.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  Tags: string[] = [];
  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) 
    {
      this.Tags.push(value);
    }

    if (input) 
    {
      input.value = '';
    }
  }

  remove(tag: string): void 
  {
    const index = this.Tags.indexOf(tag);

    if (index >= 0) 
    {
      this.Tags.splice(index, 1);
    }
  }



  @Output() posted = new EventEmitter<boolean>(); 
  s3link = environment.S3;
  file: any = null;
  loadMessage: string = "loading...";
  fileUploadResponse: any = undefined;
  thumbnails: any[] = [];
  model = {
    title: undefined,
    caption: undefined,
    fileId: undefined,
    userId: undefined,
    tags: undefined,
    thumbnailLink: undefined
  }

  constructor(private contentService: ContentService, 
    private userService: UserService,
    private snackService:SnackService,
    private spinner: NgxSpinnerService,
    private router: Router) { }

  ngOnInit(): void {
  }


  onFileSelect(event){
    this.file = event.target.files[0];
    console.log(this.file);
    if(this.file.size>121*1024*1024)
      alert("File too big, please select video of size less than 120MB");
    else{
      const formData = new FormData();
      formData.append('file',this.file);
      this.spinner.show();
      this.contentService.uploadFile(formData).subscribe((res) => {
        console.log(res);
        if(res.status == 'progress')
          this.loadMessage = `uploading... (${res.message}%)`;
        else if(res.status == 'success' && (<any>(res.message)).status == 'success'){
          this.loadMessage = "loading";
          this.spinner.hide();
          this.fileUploadResponse = (<any>(res.message)).body;
          this.thumbnails = this.fileUploadResponse.fileThumbnails.split(",");
          this.thumbnails.pop();
          this.model.thumbnailLink = this.thumbnails[0];
          console.log(this.fileUploadResponse,this.thumbnails,this.model.thumbnailLink);
        }
        else
          this.loadMessage = "processing...";
      });
    }
  }

  contentUpload(){
    this.model.userId = this.userService.user.userId;
    this.model.fileId = this.fileUploadResponse.fileId;
    this.model.tags = this.Tags.join();
    console.log(this.model);
    this.contentService.uploadContent(this.model).subscribe((res)=>{
      console.log(res);
      if(res.status == "success"){
        this.snackService.openSnackBar('File uploaded successfully','');
        this.router.navigate(['/feed']);
        this.posted.emit(true);
      }
    });
  }

  switchThumbnail(link: string){
    this.model.thumbnailLink = link;
  }

}
