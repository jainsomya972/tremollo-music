import { Component,Output,EventEmitter, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { environment } from 'src/environments/environment';
import {SnackService} from '../snack.service';
import {Router } from '@angular/router';
import {userTypes} from '../constants';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: any;
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
    private router: Router,
    private snackService:SnackService,
    private spinner: NgxSpinnerService,) { }
    
    @Output() edit = new EventEmitter<boolean>(); 

  editprofile()
  {
    this.edit.emit(true);
  }

  ngOnInit(): void {
    this.spinner.show();
    this.user = this.userService.user;
    this.Type = this.user.type;
    this.userType = this.types[this.Type];
    // console.log(this.userType[1]);
    console.log(this.user);
    this.loadStats();
    this.loadContent();
    this.loadFollowers();
    this.loadFollowing();
    this.spinner.hide();
  }

  loadStats(){
    this.userService.getstats().subscribe(res =>{
      if(res.status=='success'){
        this.stats.followers = res.body.followers;
        this.stats.following = res.body.following;
        this.stats.uploads = res.body.uploads;
      }
    });
  }

  loadContent(){
    this.userService.getUploads().subscribe(res => {
      if(res.status=='success'){
        this.uploads = res.body;
      }
    });
  }

  loadFollowers(){
    this.userService.getFollowers().subscribe(res => {
      if(res.status=='success')
        this.followers = res.body;
        console.log(this.followers);
    });
  }

  loadFollowing(){
    this.userService.getFollowing().subscribe(res => {
      if(res.status=='success')
        console.log("fan of : ",res.body);
        this.following = res.body;
    });
  }

  uploadAvatar(event){
    let file = event.target.files[0];
    this.userService.uploadAvatar(file).subscribe(res=>{
      console.log(res);
      if(res.status=="success"){
        this.user.avatarLink = this.userService.user.avatarLink = res.body.avatarLink;
        this.snackService.openSnackBar('profile pic updated','');
      }
    });
  }

  navTouser(userId:number) {
    this.router.navigate(['/user/' + window.btoa(userId.toString())]);
    this.ngOnInit();
  }

  navTocontent(contentId:number) {
    this.router.navigate(['/content/'+ window.btoa(contentId.toString())]);
  }

}
