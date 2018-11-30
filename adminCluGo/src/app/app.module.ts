import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LocationComponent } from './location/location.component';
import { HomeComponent } from './home/home.component';
import { NavComponent } from './nav/nav.component';
import { AdminComponent } from "./admin/admin.component";

const appRoutes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'admin', component: AdminComponent }, 
  { path: 'location', component: LocationComponent }, 
  { path: '**', component: HomeComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    LocationComponent,
    HomeComponent,
    NavComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(
      appRoutes,
      { useHash: true}
    )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
