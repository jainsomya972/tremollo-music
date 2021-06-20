import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  page = 0;
  model = {
    firstName: undefined,
    lastName: undefined,
    username: undefined,
    email: undefined,
    password: undefined,

    about: undefined,
    gender: 'M',
    dateOfBirth: undefined,
    type: 1,
    followersCount: 0
  }
  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  signupClick(){
    console.log(this.model);
    this.userService.signup(this.model).subscribe((res)=>{
      console.log(res);
      if(res.status=="success"){
        this.userService.user = res.body;
        this.userService.saveLocal();
        this.router.navigate(['/feed']);
      }
      else
        alert("Can't signup! Make sure you have entered all the details");
    });
  }

  dateAdapter(event): void{
    let d: Date = new Date(event.value);
    let date: string = d.getFullYear()+'-';
    if(d.getMonth()<10) date+='0';
    date+=d.getMonth()+'-';
    if(d.getDay()<10) date+='0';
    date+=d.getDay();
    this.model.dateOfBirth = date;
    // console.log(d, date);

  }
}
