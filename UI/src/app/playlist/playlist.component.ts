import { Component, OnInit } from '@angular/core';
import { PlaylistService } from '../playlist.service';
import { UserService } from '../user.service';
import { environment } from "src/environments/environment";
import {SnackService} from '../snack.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {

  userPlaylists = [];
  nowPlaying: any = undefined;
  contents: any = undefined;
  s3link = environment.S3;

  constructor(private playlistService: PlaylistService,
              private userService: UserService,
              private snackService:SnackService) { }

  ngOnInit(): void {
    console.log("loading playlists");
    this.loadPlaylists();
  }

  loadPlaylists(){
    this.userService.getPlaylists().subscribe(res =>{
      console.log(res);
      if(res.status=='success'){
        this.userPlaylists = res.body;
      }
    });
  }

  loadContents(contentIds:number[]){
    this.playlistService.getContents(contentIds).subscribe(res =>{
      console.log(res);
      if(res.status=='success'){
        this.contents = res.body;
      }
    });
  }

  createPlaylist(name){
    this.playlistService.create(name, this.userService.user.userId).subscribe(res =>{
      console.log(res);
      if(res.status=='success'){
        this.userPlaylists.push(res.body);
        this.snackService.openSnackBar('Playlist created','');
      }
    });
  }

  deletePlaylist(playListId){
    this.playlistService.delete(playListId).subscribe(res =>{
      if(res && res.body){
        this.snackService.openSnackBar('Playlist deleted','');
        this.loadPlaylists();
      }
    });
  }

  playItem(content){
    // console.log(content, player);
    this.nowPlaying = content;
    // player.load();
  }

}
