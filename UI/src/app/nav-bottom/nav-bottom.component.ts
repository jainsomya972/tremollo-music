import { Component, OnInit, Output,EventEmitter } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-nav-bottom',
  templateUrl: './nav-bottom.component.html',
  styleUrls: ['./nav-bottom.component.css']
})
export class NavBottomComponent implements OnInit {

  constructor(private router: Router) { 
    this.router.events.subscribe((event)=>{
      if(event instanceof NavigationEnd){
        console.log("route change", event);
        this.path = event.url;
      }
    });
  }

  path: string = "/feed";

  ngOnInit(): void {
    
  }

}
