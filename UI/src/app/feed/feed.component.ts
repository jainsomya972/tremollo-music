import { Component, OnInit,Output,EventEmitter, Inject, ChangeDetectorRef } from '@angular/core';
import {Router} from '@angular/router';
import { ContentService } from '../content.service';
import { environment } from 'src/environments/environment';
import { UserService } from '../user.service';
import { MatBottomSheet, MatBottomSheetRef, MAT_BOTTOM_SHEET_DATA } from '@angular/material/bottom-sheet';
import { PlaylistService } from '../playlist.service';
import { SnackService } from '../snack.service';
import {MatDialog,MAT_DIALOG_DATA} from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  itemList: any = [];
  s3link = environment.S3;
  pageNo:number = 0;
  url:string;
  pageSize:number = 10;
  constructor(private contentService: ContentService, 
              private userService: UserService,
              private bottomSelector: MatBottomSheet,
              private router:Router,
              private snackService: SnackService,
              private dialog: MatDialog,
              private spinner: NgxSpinnerService,
              @Inject(DOCUMENT) document) { }

  @Output() visitUser = new EventEmitter<number>();
  nowplayingId:string = "";
  ngOnInit(): void {
    this.spinner.show();
    this.pageNo = 1;
    this.contentService.getFeed(this.pageNo,this.pageSize,this.userService.user.userId).subscribe((data)=>{
      if(data.body){
        for(let item of data.body)
          this.itemList.push(item);
        this.spinner.hide();
      }
    });
    console.log(this.itemList);
  }

  likeClick(contentId,isLiked,index){
    this.contentService.likeSwitch(this.userService.user.userId,contentId,!isLiked).subscribe((res)=>{
      if(res.status == 'success')
        this.itemList[index].isLikedByUser = res.body.liked;
        if(res.body.liked)
          this.itemList[index].likes++;
        else
          this.itemList[index].likes--;
    });
  }

  followClick(followedId,isFollowed,index){
    this.userService.followSwitch(followedId,!isFollowed).subscribe(res => {
      if(res.status == 'success'){
        for(let item of this.itemList){
          if(item.userId == followedId)
            item.isFollowedByUser = res.body.followed;
        }
      }
      if(res.body.followed == true)
        this.snackService.openSnackBar('You are now a fan of '+this.itemList[index].username,'');
      else if(res.body.followed == false)
        this.snackService.openSnackBar('You are no more a fan of '+this.itemList[index].username,'');
    });
  }
  copyurl:string;
  temp(contentId:number){
     this.copyurl = window.btoa(contentId.toString());
     return this.copyurl;
  }
  openDialog(contentId:number) {
    this.url = 'tremollo.co/content/' + window.btoa(contentId.toString());
    this.dialog.open(DialogElements,{data: {url: this.url}});
    // console.log(url);
  }

  openPlaylistBottomSheet(contentId: number){
    this.bottomSelector.open(BottomSelectorPlaylist, {data: {contentId: contentId}});
  }
  onplay(elementId:string) {
    if(elementId == this.nowplayingId) {
      return;
    }
    if(this.nowplayingId) {
      let nowplayingelement = <HTMLVideoElement>document.getElementById(this.nowplayingId);
      nowplayingelement.pause();
    }
    this.nowplayingId = elementId;
  }
  onScrolldown(){
    this.contentService.getFeed(this.pageNo+1,this.pageSize,this.userService.user.userId).subscribe((data)=>{
      if(data.body){
        for(let item of data.body)
          this.itemList.push(item);
      }
    });
    this.pageNo++;
  }

  navTocontent(contentId:number) {
    this.router.navigate(['/content/' + window.btoa(contentId.toString())])
  }

  navUser(userId:number) {
    this.router.navigate(['/user/' + window.btoa(userId.toString())])
  }
}

@Component({
  selector: 'dialog-elements',
  template: ` 
  <h1 mat-dialog-title style="font-size:20px;">Link copied to clipboard</h1>
  <div mat-dialog-content>
  <p style="color:#e91e63;">{{url}}</p>
  <p>Now you can share this link with your friends</p>
  </div>
  <div mat-dialog-actions>
    <button mat-button mat-dialog-close>Close</button>
  </div>
  `,
})

// <button mat-button [cdkCopyToClipboard] = "url">copy</button>
export class DialogElements {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
  public url:string;
  ngOnInit(): void{
    console.log(this.data);
    this.url = this.data.url;
    console.log(this.url);
      }
    };

@Component({
  selector: 'bottom-selector-playlist',
  templateUrl: './bottom-selector-playlist.component.html'
})
export class BottomSelectorPlaylist implements OnInit{

  playlists: any = [];

  constructor(private bottomSheetRef: MatBottomSheetRef<BottomSelectorPlaylist>,
              @Inject(MAT_BOTTOM_SHEET_DATA) public data: any,
              private userService: UserService,
              private playlistService: PlaylistService,
              private changeDetectorRef: ChangeDetectorRef){}
  
  ngOnInit(): void{
    console.log(this.data);
    this.userService.getPlaylists().subscribe(res =>{
      console.log(res);
      if(res.status=="success"){
        this.playlists = res.body;
        this.changeDetectorRef.detectChanges();
      }
    });
  }

  addContent(playlistId: number): void {
    this.playlistService.addContent(playlistId, this.data.contentId).subscribe(res =>{
      if(res.status=="success"){
        this.bottomSheetRef.dismiss();
        event.preventDefault();
      }
    });
  }

  createPlaylist(name: string){
    this.playlistService.create(name, this.userService.user.userId).subscribe(res =>{
      if(res.status=="success"){
        this.addContent(res.body.playlistId);
      }
    });
  }
}