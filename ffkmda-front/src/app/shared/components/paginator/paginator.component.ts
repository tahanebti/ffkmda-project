import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PageRequest } from 'src/app/core/models/page-request.model';
import { Page, IPageable } from 'src/app/core/models/pageable.model';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent implements OnInit {

  @Input()
  public set page(currentPage: any) {
    if (currentPage) {
      this.currentPage = currentPage;
      this.pages = this._generatePagesForPaginator(currentPage?.current?.pageNumber , currentPage.totalPages);
    }
  }

  @Output()
  public onNextPage: EventEmitter<IPageable>;

  @Output()
  public onPreviousPage: EventEmitter<IPageable>;

  @Output()
  public onGoToPage: EventEmitter<IPageable>;

  public currentPage: any;
  public pages: number[] = [];
  public dot: any = "..."
  constructor() {
    this.onNextPage = new EventEmitter<IPageable>();
    this.onPreviousPage = new EventEmitter<IPageable>();
    this.onGoToPage = new EventEmitter<IPageable>();
  }

  ngOnInit(): void {
    console.log(this.currentPage, this.pages)
  }

  public nextPage(): void {
    if (this.currentPage && this.currentPage.next) {
      this.onNextPage.emit(this.currentPage.next);
    }
  }

  public previousPage(): void {
    if (this.currentPage && this.currentPage.next) {
      this.onPreviousPage.emit(this.currentPage.previous);
    }
  }
  

  public goToPage(pageNumber: number): void {
    const pageToGoTo: IPageable = PageRequest.from(pageNumber, this.currentPage?.current?.pageSize)
    this.onGoToPage.emit(pageToGoTo);
  }

  private _generatePagesForPaginator(currentPage: number, totalPages: number): number[] {
    const delta = 2;
    const range = [];
    const rangeWithDots: any = [];
    let l;
  
    range.push(1);

    if (totalPages <= 1){
      return range;
    }

    for (let i = currentPage - delta; i <= currentPage + delta; i++) {
      if (i < totalPages && i > 1) {
        range.push(i);
      }
    }

    range.push(totalPages);

    for (let i of range) {
      if (l) {
        if (i - l === 2) {
          rangeWithDots.push(l + 1);
        } else if (i - l !== 1) {
          rangeWithDots.push('...');
        }
      }
      rangeWithDots.push(i);
      l = i;
    }

    return rangeWithDots;
  }

}
