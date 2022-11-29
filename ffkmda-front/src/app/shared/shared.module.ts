import { NgModule } from "@angular/core"
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "./components/header/header.component";
import { SuggestionsHelperModule } from "./pipes/suggestion-helper.pipe";
import { PaginatorComponent } from './components/paginator/paginator.component';
import { ClubCardLoaderComponent } from './components/club-card-loader/club-card-loader.component';


export const components = [
  HeaderComponent,
  PaginatorComponent,
  ClubCardLoaderComponent,

]
  
  export const pipes = [
   SuggestionsHelperModule
  ]
  

@NgModule({
    declarations: [
      ...components,
      
     
    ],
    exports: [
      ...components,
      
    ],
    imports: [
      CommonModule,
      RouterModule,
      ...pipes,
    ]
  })
  export class SharedModule { }