import { IPageable } from "./pageable.model";
import { ISortable, Sort } from "./sort.model";


export class PageRequest implements IPageable {
  offset!: number;
  pageNumber: number;
  pageSize: number;
  paged: true;
  unpaged: false;
  

  constructor(page: number = 1, size: number = 1) {
    this.pageNumber = page;
    this.pageSize = size;
    this.paged = true;
    this.unpaged = false;
  
  }
 

  public getPageNumber(): number {
    return this.pageNumber;
  }

  public getPageSize(): number {
    return this.pageSize;
  }

  

  public next(totalElements:number): IPageable {
    const totalPages: number = Math.ceil(totalElements / this.getPageSize());
    const nextPage: number = +this.getPageNumber() === totalPages ? 1 : +this.getPageNumber() + 1;
    return new PageRequest(nextPage, this.getPageSize());
  }

  public previous(totalElements: number): IPageable {
    const totalPages: number = Math.ceil(totalElements / this.getPageSize());
    const previousPage: number = +this.getPageNumber() === 1 ? totalPages : +this.getPageNumber() - 1;
    return new PageRequest(previousPage, this.getPageSize());
  }

  public static from(page: number, size: number): IPageable {
    return new PageRequest(page, size);
  }

  
}