import { Component,Output,EventEmitter, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import {SnackService} from '../snack.service';
@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  constructor(private userService: UserService,
    private router: Router,
    private snackService:SnackService) { }
  
  User = this.userService.user;

  @Output() edited = new EventEmitter<boolean>(); 

  ngOnInit(): void {
  }

  editClick(){
    this.userService.signup(this.User).subscribe((res)=>{
      console.log(res);
      if(res.status=="success"){
        this.userService.user = res.body;
        this.userService.saveLocal();
        this.edited.emit(true);
        this.snackService.openSnackBar('Information updated','');
        this.router.navigate(['/profile']);
      }
      else
        alert("Can't edit! Make sure you have entered all the details");
    });
    // console.log(this.User);
  }

  dateAdapter(event): void{
    let d: Date = new Date(event.value);
    let date: string = d.getFullYear()+'-';
    if(d.getMonth()<10) date+='0';
    date+=d.getMonth()+'-';
    if(d.getDay()<10) date+='0';
    date+=d.getDay();
    this.User.dateOfBirth = date;
    // console.log(d, date);

  }
}
