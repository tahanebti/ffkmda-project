import { Component, Input, OnInit, OnDestroy, Type, ViewChild, ViewContainerRef,  HostListener, ComponentRef } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { fadeAnimation } from '../../animations/fade.animation';
import { DrawerLocation } from './drawer-location.enum';
import { DEFAULT_DRAWER_OPTIONS } from './drawer-options.defaults';
import { DrawerOptions } from './drawer-options.model';
import { DrawerOverlayStyle } from './drawer-overlay-style.enum';
import { DrawerSize } from './drawer-size.enum';
import { DrawerService } from './drawer.service';

@Component({
  selector: 'app-drawer',
  templateUrl: './drawer.component.html',
  styleUrls: ['./drawer.component.scss'],
  animations: [fadeAnimation],
  providers: [DrawerService]
})
export class DrawerComponent implements OnInit, OnDestroy {
  @ViewChild('content', { read: ViewContainerRef })
  public drawerContentRef!: ViewContainerRef;

  @Input()
  public overlayStyle: DrawerOverlayStyle = DrawerOverlayStyle.DIM_DARK;

  @Input()
  public drawerLocation: DrawerLocation = DrawerLocation.RIGHT;

  @Input()
  public closeOnEscape = true;

  @Input()
  public drawerSize: DrawerSize = DrawerSize.DEFAULT;

  public isDrawerVisible!: boolean;

  public DrawerLocation = DrawerLocation;

  public options: DrawerOptions = DEFAULT_DRAWER_OPTIONS;

  private _drawerServiceSubject$: Subject<void>;

  constructor(
    private _drawerService: DrawerService
  ) {
    this._drawerServiceSubject$ = new Subject<void>();
  }

  ngOnInit(): void {
    this._drawerService.onDrawerOptionsChange()
      .pipe(takeUntil(this._drawerServiceSubject$))
      .subscribe((options: DrawerOptions) => this.options = options);

    this._drawerService.onDrawerVibilityChange()
      .pipe(takeUntil(this._drawerServiceSubject$))
      .subscribe((visible: boolean) => {
        this.isDrawerVisible = visible
       
        console.log(this.isDrawerVisible)
      });

    this._drawerService.onContentChange()
      .pipe(takeUntil(this._drawerServiceSubject$))
      .subscribe((component: Type<any> | null) => {
        if (component) {
          this._setDrawerContent(component)
        } else {
          this._removeDrawerContent();
        }
      });
  }

  public close(): void {
    this._drawerService.close();
  }

  private _setDrawerContent(component: Type<any>) {
    this.drawerContentRef.clear();
    const componentRef: ComponentRef<any> = this.drawerContentRef.createComponent(component);
  }

  private _removeDrawerContent(): void {
    this.drawerContentRef.clear();
  }

  @HostListener('document:keydown.escape', ['$event']) 
  public onKeydownHandler(event: KeyboardEvent) {
    if (this.closeOnEscape) {
      event.preventDefault();
      this._drawerService.close();
    }
  }

  ngOnDestroy() {
    this._drawerServiceSubject$.next();
    this._drawerServiceSubject$.complete();
  }
}