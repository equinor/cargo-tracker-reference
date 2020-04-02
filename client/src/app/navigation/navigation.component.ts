import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import navigation from 'src/assets/navigation.json';
import { StoDrawerComponent } from '@ngx-stoui/drawer';
import { Title } from '@angular/platform-browser';
import { MatSnackBar } from '@angular/material/snack-bar';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'ctref-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss'],
  animations: [
    trigger('slideInOut', [

        state('in', style({
          transform: 'translate3d(0%, 0, 0)'
        })),
        state('out', style({
          transform: 'translate3d(-100%, 0, 0)'
        })),
        transition('out => in', animate('400ms ease-in-out')),
        transition('in => out', animate('400ms ease-in-out'))
      ]
    )
  ]
})
export class NavigationComponent implements OnChanges {
  @ViewChild(StoDrawerComponent, { static: true })
  drawer: StoDrawerComponent;
  @Input()
  segment: string;
  @Input()
  environment: any = {};
  @Input()
  user: any;
  @Input()
  offline: boolean;
  @Output()
  navigate = new EventEmitter<string[]>();
  @Output()
  logout = new EventEmitter();
  @Input()
  breadCrumbs: any[];
  public navigation = navigation;
  public isOpen = false;

  home = {
    command: () => this.openMenu()
  };
  rootBreadCrumb = [ { label: 'Cargo Tracker', command: () => this.openMenu() } ];

  closeMenu() {
    this.isOpen = false;
  }

  openMenu() {
    this.isOpen = true;
  }

  constructor(private title: Title, private snackbar: MatSnackBar) {
  }

  ngOnChanges(changes: SimpleChanges) {

  }

  handleCommand({$event: event, command}: {$event: MouseEvent, command: any}) {
    switch ( command.type ) {
      case 'internalLink':
        this.isOpen = false;
        setTimeout(() => this.navigate.emit(command.arguments), 300);
        break;
      case 'externalLink':
        this.isOpen = false;
        const url = `${window.location.origin}${command.arguments[0]}`;
        if (event.ctrlKey) {
          window.open(url, '_blank');
        } else {
          window.location.href = url;
        }
        break;
      default:
        console.log(event);
    }
  }

}
