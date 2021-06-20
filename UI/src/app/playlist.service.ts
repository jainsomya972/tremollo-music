import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {

  constructor(private http:HttpClient) { }

  getContents(contentIds: number[]){
    const params = new HttpParams()
    .set('contentId',contentIds.join(','));
    return this.http.get<any>(environment.DATA_API+'content',{params});
  }

  addContent(playlistId: number, contentId: number){
    return this.http.post<any>(environment.DATA_API+'playlist/add',{playlistId: playlistId, contentId: contentId});
  }

  create(name: string, userId: number){
    return this.http.post<any>(environment.DATA_API+'playlist/create',{playlistName: name, userId: userId});
  }

  delete(playlistId: number){
    return this.http.delete<any>(environment.DATA_API+'playlist?playlistId='+playlistId);
  }

}
