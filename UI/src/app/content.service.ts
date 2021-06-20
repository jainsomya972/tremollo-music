import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpRequest, HttpEventType } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class ContentService {

  s3link = environment.S3;

  constructor(private http: HttpClient, private userService: UserService) { }

  getFeed(page, rows, userId){
    let params = new HttpParams()
    .set('pageNumber',page)
    .set("rowCount",rows)
    .set('userId',userId);
    console.log(params.toString());
    return this.http.get<any>(environment.DATA_API+'feed',{params});
  }

  getContent(contentId: number){
    let params = new HttpParams()
    .set('contentId',contentId.toString())
    .set('userId',this.userService.user.userId);
    return this.http.get<any>(environment.DATA_API+'content/withUser',{params});
  }

  getLikes(contentId:number) {
    let params = new HttpParams()
    .set('contentId',contentId.toString())
    return this.http.get<any>(environment.DATA_API+'content/likes',{params});
  }

  getComments(contentId:number) {
    let params = new HttpParams()
    .set('contentId',contentId.toString())
    return this.http.get<any>(environment.DATA_API+'content/comments',{params});
  }

  postComment(comment){
    return this.http.post<any>(environment.DATA_API+'content/comments', comment);
  }

  uploadFile(formData){
    const req = new HttpRequest('PUT',environment.DATA_API+'content/upload/file', formData, {
      reportProgress: true
    });
    
    return this.http.request(req).pipe(
      map((event) => {
        switch(event.type){
          case HttpEventType.UploadProgress:
            const progress = Math.round(100*event.loaded/event.total);
            return {status: 'progress', message: progress};
          case HttpEventType.Response:
            return {status: 'success', message: event.body};
          default:
            return {status: 'unhandled', message: event.type};
        }
      })
    );
  }

  uploadContent(content){
    return this.http.post<any>(environment.DATA_API+'content/upload/data', content);
  }

  likeSwitch(userId,contentId,like){
    const body = {
      userId: userId,
      contentId: contentId,
      like: like
    }
    return this.http.put<any>(environment.DATA_API+'content/like', body);
  }

  search(keyword: string){
    let params = new HttpParams()
    .set('keyword',keyword);
    return this.http.get<any>(environment.DATA_API+'content/search', {params});
  }
}