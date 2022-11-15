import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-club-card-loader',
  templateUrl: './club-card-loader.component.html',
  styleUrls: ['./club-card-loader.component.scss']
})
export class ClubCardLoaderComponent {

  @Input() direction?: 'vertical' | 'horizontal';
  

}
