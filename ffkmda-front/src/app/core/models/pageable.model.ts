import { PageRequest } from "./page-request.model";

export interface IPageable{
    offset?: number;
    pageNumber?: number;
    pageSize?: number
    getPageNumber(): number;
    getPageSize(): number;
    next(totalElements: number): IPageable;
    previous(totalElements: number): IPageable;
}

export class Page<T> {
    public elements?: any;
    public totals?: number;
    public pages?: number;
    public current?: IPageable;
    public next?: IPageable;
     public previous?: IPageable;
}

export class PageableSearch {
    public code_postal_fr?: any;
    public code_insee_departement?: any;
    public  commune?: any;
    public pageable?: IPageable
}

export class FilterQuery{
    public code?: string
    public type?: string
    public commune?: string
    public code_postal_fr?: string
}


export const DEFAULT_SEARCH_PAGE: IPageable =   PageRequest.from(1, 5);
export const NULL_SEARCH_PAGE: IPageable =   PageRequest.from(1, 1);
