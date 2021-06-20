import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { HttpClientModule } from "@angular/common/http";
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
// import { NavTopComponent } from './nav-top/nav-top.component';
import { NavBottomComponent } from './nav-bottom/nav-bottom.component';
// import { AppInsideComponent } from './app-inside/app-inside.component';
import { FeedComponent, BottomSelectorPlaylist,DialogElements } from './feed/feed.component';
import { SearchComponent } from './search/search.component';
import { PostComponent } from './post/post.component';
import { PlaylistComponent } from './playlist/playlist.component';
import { ProfileComponent } from './profile/profile.component';
import { TimeagoModule } from "ngx-timeago";
import { NgxSpinnerModule } from "ngx-spinner";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ClipboardModule} from '@angular/cdk/clipboard'; 

import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { MatCardModule } from "@angular/material/card";
import { MatListModule, MatNavList } from "@angular/material/list";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatBottomSheetModule } from "@angular/material/bottom-sheet";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatTabsModule } from "@angular/material/tabs";
import {MatDialogModule} from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';


import { CommonModule } from '@angular/common';
import { VisitprofileComponent } from './visitprofile/visitprofile.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { MatNativeDateModule } from '@angular/material/core';
import { ContentComponent } from './content/content.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { AboutUsComponent } from './about-us/about-us.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    // NavTopComponent,
    NavBottomComponent,
    DialogElements,
    // AppInsideComponent,
    FeedComponent,
    SearchComponent,
    PostComponent,
    PlaylistComponent,
    ProfileComponent,
    BottomSelectorPlaylist,
    VisitprofileComponent,
    EditProfileComponent,
    ContentComponent,
    FeedbackComponent,
    AboutUsComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    ClipboardModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgxSpinnerModule,
    BrowserAnimationsModule,
    InfiniteScrollModule,
    TimeagoModule.forRoot(),
    
    MatCardModule,
    MatListModule,
    MatDialogModule,
    MatExpansionModule,
    MatToolbarModule,
    MatBottomSheetModule,
    MatChipsModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatSelectModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSidenavModule,
    MatTabsModule
  ],
  providers: [MatDatepickerModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
