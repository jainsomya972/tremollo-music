import { Component,Input, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { environment } from 'src/environments/environment';
import { ContentService } from '../content.service';
import { ActivatedRoute,Router } from '@angular/router';
import { SnackService } from '../snack.service';
import {userTypes} from '../constants';

@Component({
  selector: 'app-visitprofile',
  templateUrl: './visitprofile.component.html',
  styleUrls: ['./visitprofile.component.css']
})
export class VisitprofileComponent implements OnInit {

  user: any;
  visited_user:any;

  s3link = environment.S3;
  stats = {
    followers: '-',
    following: '-',
    uploads: '-'
  }
  uploads: any = [];
  followers: any = [];
  following: any = [];

  types = userTypes;
  Type:number;
  userType:any;

  constructor(private userService: UserService,
              private contentService: ContentService,
              private route: ActivatedRoute,
              private router: Router,
              private snackService: SnackService) { }

  profile_id:number;
  isFollowing:boolean = false;

  ngOnInit(): void {
    this.profile_id = this.route.snapshot.params['id'];
    this.profile_id = parseInt(window.atob(this.profile_id.toString()));
    this.user = this.userService.user;
    this.userService.getById(this.profile_id).subscribe(res =>{
      if(res.status == 'success'){
        this.visited_user = res.body;
        this.Type = this.visited_user.type;
        this.userType = this.types[this.Type];
        // console.log(this.userType);
      }
    });
    this.loadStats();
    this.loadContent();
    this.loadFollowers();
    this.loadFollowing();
  }

  loadStats(){
    this.userService.getStatsById(this.profile_id).subscribe(res =>{
      if(res.status=='success'){
        this.stats.followers = res.body.followers;
        this.stats.following = res.body.following;
        this.stats.uploads = res.body.uploads;
      }
    });
  }

  loadContent(){
    this.userService.getUploadsById(this.profile_id).subscribe(res => {
      if(res.status=='success'){
        this.uploads = res.body;
      }
    });
  }

  loadFollowers(){
    this.userService.getFollowersById(this.profile_id).subscribe(res => {
      if(res.status=='success'){
            this.followers = res.body;
            for(var followingUser of this.followers){
              if(this.user.userId == followingUser.userId){
              this.isFollowing = true;
              console.log(this.isFollowing);
              console.log("fans are : ",res.body);
          }
      }
    }
    });
  }

  loadFollowing(){
    this.userService.getFollowingById(this.profile_id).subscribe(res => {
      if(res.status=='success')
        console.log("fan of : ",res.body);
        this.following = res.body;
    });
  }

  followClick(){
    this.userService.followSwitch(this.profile_id,!this.isFollowing).subscribe(res => {
      if(res.status == 'success'){
            this.visited_user.isFollowedByUser = res.body.followed;
            if(this.isFollowing == true){
              this.isFollowing = false;
            }
            else{
              this.isFollowing = true;
            }
            this.loadFollowers();
            this.loadFollowing();
            this.loadStats();
      }
      if(res.body.followed == true)
        this.snackService.openSnackBar('You are now a fan of '+this.visited_user.username,'');
      else if(res.body.followed == false)
        this.snackService.openSnackBar('You are no more a fan of '+this.visited_user.username,'');
    });
  }

  navTouser(userId:number) {
    this.router.navigate(['/user/' + window.btoa(userId.toString())]);
    this.profile_id = userId;
    this.user = this.userService.user;
    this.userService.getById(userId).subscribe(res =>{
      if(res.status == 'success'){
        this.visited_user = res.body;
      }
    });
    this.loadStats();
    this.loadContent();
    this.loadFollowers();
    this.loadFollowing();
  }

  navTocontent(contentId:number) {
    this.router.navigate(['/content/'+ window.btoa(contentId.toString())]);
  }

}
