import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';
import { tap, filter, take, switchMap, catchError } from 'rxjs/operators';
import { ExtranetService } from '../service/extranet.service';


@Injectable({
  providedIn: 'root'
})
export class ClubByCodeGuard implements CanActivate {
  constructor(private _clubService: ExtranetService) {}

  canActivate(next: ActivatedRouteSnapshot): Observable<boolean> {
    const clubCode: any = next.paramMap.get('code');
    
    return this._setSelectedClubIfExistElseRedirect(clubCode).pipe(
      switchMap(() => of(true)),
      catchError(() => of(false))
    );
  }

  private _setSelectedClubIfExistElseRedirect(clubCode: any): Observable<any> {
    return this._clubService.getOne(clubCode).pipe(
      tap((club: any) => {
        if (!club || club.id !== club) {
          console.log("club by code", club)
        }
      }),
      filter((club: any) => !!club),
      take(1),
    );
  }  
}