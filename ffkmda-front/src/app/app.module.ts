import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from './shared/shared.module';
import { NgxPaginationModule } from "ngx-pagination";
import { ClubViewComponent } from './club-view/club-view.component';
import { DrawerModule } from './shared/components/drawer/drawer.module';
import { AppRoutingModule } from './app-routing.module';
import { ClubDetailsComponent } from './club-details/club-details.component';

@NgModule({
  declarations: [
    AppComponent,
    ClubViewComponent,
    ClubDetailsComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    DrawerModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,    
    NgxPaginationModule,
    AppRoutingModule,
  ],
  providers: [],
  entryComponents: [
    ClubViewComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
