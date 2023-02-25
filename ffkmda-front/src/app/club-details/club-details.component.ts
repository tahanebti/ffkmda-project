import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ExtranetService } from '../core/service/extranet.service';
import { fadeAnimation } from '../shared/animations/fade.animation';

@Component({
  selector: 'app-club-details',
  templateUrl: './club-details.component.html',
  styleUrls: ['./club-details.component.css'],
  animations: [fadeAnimation]
})
export class ClubDetailsComponent implements OnInit {

  club : any
  constructor(
    private _extrantService: ExtranetService,
    private route: ActivatedRoute
    ) { }

  ngOnInit(): void {

    let code = this.route.snapshot.paramMap.get('code')

    this._extrantService.getOne(code).subscribe(resp => {
      console.log(resp)
      this.club = resp.data
    })

  }

}
