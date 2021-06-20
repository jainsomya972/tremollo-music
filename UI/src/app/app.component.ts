import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { SnackService } from './snack.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'tremollo';
  path: string = "";

  constructor(private userService: UserService, 
              private router: Router,
              private route: ActivatedRoute,
              private snackService: SnackService){}

  ngOnInit(): void {
    console.log(screen.width);
    if(screen.width >= 1000)
      window.location.href = "http://tremollo.co";
    if(!this.userService.loadLocal())
      this.router.navigate(['/login']);
    else if(this.router.url == "")
      this.router.navigate(["feed"]);

    this.router.events.subscribe((event)=>{
      if(event instanceof NavigationEnd){
        // console.log("route change", event);
        this.path = event.url;
      }
    });
  }
  
  pageNo = 0;
  isSideNavOpened = false;
  pages = [
    {name: 'Home', title: 'tremollo', icon: ''},
    {name: 'Search', title: 'search', icon: ''},
    {name: 'Upload', title: 'upload', icon: ''},
    {name: 'Playlist', title: 'playlist', icon: ''},
    {name: 'Profile', title: 'profile', icon: ''},
    {name: 'Profile', title: 'visitprofile', icon: ''},
    {name: 'Edit profile', title: 'editprofile', icon: ''}
  ];

  visited_user:number;

  switchPage(pageNo){
    this.pageNo = pageNo;
  }

  visitUser($event)
  {
    this.visited_user = $event;
    this.switchPage(5);
  }

  // open:boolean = false;

  logout()
  {
    this.isSideNavOpened = false;
    this.userService.deleteLocal();
    this.router.navigate(['/login']);
    this.snackService.openSnackBar('Successfully logged out','');
  }
}
