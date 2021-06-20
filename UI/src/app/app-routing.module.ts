import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component'
import { SignupComponent } from './signup/signup.component';
import { AppComponent } from './app.component';
// import { AppInsideComponent } from './app-inside/app-inside.component';
import { VisitprofileComponent } from './visitprofile/visitprofile.component';
import { FeedComponent } from './feed/feed.component';
import { SearchComponent } from './search/search.component';
import { PostComponent } from './post/post.component';
import { PlaylistComponent } from './playlist/playlist.component';
import { ProfileComponent } from './profile/profile.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ContentComponent } from './content/content.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { AboutUsComponent } from './about-us/about-us.component';


const routes: Routes = [
  {path : '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  // {path: 'app', component: AppInsideComponent},
  {path: 'feed', component: FeedComponent},
  {path: 'search', component: SearchComponent},
  {path: 'upload', component: PostComponent},
  {path: 'playlists', component: PlaylistComponent},
  {path: 'user/:id', component: VisitprofileComponent},
  {path: 'content/:id', component: ContentComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'profile/edit', component: EditProfileComponent},
  {path: 'feedback', component: FeedbackComponent},
  {path: 'about-us', component: AboutUsComponent},
  {path: '**', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
