import { Component } from '@angular/core';
import { NavbarComponent } from '../header/navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-produt-detail',
  standalone: true,
  imports: [NavbarComponent, FooterComponent, RouterLink],
  templateUrl: './produt-detail.component.html',
  styleUrl: './produt-detail.component.css'
})
export class ProdutDetailComponent {

}
