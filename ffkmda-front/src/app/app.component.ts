import { isPlatformServer } from '@angular/common';
import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnDestroy, OnInit, PLATFORM_ID } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { filter, map, Observable, of, share, Subject, switchMap, takeUntil, tap } from 'rxjs';
import { DEFAULT_SEARCH_PAGE, FilterQuery, IPageable, NULL_SEARCH_PAGE, PageableSearch } from './core/models/pageable.model';
import { AutoCompleteResult, ExtranetService } from './core/service/extranet.service';
declare var $: any;
import { groupBy } from 'lodash-es';
import { PageRequest } from './core/models/page-request.model';
import { fadeAnimation } from './shared/animations/fade.animation';

const typeMap: Record<string, string> = {
  ClubDepartement: 'departement',
  ClubCommune: 'commune',
};

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [fadeAnimation]
})
export class AppComponent implements OnInit, AfterViewInit, OnDestroy  {
  regions: any;
  clubs: any
  targetCode: any;
  filter: any
  p = 1
  private readonly DEFAULT_PAGE: IPageable = DEFAULT_SEARCH_PAGE || NULL_SEARCH_PAGE;

  private _subscriptionSubject: Subject<void>;

  sections$!: Observable<{ type: string; clubs: any[] }[]>;

  public items = new Array(18);
  public isLoading = false;
  public isServer!: boolean;
  commune: any


  opened = true;

  public suggestions$!: Observable<AutoCompleteResult>;
  public  autoCompleteSubject = new Subject<string>();
  cubPage: any;

  constructor(
    private _extranetService: ExtranetService,
    
    @Inject(PLATFORM_ID) private platformId: Object, //eslint-disable-line
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    this._subscriptionSubject = new Subject<void>();
  }

  ngOnInit() {
    this.listenToEvents();
    this.isServer = isPlatformServer(this.platformId);


  }

  
  onSearch(searchTerm: string) {
    this.router.navigate(['/'], { queryParams: { search: searchTerm } });
  }

  onAutoComplete($event: string) {
    this.autoCompleteSubject.next($event);
  }

  onFilter({ key, value }: { key: string; value: string }) {
    this.router.navigate(['/'], {
      queryParams: {
        filters: `${key}=${value}`,
      },
      queryParamsHandling: 'merge',
    });
  }
  
  toggle(): void {
    this.opened = !this.opened;
  }


  listenToEvents(){
    this.listenToSearchQuery()
  }
  
  listenToSearchQuery(page?: PageableSearch) {
    const clubSearch: PageableSearch = {
      pageable: this.cubPage || this.DEFAULT_PAGE
    };
    this._extranetService
    .searchByCommune("paris", clubSearch)
    .pipe(
      tap((resp: any) => this.setLoading(true)),
      switchMap(
        (query?: any) => (this.sections$ = this.getSearchRequest(query, page))
      ),
      takeUntil(this._subscriptionSubject)
    )
    .subscribe(resp => {
      this.clubs = resp
      console.log(resp)
      this.setLoading(false);
      this.cdr.detectChanges();
    })
  }


  private getSearchRequest(query?: any, page?: any): Observable<any> {
    console.log("target page", this.cubPage)
    const clubSearch: PageableSearch = {
      pageable: this.cubPage || this.DEFAULT_PAGE
    };
   
    return this.route.queryParams
    .pipe(switchMap((p: any) => {
      if (p.dep) {
        console.log(p.dep)
        this.filter = p.dep
        return this._extranetService.searchByDep(p.dep, clubSearch)
      }

      if(p.search){
        console.log(typeof p.search)
        console.log(p.search instanceof Number)
        this.filter = p.search
        return this.isNumber(p.search) ? this._extranetService.searchByCodePostal(p.search, clubSearch) : 
        this._extranetService.searchByCommune(p.search, clubSearch)
      }
      return of([]);
    }))
  }

  isNumber(n: any) { return !isNaN(parseFloat(n)) && !isNaN(n - 0) }


  

  private setLoading(isLoading: boolean): void {
    this.isLoading = isLoading;
    this.cdr.detectChanges();
  }

  
  ngAfterViewInit() {
    var regions = this.regions;

    $(function() {
      $('#vmap').vectorMap({
        map: 'france_fr',
        backgroundColor: '#fff',
        borderColor: '#818181',
        borderOpacity: 0.75,
        borderWidth: 1,
        color: '#f4f3f0',
        enableZoom: true,
        hoverColor: '#c9dfaf',
        hoverOpacity: null,
        normalizeFunction: 'linear',
        scaleColors: ['#b6d6ff', '#005ace'],
        selectedColor: '#c9dfaf',
        selectedRegions: null,
        showTooltip: true,
   // Click by region
    onRegionClick: function(element:any, code:any, region:any) {

      var name = '<strong>' + region + '</strong><br>';
      localStorage.setItem("commune", region)
      console.log(code)
      let dep = code.split("-")[1]
      var urlParts = location.href.split("?");
      location.href = urlParts[0] + "?dep=" + dep;

      //location.reload()
    },
    });
  })
   
  
  }

  public onGoToPage(event: any): void {
      console.log(event)
      this.cubPage = PageRequest.from(event.pageNumber, event.pageSize);
      
      console.log("target page", this.cubPage)

      this.getSearchRequest(this.filter, this.cubPage)
      .pipe(
        takeUntil(this._subscriptionSubject)
      )
      .subscribe(resp => {
        this.clubs = resp
        console.log(resp)
        this.setLoading(false);
        this.cdr.detectChanges();
      })
  }

 
  
  ngOnDestroy(): void {
    this._subscriptionSubject.next();
    this._subscriptionSubject.complete();
    console.log('person component destroyed');
  }


}

