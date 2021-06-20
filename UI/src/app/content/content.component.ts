import { Component, OnInit } from '@angular/core';
import { ContentService } from '../content.service';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { BottomSelectorPlaylist } from '../feed/feed.component';
import { DialogElements } from '../feed/feed.component';
import { SnackService } from '../snack.service';
import { UserService } from '../user.service';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  content: any = undefined;
  contentId: number;
  s3link = environment.S3;
  likers:any = [];
  comments:any = [];
  user: any;
  url:string;

  constructor(private contentService: ContentService,
              private route: ActivatedRoute,
              private bottomSelector: MatBottomSheet,
              private snackService: SnackService,
              private userService: UserService,
              private dialog: MatDialog,
              private router: Router,
            ) { }

  ngOnInit(): void {
    this.contentId = this.route.snapshot.params['id'];
    this.contentId = parseInt(window.atob(this.contentId.toString()));
    this.user = this.userService.user;
    this.loadContent(this.contentId);
    this.getlikers();
    this.getcomments();
  }

  comment = {
    contentId:this.contentId,
    userId:this.userService.user.userId,
    text:"",
  }

  loadContent(contentId){
    this.contentService.getContent(contentId).subscribe(res => {
      if(res.status == "success")
        this.content = res.body;
        // console.log(this.content);
    });
  }

  openPlaylistBottomSheet(contentId: number){
    this.bottomSelector.open(BottomSelectorPlaylist, {data: {contentId: contentId}});
  }

  likeClick(contentId,isLiked){
    this.contentService.likeSwitch(this.userService.user.userId,contentId,!isLiked).subscribe((res)=>{
      if(res.status == 'success')
        this.content.isLikedByUser = res.body.liked;
        this.getlikers();
        if(res.body.liked)
          this.content.likes++;
        else
          this.content.likes--;
    });
  }

  followClick(followedId,isFollowed){
    this.userService.followSwitch(followedId,!isFollowed).subscribe(res => {
      if(res.status == 'success')
        this.content.isFollowedByUser = res.body.followed;
      if(res.body.followed == true)
        this.snackService.openSnackBar('You are now a fan of '+this.content.username,'');
      else if(res.body.followed == false)
        this.snackService.openSnackBar('You are no more a fan of '+this.content.username,'');
    });
  }

  getlikers() {
    this.contentService.getLikes(this.contentId).subscribe(res => {
      if(res.status=='success')
        this.likers = res.body;
        // console.log(this.likers);
    });
  }

  getcomments() {
    this.contentService.getComments(this.contentId).subscribe(res => {
      if(res.status=='success')
        this.comments = res.body;
        console.log(this.comments);
    });
  }

  postComment() {
    this.comment.contentId = this.contentId;
    this.contentService.postComment(this.comment).subscribe(res => {
      if(res.status=='success')
        this.getcomments();
        console.log(this.comment);
        this.comment.text = ""; 
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

  navTouser(userid:number) {
    this.router.navigate(['/user/'+ window.btoa(userid.toString())]);
  }
}
