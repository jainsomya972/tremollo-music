import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from '../user.service';
import { ContentService } from '../content.service';
import { environment } from "src/environments/environment";
import { Router } from '@angular/router';
import { Key } from 'protractor';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  userSearchResult = [];
  contentSearchResult = [];
  ifResult:boolean = false;
  s3link = environment.S3;


  constructor(private userService: UserService,
              private contentService: ContentService,
              private router: Router
            ) { }

  ngOnInit(): void {
  }

  searchSubmit(keyword: string, event: KeyboardEvent){
    // if(event.key != Key.ENTER)
    // keyword+=event.key;
    console.log(event);
    this.userService.search(keyword).subscribe(res=>{
      if(res.status=="success"){
        this.ifResult=true;
        this.userSearchResult = res.body;
        // console.log(this.userSearchResult);
      }
    });

    this.contentService.search(keyword).subscribe(res=>{
      if(res.status=="success"){
        this.ifResult = true;
        this.contentSearchResult = res.body;
        // console.log(this.contentSearchResult);
      }
    });
  }

  visitUser(userId:number){
    this.router.navigate(['/user', window.btoa(userId.toString())]);
  }

  navTocontent(contentId:number) {
    this.router.navigate(['/content/'+ window.btoa(contentId.toString())]);
  }
}
