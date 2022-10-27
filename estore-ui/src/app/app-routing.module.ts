import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingComponent } from './pages/landing/landing.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { RegisterComponent } from './pages/register/register.component';

const routes: Routes = [
{path: '', component: LandingComponent},
{path: 'inventory', component: InventoryComponent}, 
 {path: 'register', component: RegisterComponent}
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
