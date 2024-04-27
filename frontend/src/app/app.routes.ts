import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AboutComponent } from './pages/about/about.component';
import { ContactComponent } from './pages/contact/contact.component';
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
    title: 'ExcursionEase | Logout'

  },
  {
    path: 'store',
    component: StoreComponent,
    title: 'ExcursionEase | Store',
  },
  {
    path: 'about',
    component: AboutComponent,
    title: 'ExcursionEase | About',
  },
  {
    path: 'contact',
    component: ContactComponent,
    title: 'ExcursionEase | Contact',
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
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
    title: 'ExcursionEase | Checkout',
  },
  {
    path: 'store/category/accommodation',
    component: AccommodationComponent,
    title: 'ExcursionEase | Accommodation',
  },
  {
    path: 'store/category/hotel',
    component: HotelComponent,
    title: 'ExcursionEase | Hotel',
  },
  {
    path: 'store/category/car-rental',
    component: CarRentalComponent,
    title: 'ExcursionEase | Car Rental',
  },
  {
    path: 'store/category/flight',
    component: FlightComponent,
    title: 'ExcursionEase | Flight',
  }
];
