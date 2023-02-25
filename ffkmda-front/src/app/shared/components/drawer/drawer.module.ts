import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DrawerRoutingModule } from './drawer-routing.module';
import { DrawerComponent } from './drawer.component';
import { DrawerService } from './drawer.service';
import { DrawerContainerDirective } from './drawer-container.directive';


@NgModule({
  declarations: [
    DrawerComponent, DrawerContainerDirective
  ],
  exports: [DrawerComponent, DrawerContainerDirective],
  imports: [CommonModule],
  entryComponents: [DrawerComponent],
  providers: [DrawerService]
})
export class DrawerModule { }
