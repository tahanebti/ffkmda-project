import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forkJoin, map, Observable } from 'rxjs';
import { FilterQuery, IPageable, Page, PageableSearch } from '../models/pageable.model';
import { isEmpty } from 'lodash';


const httOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
}

export interface AutoCompleteResult {
  clubs: AutoCompleteData[];
  sieges: AutoCompleteData[];
}

export interface AutoCompleteData {
  _id: string;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class ExtranetService {

  constructor(private _http: HttpClient) { }



  searchByCommune(query?: any, page?: PageableSearch): Observable<Page<any>> {

    const params: {} = !page ? {} : {
      page: page.pageable?.pageNumber,
      size: page.pageable?.pageSize
    }

    return this._http.get<Page<any>>(`http://prod-club.ffkmda.fr:9007/api/v1/clubs/address?commune=${query}`, { params: params })
  }

  searchByCodePostal(query?: any, page?: PageableSearch): Observable<Page<any>> {

    const params: {} = !page ? {} : {
      page: page.pageable?.pageNumber || 1,
      size: page.pageable?.pageSize || 1
    }


    return this._http.get<Page<any>>(`http://prod-club.ffkmda.fr:9007/api/v1/clubs/address?code_postal_fr=${query}`, { params: params })
  }


  searchByDep(query: any, page?: PageableSearch): Observable<any> {

    const params: {} = !page ? {} : {
      page: page.pageable?.pageNumber,
      size: page.pageable?.pageSize
    }

    return this._http.get<Page<any>>(`http://prod-club.ffkmda.fr:9007/api/v1/clubs/address?code_insee_departement=${query}`, { params: params })
  }

  getAutoComplete(query?: any): Observable<any> {
    return forkJoin([
      this.searchByCodePostal(query),
      this.searchByCommune(query),
      this.searchByDep(query),
    ]).pipe(
      map(([clubs, commune, dep]) => ({
        clubs,
        commune,
        dep
      })
      )
    )
  }


  //http://prod-club.ffkmda.fr:9007/api/v1/clubs/address?code_insee_departement





  public contsructFiltersQuery(filters: any) {

    if (!filters) return null;
    const { zip, commune, dep } = filters;

    let query = {};

    if (zip) {
      query = { ...query, code_postal_fr: zip };
    }
    if (dep) {
      query = { ...query, code_insee_departement: dep };
    }
    if (commune) {
      query = { ...query, commune: commune };
    }


    return query;
  }


}