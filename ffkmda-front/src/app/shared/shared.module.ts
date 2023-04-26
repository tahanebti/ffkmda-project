import { NgModule } from "@angular/core"
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "./components/header/header.component";
import { SuggestionsHelperModule } from "./pipes/suggestion-helper.pipe";
import { PaginatorComponent } from './components/paginator/paginator.component';
import { ClubCardLoaderComponent } from './components/club-card-loader/club-card-loader.component';
import { DrawerModule } from "./components/drawer/drawer.module";


export const components = [
  HeaderComponent,
  PaginatorComponent,
  ClubCardLoaderComponent,

]
  
  export const pipes = [
   SuggestionsHelperModule
  ]

  export const modules = [
  
]
  

@NgModule({
    declarations: [
      ...components,
    ],
    exports: [
      ...components,
      ...modules,
      ...pipes
    ],
    imports: [
      CommonModule,
      RouterModule,
      ...modules,
      ...pipes,
    ]
  })
  export class SharedModule { }