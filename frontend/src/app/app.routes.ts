import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AboutComponent } from './pages/about/about.component';
import { ContactComponent } from './pages/contact/contact.component';
import { LoginComponent } from './pages/authentication/login/login.component';
import { CartComponent } from './pages/header/cart/cart.component';
import { StoreComponent } from './pages/store/store.component';
import { ThankyouComponent } from './pages/thankyou/thankyou.component';
import { CheckoutComponent } from './pages/checkout/checkout.component';
import { SignupComponent } from './pages/authentication/signup/signup.component';
import { AccommodationComponent } from './pages/categories/accommodation/accommodation.component';
import { HotelComponent } from './pages/categories/hotel/hotel.component';
import { CarRentalComponent } from './pages/categories/car-rental/car-rental.component';
import { FlightComponent } from './pages/categories/flight/flight.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: 'ExcursionEase | Home',
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
    path: 'login',
    component: LoginComponent,
    title: 'ExcursionEase | Login',
  },
  {
    path: 'signup',
    component: SignupComponent,
    title: 'ExcursionEase | Register',
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
  },
];
