import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { ClubDetailsComponent } from './club-details/club-details.component';
import { ClubViewComponent } from './club-view/club-view.component';
import { ClubByCodeGuard } from './core/guards/club-by-code.guard';


const routes: Routes = [
    {
      path: '',
      component: ClubViewComponent,
    },
    {
        path: ':code',
        //canActivate: [ClubByCodeGuard],
        component: ClubDetailsComponent,
    },
    {
      path: '**',
      redirectTo: '',
      pathMatch: 'full'
    }

]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
export class AppRoutingModule { }
  