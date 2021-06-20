import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { NgModel,NgForm } from "@angular/forms";
import { Router } from '@angular/router';
import {SnackService} from '../snack.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model = {
    email: "",
    password: ""
  }
  error = false;
  isLandingPage:boolean = true;

  carousal = [
    {image: 'assets/image/carousal1.svg'},
    {image: 'assets/image/carousal2.svg'},
    {image: 'assets/image/carousal3.svg'},
    {image: 'assets/image/carousal4.svg'},
    {image: 'assets/image/carousal5.svg'},
    {image: 'assets/image/carousal6.svg'}
  ]

  constructor(private userService: UserService,
              private router: Router,
              private snackService:SnackService) { }

  ngOnInit(): void {
    this.isLandingPage = true;
  }

  loginClick(){
    this.userService.login(this.model).subscribe((data)=>{
      if(!data.body){
        this.error = true;
      }
      else{
        this.error = false;
        this.userService.user = data.body;
        this.userService.saveLocal();
        this.router.navigate(['/feed']);
        this.snackService.openSnackBar('Successfully logged in','');

      }
    });
    // if(!this.error)
    //   this.router.navigate(['/app']);
  }
  switchlanding() {
    this.isLandingPage = false;
  }



}
