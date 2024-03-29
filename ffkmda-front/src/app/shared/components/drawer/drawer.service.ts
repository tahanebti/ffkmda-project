import { Injectable, Type } from '@angular/core';
import { BehaviorSubject, Subject, Observable } from 'rxjs';
import { DrawerOptions } from './drawer-options.model';
import { DEFAULT_DRAWER_OPTIONS } from './drawer-options.defaults';

@Injectable({
    providedIn: 'root'
})
export class DrawerService {
  private readonly _defaultOptions: DrawerOptions;
  private _isDrawerVisible: boolean;
  private _closeDrawerSource: BehaviorSubject<boolean>;

  private _contentChangeSource: Subject<Type<any> | null>;
  private _dataChangeSource:  BehaviorSubject<any>;
  private _optionsSource: BehaviorSubject<DrawerOptions>;

  constructor() {
    this._defaultOptions = DEFAULT_DRAWER_OPTIONS;
    this._isDrawerVisible = false;
    this._closeDrawerSource = new BehaviorSubject<boolean>(this._isDrawerVisible);
    this._contentChangeSource = new Subject<any>();
    this._dataChangeSource = new BehaviorSubject<any>(null);
    this._optionsSource = new BehaviorSubject<DrawerOptions>(DEFAULT_DRAWER_OPTIONS);
  }

  public onDrawerVibilityChange(): Observable<boolean> {
    return this._closeDrawerSource.asObservable()
  }

  public onContentChange(): Observable<Type<any> | null> {
    return this._contentChangeSource.asObservable();
  }

  public onDataChange(): Observable<any> {
    return this._dataChangeSource.asObservable();
  }

  public onDrawerOptionsChange(): Observable<DrawerOptions> {
    return this._optionsSource.asObservable();
  }

  public setData(data: any): void {
    this._dataChangeSource.next(data);
  }

  public show(content: Type<any>, options?: DrawerOptions): void {
    
    const drawerOptions: DrawerOptions = {
      ...this._defaultOptions,
      ...options
    };
    console.log("options", drawerOptions)
    this._contentChangeSource.next(content);
    this._optionsSource.next(drawerOptions);
    this._dataChangeSource.next(drawerOptions.data);
    this._isDrawerVisible = true;
    this._isDrawerVisible = !this._isDrawerVisible;
    this._closeDrawerSource.next(this._isDrawerVisible);
  }

  public close(): void {
    this._isDrawerVisible = false;
    this._isDrawerVisible = !this._isDrawerVisible;
    this._closeDrawerSource.next(this._isDrawerVisible);
    this._dataChangeSource.next(null);
    this._contentChangeSource.next(null);
    console.log(this._isDrawerVisible)
  }
}