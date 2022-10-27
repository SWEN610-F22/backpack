import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingComponent } from './pages/landing/landing.component';
import { LoginComponent } from './pages/login/login.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { RegisterComponent } from './pages/register/register.component';
import { CartPageComponent } from './pages/cart-page/cart-page.component';

const routes: Routes = [{path: '', component: LandingComponent},
                        {path: 'register', component: RegisterComponent},
                        {path: 'cart', component: CartPageComponent},
                        {path: 'inventory', component: InventoryComponent},
                        {path: 'login', component: LoginComponent}];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
