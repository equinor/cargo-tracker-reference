import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationComponent } from './navigation.component';
import { StoDrawerModule, StoNavigationModule } from '@ngx-stoui/drawer';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { StoAppHeaderModule } from '@ngx-stoui/common';
import { MatSnackBarModule } from '@angular/material/snack-bar';



@NgModule({
  declarations: [NavigationComponent],
  exports: [NavigationComponent],
  imports: [
    CommonModule,
    StoAppHeaderModule,
    StoDrawerModule,
    StoNavigationModule,
    MatIconModule,
    MatSnackBarModule,
    MatButtonModule,
    MatMenuModule,
  ]
})
export class NavigationModule { }
