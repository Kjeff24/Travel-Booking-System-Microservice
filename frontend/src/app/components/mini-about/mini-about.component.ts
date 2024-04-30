import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-mini-about',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './mini-about.component.html',
  styleUrl: './mini-about.component.css'
})
export class MiniAboutComponent {

}
