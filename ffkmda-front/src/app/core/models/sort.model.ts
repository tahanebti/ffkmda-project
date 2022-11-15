export enum SortDirection {
    ASCENDING = 'asc',
    DESCENDING = 'desc'
  }
  

export interface ISortable {
    direction: string;
    property?: string;
    getSortDirection(): SortDirection;
    getSortProperty(): string;
    asKeyValue(): { [key: string]: string };
  }
  



  export class Sort {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
    public direction: SortDirection;
    public property: string;
  
    constructor(property: string = 'id', direction: SortDirection = SortDirection.ASCENDING) {
      this.empty = false
      this.sorted = true
      this.unsorted = false
      this.direction = direction; 
      this.property = property;
    }
  
    public getSortDirection(): SortDirection {
      return this.direction;
    }
  
    public getSortProperty(): string {
      return this.property;
    }
  
    public asKeyValue(): { [key: string]: string } {
      return {
        [this.getSortProperty()]: this.getSortDirection()
      };
    }
  
    public static from(property: string, direction: string): ISortable {
      switch (direction.toUpperCase()) {
        case 'ASC': return new Sort(property, SortDirection.ASCENDING);
        case 'DESC': return new Sort(property, SortDirection.DESCENDING);
        default: return new Sort(property, SortDirection.ASCENDING);
      }
    }
  }
  