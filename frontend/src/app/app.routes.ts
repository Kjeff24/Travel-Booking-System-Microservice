import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AboutComponent } from './pages/about/about.component';
import { CartComponent } from './pages/cart/cart.component';
import { StoreComponent } from './pages/store/store.component';
import { ThankyouComponent } from './pages/thankyou/thankyou.component';
import { CheckoutComponent } from './pages/checkout/checkout.component';
import { AccommodationComponent } from './components/accommodation/accommodation.component';
import { HotelComponent } from './components/hotel/hotel.component';
import { CarRentalComponent } from './components/car-rental/car-rental.component';
import { FlightComponent } from './components/flight/flight.component';
import { AuthorizedComponent } from './components/authorized/authorized.component';
import { LogoutComponent } from './components/logout/logout.component';
import { DashboardComponent } from './pages/admin/admin-dashboard/dashboard.component';
import { CreateUpdateAccommodationComponent } from './pages/admin/create-update-accommodation/create-update-accommodation.component';
import { CreateUpdateCategoryComponent } from './pages/admin/create-update-category/create-update-category.component';
import { CreateUpdateCarRentalComponent } from './pages/admin/create-update-car-rental/create-update-car-rental.component';
import { CreateUpdateFlightComponent } from './pages/admin/create-update-flight/create-update-flight.component';
import { CreateUpdateHotelComponent } from './pages/admin/create-update-hotel/create-update-hotel.component';
import { adminGuard } from './guards/admin/admin.guard';
import { loginGuard } from './guards/login/login.guard';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'ExcursionEase | Home',
  },
  {
    path: 'login/oauth2/code/angular-client',
    component: AuthorizedComponent,
    title: 'ExcursionEase | Authorized',
  },
  {
    path: 'logout',
    component: LogoutComponent,
    title: 'ExcursionEase | Logout',
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    title: 'ExcursionEase Admin | Dashboard',
    canActivate: [adminGuard],
    children: [
      {
        path: 'create-category',
        component: CreateUpdateCategoryComponent,
        title: 'ExcursionEase Admin | Create-Category',
      },
      {
        path: 'create-accommodation',
        component: CreateUpdateAccommodationComponent,
        title: 'ExcursionEase Admin | Create-Accommodation',
      },
      {
        path: 'create-car-rental',
        component: CreateUpdateCarRentalComponent,
        title: 'ExcursionEase Admin | Create-Car-Rental',
      },
      {
        path: 'create-flight',
        component: CreateUpdateFlightComponent,
        title: 'ExcursionEase Admin | Create-Flight',
      },
      {
        path: 'create-hotel',
        component: CreateUpdateHotelComponent,
        title: 'ExcursionEase Admin | Create-Hotel',
      },

      {
        path: 'update-category/:categoryId',
        component: CreateUpdateCategoryComponent,
        title: 'ExcursionEase Admin | Update-Category',
      },
      {
        path: 'update-accommodation/:bookingId',
        component: CreateUpdateAccommodationComponent,
        title: 'ExcursionEase Admin | Update-Accommodation',
      },
      {
        path: 'update-car-rental/:bookingId',
        component: CreateUpdateCarRentalComponent,
        title: 'ExcursionEase Admin | Update-Car-Rental',
      },
      {
        path: 'update-flight/:bookingId',
        component: CreateUpdateFlightComponent,
        title: 'ExcursionEase Admin | Update-Flight',
      },
      {
        path: 'update-hotel/:bookingId',
        component: CreateUpdateHotelComponent,
        title: 'ExcursionEase Admin | Update-Hotel',
      },
    ],
  },
  {
    path: 'store',
    component: StoreComponent,
    title: 'ExcursionEase | Store',
    children: [
      {
        path: 'category/accommodation',
        component: AccommodationComponent,
        title: 'ExcursionEase | Accommodation',
      },
      {
        path: 'category/hotel',
        component: HotelComponent,
        title: 'ExcursionEase | Hotel',
      },
      {
        path: 'category/car-rental',
        component: CarRentalComponent,
        title: 'ExcursionEase | Car Rental',
      },
      {
        path: 'category/flight',
        component: FlightComponent,
        title: 'ExcursionEase | Flight',
      },
    ],
  },
  {
    path: 'about',
    component: AboutComponent,
    title: 'ExcursionEase | About',
  },
  {
    path: 'cart',
    component: CartComponent,
    title: 'ExcursionEase | Cart',
  },
  {
    path: 'thankyou',
    component: ThankyouComponent,
    title: 'ExcursionEase | Thankyou',
    canActivate: [loginGuard],
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
    title: 'ExcursionEase | Checkout',
    canActivate: [loginGuard],
  },
];
