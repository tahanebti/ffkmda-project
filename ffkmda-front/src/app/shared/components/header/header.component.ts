import { DOCUMENT } from '@angular/common';
import { ChangeDetectionStrategy, Component, ElementRef, EventEmitter, HostListener, Inject, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, BehaviorSubject, Observable, combineLatest, map, debounceTime, distinctUntilChanged, takeUntil } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush

})
export class HeaderComponent implements OnInit{

  @Output() menuToggled = new EventEmitter<boolean>();

  user: string = 'Achref';


  private _subscriptionsSubject$: Subject<void>;


  
  @Output()
  autoComplete = new EventEmitter<string>();
  autoCompleteSubject = new Subject<string>();

  @Output()
  searched = new EventEmitter<string>();
  searchSubject = new Subject<string>();


  @Output()
  filtered = new EventEmitter<{ key: string; value: string }>();

  @ViewChild('searchRef') searchRef: ElementRef<HTMLInputElement> | null = null;

  suggestionsVisibleSubject = new BehaviorSubject(false);

  private suggestionSubject = new BehaviorSubject<any>([]);
  readonly suggestionsContent$: Observable<any> = combineLatest([
    this.suggestionSubject.asObservable(),
    this.suggestionsVisibleSubject.asObservable(),
  ]).pipe(map(([suggestions, visible]) => (visible ? suggestions : {})));

  constructor(
    private readonly router: Router,
    @Inject(DOCUMENT) private readonly document: Document
  ) {
    this._subscriptionsSubject$ = new Subject<void>();
    this.autoCompleteSubject
      .asObservable()
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        takeUntil(this._subscriptionsSubject$)
      )
      .subscribe((searchTerm) => {
        this.autoComplete.emit(searchTerm);
      });
  }

  ngOnInit(): void {
    
  }

  logout(): void {
    console.log('Logged out');
  }

  @Input()
  set suggestions(data: Record<string, { _id: string; name: string }[]>) {
    this.suggestionSubject.next(data ?? {});
    this.suggestionsVisibleSubject.next(true);
  }

  @HostListener('window:click', ['$event'])
  onClick(event: MouseEvent) {
    const autoComplete = this.document.getElementById('suggestions');
    if (
      autoComplete &&
      !autoComplete.contains(event.target as Node) &&
      !this.searchRef?.nativeElement.contains(event.target as Node)
    ) {
      this.suggestionsVisibleSubject.next(false);
    }
  }

  suggestionsClicked(group: string, item: any) {
    switch (group) {
      case 'products':
        this.searched.emit(item.name);
        break;
      case 'brands':
      case 'categories':
        this.filtered.emit({ key: group, value: item._id });
        break;
    }
    this.suggestionsVisibleSubject.next(false);
  }

  ngOnDestroy(): void {
    this._subscriptionsSubject$.next();
    this._subscriptionsSubject$.complete();
  }
}