import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  user: any = null;
  private headers = new HttpHeaders();


  constructor(private http: HttpClient) { 
    this.headers.set("Content-Type","application/json");
  }

  login(body): any{
    return this.http.post<any>(environment.DATA_API+'user/login',body);
  }

  signup(body): any{
    return this.http.post<any>(environment.DATA_API+'user/create',body);
  }

  all(): any{
    this.http.get(environment.DATA_API+'user/all').subscribe((data)=>{
      console.log(data);
    });
  }

  getById(userId: number): any{
    let params = new HttpParams().set('userId',userId.toString());
    return this.http.get<any>(environment.DATA_API+'user',{params});
  }

  getStatsById(userId: number): any{
    let params = new HttpParams().set('userId',userId.toString());
    return this.http.get<any>(environment.DATA_API+'user/stats',{params});
  }

  getUploadsById(userId: number): any{
    let params = new HttpParams()
    .set('userId', userId.toString());
    return this.http.get<any>(environment.DATA_API+'user/content',{params});
  }

  getstats(){
    let params = new HttpParams()
    .set('userId',this.user.userId);
    return this.http.get<any>(environment.DATA_API+'user/stats',{params});
  }

  getUploads(){
    let params = new HttpParams()
    .set('userId', this.user.userId);
    return this.http.get<any>(environment.DATA_API+'user/content',{params});
  }

  getPlaylists(){
    let params = new HttpParams()
    .set('userId', this.user.userId);
    return this.http.get<any>(environment.DATA_API+'user/playlist',{params});
  }

  getFollowers(){
    let params = new HttpParams()
    .set('userId', this.user.userId);
    return this.http.get<any>(environment.DATA_API+'user/followers',{params});
  }

  getFollowing(){
    let params = new HttpParams()
    .set('userId', this.user.userId);
    return this.http.get<any>(environment.DATA_API+'user/following',{params});
  }
  getFollowersById(userId:number){
    let params = new HttpParams()
    .set('userId', userId.toString());
    return this.http.get<any>(environment.DATA_API+'user/followers',{params});
  }

  getFollowingById(userId:number){
    let params = new HttpParams()
    .set('userId', userId.toString());
    return this.http.get<any>(environment.DATA_API+'user/following',{params});
  }

  followSwitch(followedId: number, follow: boolean){
    let req = {
      followerId: this.user.userId,
      followedId: followedId,
      follow: follow
    }
    return this.http.put<any>(environment.DATA_API+'user/follow',req);
  }

  search(keyword: string){
    let params = new HttpParams()
    .set('keyword',keyword);
    return this.http.get<any>(environment.DATA_API+'user/search',{params});
  }

  uploadAvatar(filePath:any){
    let formData = new FormData();
    formData.append('file',filePath);
    formData.append('userId',this.user.userId);
    return this.http.put<any>(environment.DATA_API+'user/avatar',formData);
  }

  saveLocal(){
    localStorage.setItem("user",JSON.stringify(this.user));
  }

  deleteLocal()
  {
    localStorage.clear();
  }
  
  loadLocal(){
    this.user = JSON.parse(localStorage.getItem("user"));
    console.log(this.user);
    if(!this.user)
      return false;
    else
      return true;
  }
}
